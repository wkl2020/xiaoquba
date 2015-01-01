/*
 * $Id$
 */

package com.jun.xiaoquren.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Environment;

public class LocalLog {
	
	public static final String CLASSNAME = "LocalLog";
	private String mPath;
	private Writer mWriter;

	private static final SimpleDateFormat TIMESTAMP_FMT = new SimpleDateFormat("[HH:mm:ss] ");

	private LocalLog() throws IOException {
		
		File sdcard = Environment.getExternalStorageDirectory();
        File logDir = new File(sdcard, "xiaoquren/log/");
        if (!logDir.exists()) {
        	logDir.mkdirs();
        	// do not allow media scan
            new File(logDir, ".nomedia").createNewFile();
        }        
		
		open(logDir.getAbsolutePath() + "/xiaoquren.txt");
	}

	private LocalLog(String basePath) throws IOException {
		open(basePath);
	}
	
	private static LocalLog LOG = null;
	public static void info(String classname, String methodname, String message) {
		try {
			if (LOG == null) {
				LOG = new LocalLog();
			}
			LOG.logInfo(classname, methodname, message);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	protected void open(String basePath) throws IOException {
		File f = new File(basePath + "-" + getTodayString());
		mPath = f.getAbsolutePath();
		mWriter = new BufferedWriter(new FileWriter(mPath), 2048);

		logInfo(LocalLog.CLASSNAME, "open", "Start log");
	}

	private static String getTodayString() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd-hhmmss");
		return df.format(new Date());
	}

	public String getPath() {
		return mPath;
	}

	public void logInfo(String classname, String methodname, String message) throws IOException {
		
		mWriter.write(TIMESTAMP_FMT.format(new Date()));
		mWriter.write(classname + ":" + methodname + ": ");
		mWriter.write(message);
		mWriter.write('\n');
		mWriter.flush();
	}

	public void close() throws IOException {
		logInfo(LocalLog.CLASSNAME, "close", "End log");
		
		mWriter.close();
	}
}
