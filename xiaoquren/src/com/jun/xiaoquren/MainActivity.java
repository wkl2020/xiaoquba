package com.jun.xiaoquren;

import java.util.ArrayList;
import java.util.List;

import com.jun.xiaoquren.util.LocalUtil;
import com.jun.xiaoquren.util.MyAbstractActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends MyAbstractActivity {
	public static final String ACTIVITY_NAME = "MainActivity";

    @Override
	public String getActivityName() {
		return ACTIVITY_NAME;
	} 
    
	private static int TOTAL_COUNT = 2;
	 
    @Override
    public void onCreate(Bundle arg0) {
    	super.onCreate(arg0);    	
    	setContentView(R.layout.main_tab_weixin);
    	
    	String xiaoquName = LocalUtil.getCurrentXiaoQuName(this);
    	if (xiaoquName == null || xiaoquName.isEmpty()) {
    		xiaoquName = "新凯家园";
    	}
    	
    	TextView currentXiaoquName = (TextView)findViewById(R.id.current_xiaoqu_name);
    	currentXiaoquName.setText(xiaoquName);
    	
    	currentXiaoquName.setOnClickListener(new XiaoquNameOnClickListener(this, MainActivity.this));
    	
        // TODO Auto-generated method stub
//        super.onCreate(arg0);
////        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
//        setContentView(R.layout.view_pager_demo);
////        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebtn);  
//         
//        PagerTabStrip mPagerTabStrip=(PagerTabStrip) findViewById(R.id.pager_tabstrip);
//        
//        mPagerTabStrip.setTabIndicatorColorResource(android.R.color.white);
// 
//        ViewPager mViewPager = (ViewPager) findViewById(R.id.view_pager_demo);
//        ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();//
//        ArrayList<String> titleList = new ArrayList<String>();// 
////        for (int i = 0; i < TOTAL_COUNT; i++) {
////            ViewPagerFragment mViewPagerFragment = new ViewPagerFragment();
////            Bundle bundle = new Bundle();
////            bundle.putInt("upImageId", R.drawable.normal_icon);// ͼƬ
////            bundle.putString("text", "Page " + i);// ����
////            mViewPagerFragment.setArguments(bundle);// ���ò���
////            titleList.add("Title " + (i+1));
////            fragmentList.add(mViewPagerFragment);
////        }
//        
//        // 物业服务
//        ViewPagerFragment mViewPagerFragment = new ViewPagerFragment();
//        Bundle bundle = new Bundle();
//        bundle.putInt("upImageId", R.drawable.normal_icon);//
//        bundle.putString("text", "Page 1");
//        bundle.putString("viewpage", "main_tab_weixin");
//        mViewPagerFragment.setArguments(bundle);//
//        titleList.add("物业服务");
//        fragmentList.add(mViewPagerFragment);
//        
//        // 周边服务
//        mViewPagerFragment = new ViewPagerFragment();
//        bundle = new Bundle();
//        bundle.putInt("upImageId", R.drawable.normal_icon);//
//        bundle.putString("text", "Page 2");//
//        bundle.putString("viewpage", "main_tab_friends");
//        mViewPagerFragment.setArguments(bundle);//
//        titleList.add("周边服务");
//        fragmentList.add(mViewPagerFragment);
//        
//        mViewPager.setAdapter(new MyPagerFragmentAdapter(
//                getSupportFragmentManager(), fragmentList, titleList));
 
    }
    
    public void refreshCurrentXiaoQuName() {
    	String xiaoquName = LocalUtil.getCurrentXiaoQuName(this);
    	if (xiaoquName == null || xiaoquName.isEmpty()) {
    		xiaoquName = "新凯家园";
    	}
    	
    	TextView currentXiaoquName = (TextView)findViewById(R.id.current_xiaoqu_name);
    	currentXiaoquName.setText(xiaoquName);
    }
    
    public void personalsettings(View v) {  

//    	AlertDialog.Builder builder = new AlertDialog.Builder(this);  
//        builder.setTitle("个人中心");  
//        builder.setMessage("个人中心 \n");  
//        builder.setNegativeButton("确定",null);  
//        builder.show();  
        
        Intent intent = new Intent();
		intent.setClass(MainActivity.this, PersonalSettingActivity.class);
		startActivity(intent);
    }
    
    //
//    private class MyPagerFragmentAdapter extends FragmentPagerAdapter {
// 
//        private List<Fragment> fragmentList;
//        private List<String> titleList;
// 
//        public MyPagerFragmentAdapter(FragmentManager fm,
//                List<Fragment> fragmentList, List<String> titleList) {
//            super(fm);
//            this.fragmentList = fragmentList;
//            this.titleList = titleList;
//        }
// 
//        // 
//        @Override
//        public Fragment getItem(int arg0) {
//            // TODO Auto-generated method stub
//            return (fragmentList == null || fragmentList.size() == 0) ? null
//                    : fragmentList.get(arg0);
//        }
// 
//        // 
//        @Override
//        public CharSequence getPageTitle(int position) {
//            // TODO Auto-generated method stub
//            return (titleList.size() > position) ? titleList.get(position) : "";
//        }
// 
//        @Override
//        public int getCount() {
//            // TODO Auto-generated method stub
//            return fragmentList == null ? 0 : fragmentList.size();
//        }
// 
//    }
    
//    public static class ViewPagerFragment extends Fragment {
//        public ViewPagerFragment() {
//            super();
//        }
// 
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                Bundle savedInstanceState) {
//        	
//        	Bundle bundle = getArguments();
//        	String viewpage = "";
//        	if (bundle != null) {
//        		 viewpage = bundle.getString("viewpage");
//        	}
//        	
//        	View view = null;
//        	if (viewpage.equals("main_tab_weixin")) {
//        		
//        		view = inflater.inflate(R.layout.main_tab_weixin, container, false);        		
//        	} else if (viewpage.equals("main_tab_friends")) {
//        		
//        		view = inflater.inflate(R.layout.main_tab_friends, container, false);
//        	}
//        	
//        	return view;
//        	
////            View view = inflater.inflate(R.layout.view_pager_fragment,
////                    container, false);
////            TextView tv = (TextView) view.findViewById(R.id.view_pager_text);
////            ImageView image = (ImageView) view
////                    .findViewById(R.id.view_pager_image);
//// 
////            
////            if (bundle != null) {
////               
////                int upImageId = bundle.getInt("upImageId");
////                if (upImageId != 0) {
////                    image.setImageResource(upImageId);
////                }
////               
////                String text = bundle.getString("text");
////                tv.setText(text);
////            }
////            return view;
//        }
//    }

}

class XiaoquNameOnClickListener implements View.OnClickListener {  
	
	MainActivity parentView = null;
	Context context = null;

    public XiaoquNameOnClickListener(MainActivity parentView, Context context) { 
    	this.parentView = parentView;
    	this.context = context;
    }
    
  @Override  
  public void onClick(View v) {
	  
	  Intent intent = new Intent();
	  intent.setClass(parentView, XiaoQuSearchActivity.class);
	  parentView.startActivity(intent); 
  }     
      
//    @Override  
//    public void onClick(View v) {  
//    	AlertDialog.Builder builder = new AlertDialog.Builder(this.parentView);  
//        builder.setTitle("点击小区名");  
//        builder.setMessage("点击小区名 \n");  
//        builder.setNegativeButton("确定",null);  
//        builder.show();  
//    }  
}
