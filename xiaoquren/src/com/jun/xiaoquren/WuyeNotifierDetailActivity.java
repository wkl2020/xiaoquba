package com.jun.xiaoquren;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jun.xiaoquren.dao.model.Document;
import com.jun.xiaoquren.dao.model.DocumentComment;
import com.jun.xiaoquren.http.JsonTools;
import com.jun.xiaoquren.http.LocalHttpUtil;
import com.jun.xiaoquren.util.LocalViewUtil;
import com.jun.xiaoquren.util.MyAbstractActivity;
import com.jun.xiaoquren.util.MyListView;
import com.jun.xiaoquren.view.adapter.CommentListViewAdapter;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

public class WuyeNotifierDetailActivity extends MyAbstractActivity implements OnClickListener {
	
	public static final String CLASSNAME = "WuyeNotifierDetailActivity";

    @Override
	public String getActivityName() {
		return CLASSNAME;
	} 
    
	// Declare Variables
	TextView id_text;
	TextView title_text;
	TextView subtitle_text;
	TextView content_text;
	TextView ower_name_text;
	TextView create_time_text;	
	
	// Declare Variables
	MyListView evaluationListView;
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
		evaluationListView = (MyListView) findViewById(R.id.evaluation_listview);
		listViewAdapter = new CommentListViewAdapter(this, commentList);
		evaluationListView.setAdapter(listViewAdapter);
		LocalViewUtil.setListViewHeightBasedOnChildren(evaluationListView);  
		
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
        popupwindow = new PopupWindow(customView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);  
        popupwindow.setAnimationStyle(R.style.AnimationFade);
        popupwindow.setOutsideTouchable(false);
        popupwindow.setBackgroundDrawable(new BitmapDrawable());
        popupwindow.setFocusable(true);//如果不加这个，Grid不会响应ItemClick
        popupwindow.setTouchable(true);
        
        popupwindow.setTouchInterceptor(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					
					System.out.println("onTouch: Y: " + event.getY() + ":X: " + event.getX());                   
                    if (event.getY() < 0 || event.getX() < 0){  //这里处理，当点击gridview以外区域的时候，菜单关闭
                        if (popupwindow.isShowing())
                        	popupwindow.dismiss();
                    }
                    return false;
				}
        });
    }  

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
            popupwindow.showAsDropDown(v, 5, 5);  
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