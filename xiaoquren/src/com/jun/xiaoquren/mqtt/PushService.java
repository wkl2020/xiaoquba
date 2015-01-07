package com.jun.xiaoquren.mqtt;

import com.ibm.mqtt.IMqttClient;
import com.ibm.mqtt.MqttClient;
import com.ibm.mqtt.MqttException;
import com.ibm.mqtt.MqttPersistence;
import com.ibm.mqtt.MqttPersistenceException;
import com.ibm.mqtt.MqttSimpleCallback;
import com.jun.xiaoquren.MainActivity;
import com.jun.xiaoquren.util.LocalLog;
import com.lidroid.xutils.util.LogUtils;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;

public class PushService extends Service {
	public static final String CLASSNAME = "PushService";
	
	public static final String		TAG = "QuPushService";

	private static final String		MQTT_HOST = "192.168.1.118";
	private static int				MQTT_BROKER_PORT_NUM      = 1883;
	
	private static MqttPersistence	MQTT_PERSISTENCE          = null;
	private static boolean			MQTT_CLEAN_START          = true;
	private static short			MQTT_KEEP_ALIVE           = 60 * 15;
	private static int[]			MQTT_QUALITIES_OF_SERVICE = { 0 } ;
	private static int				MQTT_QUALITY_OF_SERVICE   = 0;
	private static boolean			MQTT_RETAINED_PUBLISH     = false;

	public static String			NOTIF_TITLE = "小区人"; 
	public static String			MQTT_CLIENT_ID = "Qu";

	private static final String		ACTION_START = "PushService.START";
	private static final String		ACTION_STOP = "PushService.STOP";
	private static final String		ACTION_KEEPALIVE = "PushService.KEEP_ALIVE";
	private static final String		ACTION_RECONNECT = "PushService.RECONNECT";
	
	private ConnectivityManager		mConnMan;
	private NotificationManager		mNotifMan;
	private boolean 				mStarted;

	private static final long		KEEP_ALIVE_INTERVAL = 1000 * 60 * 28;
	private static final long		INITIAL_RETRY_INTERVAL = 1000 * 10;
	private static final long		MAXIMUM_RETRY_INTERVAL = 1000 * 60 * 30;

	private SharedPreferences 		mPrefs;
	public static final String		PREF_STARTED = "isStarted";
	public static final String		PREF_DEVICE_ID = "deviceID";
	public static final String		PREF_RETRY = "retryInterval";
	
	private static final int		NOTIF_CONNECTED = 0;	
		
	private MQTTConnection			mConnection;
	private long					mStartTime;
	
	public static void actionStart(Context ctx) {
		LocalLog.info(CLASSNAME, "actionStart", "Start");
		
		Intent i = new Intent(ctx, PushService.class);
		i.setAction(ACTION_START);
		ctx.startService(i);
	}

	public static void actionStop(Context ctx) {
		LocalLog.info(CLASSNAME, "actionStop", "Start");
		
		Intent i = new Intent(ctx, PushService.class);
		i.setAction(ACTION_STOP);
		ctx.startService(i);
	}
	
	public static void actionPing(Context ctx) {
		LocalLog.info(CLASSNAME, "actionPing", "Start");
		
		Intent i = new Intent(ctx, PushService.class);
		i.setAction(ACTION_KEEPALIVE);
		ctx.startService(i);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		
		LocalLog.info(CLASSNAME, "onCreate", "Start");
		LogUtils.i("PushService: on creating service...");
		mPrefs = getSharedPreferences(TAG, MODE_PRIVATE);
		mConnMan = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
		mNotifMan = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		mStartTime = System.currentTimeMillis();
		handleCrashedService();
	}

	private void handleCrashedService() {
		LocalLog.info(CLASSNAME, "handleCrashedService", "Start wasStarted:" + wasStarted());
		if (wasStarted() == true) {
			stopKeepAlives(); 
			start();
		}
	}
	
