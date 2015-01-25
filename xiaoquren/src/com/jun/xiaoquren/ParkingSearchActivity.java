package com.jun.xiaoquren;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.jun.xiaoquren.util.LocalUtil;
import com.jun.xiaoquren.util.LocalViewUtil;
import com.jun.xiaoquren.util.MyAbstractActivity;
import com.jun.xiaoquren.view.adapter.ParkingSearchViewAdapter;

public class ParkingSearchActivity  extends MyAbstractActivity {

	public static final String CLASSNAME = "ParkingSearchActivity";

    @Override
	public String getActivityName() {
		return CLASSNAME;
	} 

	// Declare Variables
	ListView searchListView;
	ParkingSearchViewAdapter listViewAdapter;
	List<String> searchKeyList = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.parking_search);
		searchListView = (ListView) findViewById(R.id.listview);		
		searchKeyList.add(LocalViewUtil.Info_Search_Supply);
		searchKeyList.add(LocalViewUtil.Info_Search_Identity);
		searchKeyList.add(LocalViewUtil.Info_Search_Area);
		searchKeyList.add(LocalViewUtil.Info_Search_Price);

		// Pass results to ListViewAdapter Class
		listViewAdapter = new ParkingSearchViewAdapter(this, searchKeyList);
		
		// Binds the Adapter to the ListView
		searchListView.setAdapter(listViewAdapter);
		
		TextView searchBtn = (TextView) findViewById(R.id.search_button);
		searchBtn.setOnClickListener(new SearchBtnOnClickListener(ParkingSearchActivity.this));
	}
	
	// Not using options menu in this page
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    public void page_back(View v) {
		this.finish();
    }
    
    public void refreshPage() {
    	searchListView.setAdapter(listViewAdapter);
		listViewAdapter.notifyDataSetChanged();
    }
}

class SearchBtnOnClickListener implements View.OnClickListener {  

	ParkingSearchActivity parentView = null;

	public SearchBtnOnClickListener(ParkingSearchActivity parentView) { 
		this.parentView = parentView;
	}

	@Override  
	public void onClick(View v) {
		
//		Toast.makeText(parentView, "onSearchBtnClick!", Toast.LENGTH_LONG).show();
		
		if (LocalUtil.isActiveActivityExists(ParkingMainActivity.CLASSNAME)) {
			ParkingMainActivity page = (ParkingMainActivity)LocalUtil.getActiveActivity(ParkingMainActivity.CLASSNAME);
			page.refreshList();
		}
		
		parentView.finish();
	
//		LocalHttpUtil.getDefaultHttpUtils().send(HttpRequest.HttpMethod.GET, LocalHttpUtil.GetAllXiaoquListUrl, new RequestCallBack<String>() {
//		
//		  @Override
//		  public void onStart() {
//		  	LogUtils.i("Start to connect xiaoqu index...");
//		  }
//		
//		  @Override
//		  public void onLoading(long total, long current, boolean isUploading) {
//		  	LogUtils.i("On loading to connect xiaoqu index: " + current + "/" + total);
//		  }
//		
//			@Override
//			public void onFailure(HttpException error, String msg) {
//				LogUtils.i("Error to connect xiaoqu index: " + msg);
//			}
//		
//			@Override
//			public void onSuccess(ResponseInfo<String> response) {
//				LogUtils.i("Success to connect xiaoqu index: " + response.result.toString());
//				String xiaoquListJsonstr = response.result.toString(); 
//				
//				Intent intent = new Intent();
//			    intent.setClass(parentView, XiaoquSearchActivity.class);
//			    intent.putExtra("xiaoquListJsonstr", xiaoquListJsonstr);
//			    parentView.startActivity(intent); 
//			}
//		});
	}
}