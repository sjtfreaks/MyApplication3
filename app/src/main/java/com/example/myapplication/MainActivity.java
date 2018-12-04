package com.example.myapplication;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.myapplication.adapter.ViewPagerAdapter;
import com.example.myapplication.fragment.HotFragment;
import com.example.myapplication.fragment.NewsFragment;
import com.example.myapplication.fragment.OneFragment;
import com.example.myapplication.fragment.UserFragment;
import com.example.myapplication.util.BottomNavigationViewHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private ViewPager mViewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private MenuItem menuItem;
    private List<Fragment> mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomView);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        mViewPager = (ViewPager) findViewById(R.id.vp);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem = bottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(viewPagerAdapter);
        mFragment = new ArrayList<>();
        mFragment.add(OneFragment.newInstance("首页"));
        mFragment.add(NewsFragment.newInstance("新闻"));
        mFragment.add(HotFragment.newInstance("热点"));
        mFragment.add(UserFragment.newInstance("个人"));
        viewPagerAdapter.setList(mFragment);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            menuItem = item;
            switch (item.getItemId()) {
                case R.id.ic_home:
                    mViewPager.setCurrentItem(0);
                    return true;
                case R.id.ic_one:
                    mViewPager.setCurrentItem(1);
                    return true;
                case R.id.ic_two:
                    mViewPager.setCurrentItem(2);
                    return true;
                case R.id.ic_user:
                    mViewPager.setCurrentItem(3);
                    return true;
            }
            return false;
        }
    };
}