	@Override
	public void onDestroy() {
		LocalLog.info(CLASSNAME, "onDestroy", "Start");
		LogUtils.i("PushService: Service destroyed (started=" + mStarted + ")");

		if (mStarted == true) {
			stop();
		}
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		LocalLog.info(CLASSNAME, "onStart", "Start Action: " + (intent == null ? "intent is NULL":intent.getAction()));
		LogUtils.i("PushService: Service started with intent=" + intent);
		
		if (intent == null) return;

		if (intent.getAction().equals(ACTION_STOP) == true) {
			stop();
			stopSelf();
		} else if (intent.getAction().equals(ACTION_START) == true) {
			start();
		} else if (intent.getAction().equals(ACTION_KEEPALIVE) == true) {
			keepAlive();
		} else if (intent.getAction().equals(ACTION_RECONNECT) == true) {
			if (isNetworkAvailable()) {
				reconnectIfNecessary();
			}
		}
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	private boolean wasStarted() {
		return mPrefs.getBoolean(PREF_STARTED, false);
	}

	private void setStarted(boolean started) {
		mPrefs.edit().putBoolean(PREF_STARTED, started).commit();		
		mStarted = started;
	}

	private synchronized void start() {
		LocalLog.info(CLASSNAME, "start", "Start");
		LogUtils.i("PushService: Starting service...");
		
		if (mStarted == true) {
			LogUtils.i("PushService: Attempt to start connection that is already active");
			return;
		}
		
		connect();
		registerReceiver(mConnectivityChanged, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));		
	}

	private synchronized void stop() {
		LocalLog.info(CLASSNAME, "stop", "Start");
		
		if (mStarted == false) {
			LogUtils.i("PushService: Attempt to stop connection not active.");
			return;
		}

		setStarted(false);
		unregisterReceiver(mConnectivityChanged);
		cancelReconnect();

		if (mConnection != null) {
			mConnection.disconnect();
			mConnection = null;
		}
	}
	
	// 
	private synchronized void connect() {
		LocalLog.info(CLASSNAME, "connect", "Start");
		LogUtils.i("PushService: Connecting...");
		
		String deviceID = mPrefs.getString(PREF_DEVICE_ID, null);
//		String deviceID = "";
		if (deviceID == null) {
			LogUtils.i("PushService: Device ID not found.");
		} else {
			try {
				mConnection = new MQTTConnection(MQTT_HOST, deviceID);
			} catch (MqttException e) {
				LocalLog.info(CLASSNAME, "connect:MqttException: ", (e.getMessage() != null ? e.getMessage() : "NULL"));
				LogUtils.i("PushService: MqttException: " + (e.getMessage() != null ? e.getMessage() : "NULL"));
	        	if (isNetworkAvailable()) {
	        		scheduleReconnect(mStartTime);
	        	}
			}
			setStarted(true);
		}
	}

	private synchronized void keepAlive() {
		LocalLog.info(CLASSNAME, "keepAlive", "Start");
		
		try {
			if (mStarted == true && mConnection != null) {
				mConnection.sendKeepAlive();
			}
		} catch (MqttException e) {
			LogUtils.i("PushService: MqttException: " + (e.getMessage() != null? e.getMessage(): "NULL"), e);
			
			mConnection.disconnect();
			mConnection = null;
			cancelReconnect();
		}
	}

