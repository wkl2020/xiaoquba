package com.jun.xiaoquren;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.jun.xiaoquren.dao.model.Document;
import com.jun.xiaoquren.http.JsonTools;
import com.jun.xiaoquren.util.MyAbstractActivity;
import com.jun.xiaoquren.view.adapter.WuyeNotifierListViewAdapter;

public class WuyeNotifierMainActivity extends MyAbstractActivity implements OnClickListener {
	
	public static final String CLASSNAME = "WuyeNotifierMainActivity";

    @Override
	public String getActivityName() {
		return CLASSNAME;
	} 

	// Declare Variables
	ListView documentListView;
	WuyeNotifierListViewAdapter listViewAdapter;
	EditText searchTextbox;
	List<Document> documentList = new ArrayList<Document>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wuye_notifier_main);

		// Locate the ListView in listview_main.xml
		documentListView = (ListView) findViewById(R.id.listview);
		
//		documentList = DocumentDao.findByXiaoquId(Integer.valueOf(LocalUtil.getCurrentXiaoQuId(WuyeNotifierMainActivity.this)));
		String xiaoquDocumentsJsonstr = (String)getIntent().getSerializableExtra("xiaoquDocumentsJsonstr");
		documentList = JsonTools.getDocumentList(xiaoquDocumentsJsonstr);

		// Pass results to ListViewAdapter Class
		listViewAdapter = new WuyeNotifierListViewAdapter(this, documentList);
		
		// Binds the Adapter to the ListView
		documentListView.setAdapter(listViewAdapter);
		
		// Locate the EditText in listview_main.xml
		searchTextbox = (EditText) findViewById(R.id.search);

		// Capture Text in EditText
		searchTextbox.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {
				String text = searchTextbox.getText().toString().toLowerCase(Locale.getDefault());
				listViewAdapter.filter(text);
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
			}
		});
		
//		Button btnBack = (Button) findViewById(R.id.btn_back);
//		btnBack.setFocusable(true);
//		btnBack.requestFocus();
	}

	// Not using options menu in this page
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
}