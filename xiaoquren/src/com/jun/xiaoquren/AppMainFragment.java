package com.jun.xiaoquren;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jun.xiaoquren.http.LocalHttpUtil;
import com.jun.xiaoquren.util.LocalUtil;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

public class AppMainFragment extends Fragment {

    public static final String EXTRA_TITLE = "title";
    
    public static final String CLASSNAME = "AppMainFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        View root = inflater.inflate(R.layout.main_activity, container, false);
        
    	String xiaoquName = LocalUtil.getCurrentXiaoQuName(this.getActivity());
    	TextView currentXiaoquName = (TextView) root.findViewById(R.id.current_xiaoqu_name);
    	currentXiaoquName.setText(xiaoquName);
    	currentXiaoquName.setOnClickListener(new XiaoquNameOnClickListener(this.getActivity()));
    	
    	LocalUtil.setAppMainFragment(this);
    	System.out.println("YYYYYY1: AppMainFragment: onCreateView: ");
    	
        return root;
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.out.println("YYYYYY2: AppMainFragment: onDestroyView: ");
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("YYYYYY3: AppMainFragment: onDestroy: ");
//        LocalUtil.setAppMainFragment(null);
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	System.out.println("YYYYYY4: AppMainFragment: onResume: ");
    }
    
    public void refreshCurrentXiaoQuName() {
    	String xiaoquName = LocalUtil.getCurrentXiaoQuName(this.getActivity());
    	if (xiaoquName == null || xiaoquName.isEmpty()) {
    		xiaoquName = "请选择小区...";
    	}
    	
    	TextView currentXiaoquName = (TextView)this.getActivity().findViewById(R.id.current_xiaoqu_name);
    	currentXiaoquName.setText(xiaoquName);
    }

}

class XiaoquNameOnClickListener implements View.OnClickListener {  
	
	FragmentActivity parentView = null;

    public XiaoquNameOnClickListener(FragmentActivity parentView) { 
    	this.parentView = parentView;
    }
    
  @Override  
  public void onClick(View v) {
	  
	  LocalHttpUtil.getDefaultHttpUtils().send(HttpRequest.HttpMethod.GET, LocalHttpUtil.GetAllXiaoquListUrl, new RequestCallBack<String>() {

          @Override
          public void onStart() {
          	LogUtils.i("Start to connect xiaoqu index...");
          }

          @Override
          public void onLoading(long total, long current, boolean isUploading) {
          	LogUtils.i("On loading to connect xiaoqu index: " + current + "/" + total);
          }

			@Override
			public void onFailure(HttpException error, String msg) {
				LogUtils.i("Error to connect xiaoqu index: " + msg);
			}

			@Override
			public void onSuccess(ResponseInfo<String> response) {
				LogUtils.i("Success to connect xiaoqu index: " + response.result.toString());
				String xiaoquListJsonstr = response.result.toString(); 
				
				Intent intent = new Intent();
			    intent.setClass(parentView, XiaoquSearchActivity.class);
			    intent.putExtra("xiaoquListJsonstr", xiaoquListJsonstr);
			    parentView.startActivity(intent); 
			}
		});
  } 
}

