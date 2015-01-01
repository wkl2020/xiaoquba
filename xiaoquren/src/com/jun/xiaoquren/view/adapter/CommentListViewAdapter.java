package com.jun.xiaoquren.view.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jun.xiaoquren.R;
import com.jun.xiaoquren.dao.model.DocumentComment;
import com.jun.xiaoquren.view.model.CommentViewHolder;

public class CommentListViewAdapter extends BaseAdapter {

	public static final String CLASSNAME = "CommentListViewAdapter";
	
	// Declare Variables
	Activity mContext;
	LayoutInflater inflater;
	private List<DocumentComment> commentList = null;			// Current shows comment list, equals with the copycommentArraylist when search inputs is empty
	private ArrayList<DocumentComment> copycommentArraylist;  // All comment list

	public CommentListViewAdapter(Activity context, List<DocumentComment> commentList) {
		this.mContext = context;
		this.commentList = commentList;
		inflater = LayoutInflater.from(mContext);
		this.copycommentArraylist = new ArrayList<DocumentComment>();
		this.copycommentArraylist.addAll(commentList);
	}

	@Override
	public int getCount() {
		return commentList.size();
	}

	@Override
	public DocumentComment getItem(int position) {
		return commentList.get(position);
	}
	
	public void setItem(List<DocumentComment> commentList) {
		this.commentList = commentList;
		this.copycommentArraylist = new ArrayList<DocumentComment>();
		this.copycommentArraylist.addAll(commentList);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup parent) {
		final CommentViewHolder holder;
		if (view == null) {
			holder = new CommentViewHolder();
			view = inflater.inflate(R.layout.wuye_notifier_comment_item, null);
			// Locate the TextViews in listview_item.xml
			holder.nickname = (TextView) view.findViewById(R.id.visitor_nickname);
			holder.content = (TextView) view.findViewById(R.id.evaluation_content);
			holder.createDate = (TextView) view.findViewById(R.id.evaluation_create_time);
			view.setTag(holder);
		} else {
			holder = (CommentViewHolder) view.getTag();
		}
		
		// Set the results into TextViews
		holder.nickname.setText(commentList.get(position).getNickname());
		holder.content.setText(commentList.get(position).getContent());
		holder.createDate.setText(commentList.get(position).getCreateDate());

		return view;
	}
}