	private void startKeepAlives() {
		LocalLog.info(CLASSNAME, "startKeepAlives", "Start");
		
		Intent i = new Intent();
		i.setClass(this, PushService.class);
		i.setAction(ACTION_KEEPALIVE);
		PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
		AlarmManager alarmMgr = (AlarmManager)getSystemService(ALARM_SERVICE);
		alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,
		  System.currentTimeMillis() + KEEP_ALIVE_INTERVAL,
		  KEEP_ALIVE_INTERVAL, pi);
	}

	private void stopKeepAlives() {
		LocalLog.info(CLASSNAME, "stopKeepAlives", "Start");
		
		Intent i = new Intent();
		i.setClass(this, PushService.class);
		i.setAction(ACTION_KEEPALIVE);
		PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
		AlarmManager alarmMgr = (AlarmManager)getSystemService(ALARM_SERVICE);
		alarmMgr.cancel(pi);
	}

	public void scheduleReconnect(long startTime) {
		LocalLog.info(CLASSNAME, "scheduleReconnect", "Start");
		
		long interval = mPrefs.getLong(PREF_RETRY, INITIAL_RETRY_INTERVAL);
		long now = System.currentTimeMillis();
		long elapsed = now - startTime;

		if (elapsed < interval) {
			interval = Math.min(interval * 4, MAXIMUM_RETRY_INTERVAL);
		} else {
			interval = INITIAL_RETRY_INTERVAL;
		}
		
		LocalLog.info(CLASSNAME, "scheduleReconnect", "Rescheduling connection in " + interval + "ms.");
		LogUtils.i("PushService: Rescheduling connection in " + interval + "ms.");

		mPrefs.edit().putLong(PREF_RETRY, interval).commit();
		Intent i = new Intent();
		i.setClass(this, PushService.class);
		i.setAction(ACTION_RECONNECT);
		PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
		AlarmManager alarmMgr = (AlarmManager)getSystemService(ALARM_SERVICE);
		alarmMgr.set(AlarmManager.RTC_WAKEUP, now + interval, pi);
	}
	
	public void cancelReconnect() {
		LocalLog.info(CLASSNAME, "cancelReconnect", "Start");
		
		Intent i = new Intent();
		i.setClass(this, PushService.class);
		i.setAction(ACTION_RECONNECT);
		PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
		AlarmManager alarmMgr = (AlarmManager)getSystemService(ALARM_SERVICE);
		alarmMgr.cancel(pi);
	}
	
	private synchronized void reconnectIfNecessary() {
		LocalLog.info(CLASSNAME, "reconnectIfNecessary", "Start mConnection:" + mConnection);
		
		if (mStarted == true && mConnection == null) {
			LogUtils.i("PushService: Reconnecting...");
			connect();
		}
	}

	private BroadcastReceiver mConnectivityChanged = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			NetworkInfo info = (NetworkInfo)intent.getParcelableExtra (ConnectivityManager.EXTRA_NETWORK_INFO);
			boolean hasConnectivity = (info != null && info.isConnected()) ? true : false;
			
			LocalLog.info(CLASSNAME, "BroadcastReceiver:onReceive", "Connectivity changed: connected=" + hasConnectivity);
			LogUtils.i("PushService: Connectivity changed: connected=" + hasConnectivity);

			if (hasConnectivity) {
				reconnectIfNecessary();
			} else if (mConnection != null) {
				mConnection.disconnect();
				cancelReconnect();
				mConnection = null;
			}
		}
	};
	
	private void showNotification(String text) {
		LocalLog.info(CLASSNAME, "showNotification", "Start");
		
		Notification n = new Notification();
				
		n.flags |= Notification.FLAG_SHOW_LIGHTS;
      	n.flags |= Notification.FLAG_AUTO_CANCEL;

        n.defaults = Notification.DEFAULT_ALL;
      	
		n.icon = com.jun.xiaoquren.R.drawable.test_app_icon;
		n.when = System.currentTimeMillis();

		PendingIntent pi = PendingIntent.getActivity(this, 0,
		  new Intent(this, MainActivity.class), 0);

		n.setLatestEventInfo(this, NOTIF_TITLE, text, pi);

		mNotifMan.notify(NOTIF_CONNECTED, n);
	}
	
	private boolean isNetworkAvailable() {
		LocalLog.info(CLASSNAME, "isNetworkAvailable", "Start");
		
		NetworkInfo info = mConnMan.getActiveNetworkInfo();
		if (info == null) {
			return false;
		}
		return info.isConnected();
	}
	
	private class MQTTConnection implements MqttSimpleCallback {
		IMqttClient mqttClient = null;
		
		public MQTTConnection(String brokerHostName, String initTopic) throws MqttException {
			LocalLog.info(CLASSNAME, "MQTTConnection:MQTTConnection", "Start");
			
	    	String mqttConnSpec = "tcp://" + brokerHostName + "@" + MQTT_BROKER_PORT_NUM;
        	mqttClient = MqttClient.createMqttClient(mqttConnSpec, MQTT_PERSISTENCE);
        	String clientID = MQTT_CLIENT_ID + "/" + mPrefs.getString(PREF_DEVICE_ID, "");
//        	String clientID = "OnlineNoticeTopic";
        	mqttClient.connect(clientID, MQTT_CLEAN_START, MQTT_KEEP_ALIVE);

			mqttClient.registerSimpleHandler(this);
			
//			initTopic = MQTT_CLIENT_ID + "/" + initTopic;
			initTopic = "OnlineNoticeTopic/WYN";
			subscribeToTopic(initTopic);
	
			LogUtils.i("PushService: Connection established to " + brokerHostName + " on topic " + initTopic);
	
			mStartTime = System.currentTimeMillis();
			startKeepAlives();				        
		}
		
		public void disconnect() {
			LocalLog.info(CLASSNAME, "MQTTConnection:disconnect", "Start");
			
			try {			
				stopKeepAlives();
				mqttClient.disconnect();
			} catch (MqttPersistenceException e) {
				LogUtils.i("PushService: MqttException" + (e.getMessage() != null? e.getMessage():" NULL"), e);
			}
		}

		private void subscribeToTopic(String topicName) throws MqttException {			
			LocalLog.info(CLASSNAME, "MQTTConnection:subscribeToTopic", "Start");
			
			if ((mqttClient == null) || (mqttClient.isConnected() == false)) {
				LogUtils.i("PushService: Connection error" + "No connection");	
			} else {									
				String[] topics = { topicName };
				mqttClient.subscribe(topics, MQTT_QUALITIES_OF_SERVICE);
			}
		}	

		private void publishToTopic(String topicName, String message) throws MqttException {	
			LocalLog.info(CLASSNAME, "MQTTConnection:publishToTopic", "Start");
			
			if ((mqttClient == null) || (mqttClient.isConnected() == false)) {
				LogUtils.i("PushService: No connection to public to");		
			} else {
				mqttClient.publish(topicName, 
								   message.getBytes(),
								   MQTT_QUALITY_OF_SERVICE, 
								   MQTT_RETAINED_PUBLISH);
			}
		}		

		public void connectionLost() throws Exception {
			LocalLog.info(CLASSNAME, "MQTTConnection:connectionLost", "Start");
			LogUtils.i("PushService: Loss of connection" + "connection downed");
			
			stopKeepAlives();
			mConnection = null;
			if (isNetworkAvailable() == true) {
				reconnectIfNecessary();	
			}
		}		
		
		public void publishArrived(String topicName, byte[] payload, int qos, boolean retained) {
			String s = new String(payload);
			showNotification(s);	
			
			LocalLog.info(CLASSNAME, "MQTTConnection:publishArrived", "Got message: " + s);
			LogUtils.i("PushService: Got message: " + s);
		}   
		
		public void sendKeepAlive() throws MqttException {
			LocalLog.info(CLASSNAME, "MQTTConnection:sendKeepAlive", "Start");
			LogUtils.i("PushService: Sending keep alive");
			
			publishToTopic(MQTT_CLIENT_ID + "/keepalive", mPrefs.getString(PREF_DEVICE_ID, ""));
//			publishToTopic("OnlineNoticeTopic/keepalive", "");
		}		
	}
}