package com.jun.xiaoquren;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class BottomNaviTabsFragment extends Fragment  {
	
	public static final String CLASSNAME = "BottomNaviTabsFragment";
	
    protected static final String TAG = BottomNaviTabsFragment.class.getName();
    private static final String PREFS = "default_prefs";
    private static final String CURRENT_TAB = "current_tab";
    private Tab[] mTabs = new Tab[] {
            new Tab("Tab 1", "主页", R.drawable.home, R.id.tab1),
            new Tab("Tab 2", "待定1", R.drawable.favorite_bookmark, R.id.tab2),
            new Tab("Tab 3", "待定2", R.drawable.star, R.id.tab3),
            new Tab("Tab 4", "我", R.drawable.user, R.id.tab4)
    };

    private TabHost mTabHost;
    private LayoutInflater mFactory;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mFactory = LayoutInflater.from(activity);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        saveCurrentTab();
    }

    private void saveCurrentTab() {
        SharedPreferences sp = getActivity().getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        Editor ed = sp.edit();
        ed.putInt(CURRENT_TAB, mTabHost.getCurrentTab());
        ed.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.bottom_navigation_tabs, container, false);
        mTabHost = (TabHost) root.findViewById(android.R.id.tabhost);
        setupTabs(mTabHost);
        restoreCurrentTab();
        return root;
    }

    private void restoreCurrentTab() {
        SharedPreferences sp = getActivity().getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        final int current = sp.getInt(CURRENT_TAB, 0);
        mTabHost.setCurrentTab(current);
    }

    private void setupTabs(final TabHost tabHost) {
        tabHost.setup();
        for (final Tab tab : mTabs) {
            TabSpec spec = createTab(tabHost, tab);
            tabHost.addTab(spec);
            attachFragment(tab);
        }
        tabHost.setOnTabChangedListener(new OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                Log.i(TAG, "which is showing ? tab id " + tabId);
            }
        });
    }

    private void attachFragment(final Tab tab) {
        Fragment frag = new AppMainFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(tab.mStub, frag, tab.mTag);
        ft.commit();
    }

    private TabSpec createTab(final TabHost tabHost, final Tab tab) {
        TabSpec spec = tabHost.newTabSpec(tab.mTag);
        View tabItem = mFactory.inflate(R.layout.bottom_navigation_tab_item, null, false);
        
        ImageView icon = (ImageView) tabItem.findViewById(R.id.navigation_tab_icon);
        icon.setImageResource(tab.mIcon);
        
        TextView title = (TextView) tabItem.findViewById(R.id.navigation_tab_title);
        title.setText(tab.mTitle);
        
        spec.setIndicator(tabItem);
        spec.setContent(tab.mStub);
        
        return spec;
    }

    private class Tab {
        public String mTag;
        public String mTitle;
        public int mIcon;
        public int mStub;

        public Tab(final String tag, String title, int icon, int stub) {
            mTag = tag;
            mTitle = title;
            mIcon = icon;
            mStub = stub;
        }

        @Override
        public String toString() {
            return "[tag " + mTag + "]";
        }
    }
}
