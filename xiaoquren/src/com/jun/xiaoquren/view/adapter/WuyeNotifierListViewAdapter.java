package com.jun.xiaoquren.view.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jun.xiaoquren.R;
import com.jun.xiaoquren.WuyeNotifierDetailActivity;
import com.jun.xiaoquren.dao.model.Document;
import com.jun.xiaoquren.dao.model.DocumentComment;
import com.jun.xiaoquren.http.LocalHttpUtil;
import com.jun.xiaoquren.view.model.DocumentViewHolder;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

public class WuyeNotifierListViewAdapter extends BaseAdapter {
	
	public static final String CLASSNAME = "WuyeNotifierListViewAdapter";

	// Declare Variables
	Activity mContext;
	LayoutInflater inflater;
	private List<Document> documentList = null;			// Current show documents, equals the copyDocumentArraylist when search inputs empty
	private ArrayList<Document> copyDocumentArraylist;  // Clone all the documents for search function

	public WuyeNotifierListViewAdapter(Activity context, List<Document> documentList) {
		this.mContext = context;
		documentList = documentList == null ? new ArrayList<Document>() : documentList;
		
		this.documentList = documentList;
		inflater = LayoutInflater.from(mContext);
		this.copyDocumentArraylist = new ArrayList<Document>();
		this.copyDocumentArraylist.addAll(documentList);
	}

	@Override
	public int getCount() {
		return documentList.size();
	}

	@Override
	public Document getItem(int position) {
		return documentList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/*
	 * View for each document
	 */
	public View getView(final int position, View view, ViewGroup parent) {
		final DocumentViewHolder documentHolder;
		if (view == null) {
			documentHolder = new DocumentViewHolder();
			view = inflater.inflate(R.layout.wuye_notifier_item, null);
			// Locate the TextViews in listview_item.xml
			documentHolder.id = (TextView) view.findViewById(R.id.document_item_id);
			documentHolder.title = (TextView) view.findViewById(R.id.document_item_title);
			documentHolder.subtitle = (TextView) view.findViewById(R.id.document_item_subtitle);
			view.setTag(documentHolder);
		} else {
			documentHolder = (DocumentViewHolder) view.getTag();
		}
		
		// Set the results into TextViews
		documentHolder.id.setText(documentList.get(position).getStringId());
		documentHolder.title.setText(documentList.get(position).getTitle());
		documentHolder.subtitle.setText(documentList.get(position).getCreatetime());
		
		// Document item click event
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				Document currentDocument = documentList.get(position);				
				LocalHttpUtil.getDefaultHttpUtils().send(HttpRequest.HttpMethod.GET, LocalHttpUtil.DocumentCommentsUrl+currentDocument.getId(), new RequestCallBack<String>() {

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
							
							Intent intent = new Intent(mContext, WuyeNotifierDetailActivity.class);
							intent.putExtra("commentListJsonstr", commentListJsonstr);
							intent.putExtra("document", documentList.get(position));
							mContext.startActivity(intent);
						}
					});
			}
		});

		return view;
	}

	/*
	 * Document search action
	 */
	public void filter(String charText) {
		charText = charText.toLowerCase(Locale.getDefault());
		documentList.clear();
		if (charText.length() == 0) {
			// Shows all documents when inputs empty
			documentList.addAll(copyDocumentArraylist);
		} else {
			// Shows the matches documents
			for (Document wp : copyDocumentArraylist) {
				if (wp.getTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
					documentList.add(wp);
				}
			}
		}
		notifyDataSetChanged();
	}

}