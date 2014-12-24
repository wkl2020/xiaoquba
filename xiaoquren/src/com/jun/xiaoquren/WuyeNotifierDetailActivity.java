package com.jun.xiaoquren;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jun.xiaoquren.dao.model.Document;
import com.jun.xiaoquren.dao.model.DocumentComment;
import com.jun.xiaoquren.http.JsonTools;
import com.jun.xiaoquren.http.LocalHttpUtil;
import com.jun.xiaoquren.util.MyAbstractActivity;
import com.jun.xiaoquren.view.adapter.CommentListViewAdapter;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

public class WuyeNotifierDetailActivity extends MyAbstractActivity implements OnClickListener{
	public static final String ACTIVITY_NAME = "WuyeNotifierDetailActivity";

    @Override
	public String getActivityName() {
		return ACTIVITY_NAME;
	} 
    
	// Declare Variables
	TextView id_text;
	TextView title_text;
	TextView subtitle_text;
	TextView content_text;
	TextView ower_name_text;
	TextView create_time_text;	
	
	// Declare Variables
	ListView evaluationListView;
	CommentListViewAdapter listViewAdapter;
	List<DocumentComment> commentList = new ArrayList<DocumentComment>();
	
	// Popup Window
	private PopupWindow popupwindow; 
	
	public void setEvaluationList(List<DocumentComment> list) {
		this.commentList = list;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wuye_notifier_detail);
		// Retrieve data from MainActivity on item click event
		Document document = (Document)getIntent().getSerializableExtra("document");

		// Locate the TextViews in singleitemview.xml
		id_text = (TextView) findViewById(R.id.document_detail_id);
		title_text = (TextView) findViewById(R.id.document_detail_title);
		subtitle_text = (TextView) findViewById(R.id.document_detail_subtitle);
		content_text = (TextView) findViewById(R.id.document_detail_content);
		ower_name_text = (TextView) findViewById(R.id.document_detail_ower_name);
		create_time_text = (TextView) findViewById(R.id.document_detail_create_time);

		// Load the results into the TextViews
		id_text.setText(document.getStringId());
		title_text.setText(document.getTitle());
		subtitle_text.setText(document.getCreatetime());
		content_text.setText(document.getContent());
		ower_name_text.setText(document.getOwner());
		create_time_text.setText(document.getCreatetime());
		
		// Init the comment list view
		String commentListJsonstr = (String)getIntent().getSerializableExtra("commentListJsonstr");
		commentList = JsonTools.getCommentList(commentListJsonstr);
		evaluationListView = (ListView) findViewById(R.id.evaluation_listview);
		listViewAdapter = new CommentListViewAdapter(this, commentList);
		evaluationListView.setAdapter(listViewAdapter);
		
		// Init the popupwindow
		initmPopupWindowView();
	}
	
	public void onAddCommentPopClick(View v) {
		System.out.println("####################### onAddCommentPopClick ################################");
		
		Intent intent = new Intent();
		intent.setClass(WuyeNotifierDetailActivity.this, WuyeNotifierCommentAddActivity.class);
	    intent.putExtra("documentId", id_text.getText());
	    startActivity(intent);   
	}
	
	 public void initmPopupWindowView() {  
		  
	        View customView = getLayoutInflater().inflate(R.layout.wuye_notifier_comment_pop, null, false);  
	        popupwindow = new PopupWindow(customView, 200, 130);  
	        popupwindow.setAnimationStyle(R.style.AnimationFade);
	        
	        popupwindow.setTouchable(true);
	        
//	        popupwindow.setTouchInterceptor(new OnTouchListener() {
//
//	            @Override
//	            public boolean onTouch(View v, MotionEvent event) {
//
//	            	LogUtils.i("popupwindow onTouch event ");
//	                return false;
//	                // 这里如果返回true的话，touch事件将被拦截, 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
//	            }
//	        });

	        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
	        // 我觉得这里是API的一个bug
	        popupwindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_menu_item));
	        popupwindow.setOutsideTouchable(true);
	        
//	        popupwindow.setBackgroundDrawable(new BitmapDrawable());
            
            
//	        customView.setOnTouchListener(new OnTouchListener() {  
//	  
//	            @Override  
//	            public boolean onTouch(View v, MotionEvent event) {  
//	                if (popupwindow != null && popupwindow.isShowing()) {  
//	                    popupwindow.dismiss();  
//	                    popupwindow = null;  
//	                }
////	                return false;  
//	                return super.onTouchEvent(event);
//	            }  
//	        });
	  
	    }  
	
//	@Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.wuye_notifier_detail_menu, menu);
//        mMenu = menu;
//        mMenuButtonAddComment= menu.findItem(R.id.add_comment_menu);
//        return true;
//    }
//
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        configureMenu(menu);
//        return true;
//    }
    
//    private void configureMenu(Menu menu) {
//        if (menu == null) {
//            return;
//        }
//        menu.findItem(R.id.add_comment_menu).setVisible(true);
//    }
//    
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int itemId = item.getItemId();
//        switch (itemId) {
//            case R.id.add_comment_menu: {
//                onAddCommentClicked();
//                return true;
//            }
//            default: {
//                return super.onOptionsItemSelected(item);
//            }
//        }
//    }
//    
//    public void onAddCommentClicked() {
//    	System.out.println("####################### onAddCommentClicked ################################");
//    }

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btn_back:
			finish();
			break;
		}
	}

    public void page_back(View v) {
		this.finish();
    }
    
    public void onPopupButtonClicked(View v) {
		if (popupwindow != null && popupwindow.isShowing()) {  
            popupwindow.dismiss();  
            return;  
        } else {
            popupwindow.showAsDropDown(v, 0, 5);  
        }  
    }
    
    public void refreshCommentList() {
    	LocalHttpUtil.getDefaultHttpUtils().send(HttpRequest.HttpMethod.GET, LocalHttpUtil.DocumentCommentsUrl+id_text.getText(), new RequestCallBack<String>() {

	          @Override
	          public void onStart() {
	          	LogUtils.i("Start to connect comment documentComments...");
	          }

	          @Override
	          public void onLoading(long total, long current, boolean isUploading) {
	          	LogUtils.i("On loading to connect comment documentComments: " + current + "/" + total);
	          }

				@Override
				public void onFailure(HttpException error, String msg) {
					LogUtils.i("Error to connect comment documentComments: " + msg);
				}

				@Override
				public void onSuccess(ResponseInfo<String> response) {
					LogUtils.i("Success to connect comment documentComments: " + response.result.toString());
					String commentListJsonstr = response.result.toString();
					commentList = JsonTools.getCommentList(commentListJsonstr);
					listViewAdapter.setItem(commentList);
					
					LogUtils.i("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx " + response.result.toString());
					
					evaluationListView.setAdapter(listViewAdapter);
					listViewAdapter.notifyDataSetChanged();
//					Intent intent = new Intent(mContext, WuyeNotifierDetailActivity.class);
//					intent.putExtra("commentListJsonstr", commentListJsonstr);
//					intent.putExtra("document", documentList.get(position));
//					mContext.startActivity(intent);
				}
			});
    }
}