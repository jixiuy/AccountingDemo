package com.blackorangejuice.songguojizhang.transaction.home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.transaction.home.myinfo.ShowMyInfoPageFragment;
import com.blackorangejuice.songguojizhang.transaction.home.list.ShowAddListPageFragment;
import com.blackorangejuice.songguojizhang.transaction.home.overview.ShowOverviewPageFragment;
import com.blackorangejuice.songguojizhang.utils.basic.BasicActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePageActivity extends BasicActivity {
    // 三个页面的常量配置
    private final int OVER_PAGE_NUM = 0;
    private final int ADD_LIST_PAGE_NUM = 1;
    private final int MY_INFO_PAGE_NUM = 2;

    // 底部导航栏
    private BottomNavigationView homePageNav;

    // 默认选择的页面
    private int defultSelectId = OVER_PAGE_NUM;

    // 填充内容的布局
    private int replaceFragmentLayout = R.id.home_page_fragment_layout;


    /**
     * 启动此活动
     *
     * @param context
     */
    public static void startThisActivity(Context context) {
        Intent intent = new Intent(context, HomePageActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        findView();
        init();
        setListener();


    }

    @Override
    public void init() {
        // 打开默认界面
        replaceFragment(getDefultFragment());
        homePageNav.setSelectedItemId(defultSelectId);
    }

    @Override
    public void findView() {
        homePageNav = findViewById(R.id.home_page_nav);
    }

    @Override
    public void setListener() {
        // 为底部导航栏添加监听器
        homePageNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_page_nav_menu_item_overview:
                        replaceFragment(new ShowOverviewPageFragment());
                        break;
                    case R.id.home_page_nav_menu_item_addlist:
                        replaceFragment(new ShowAddListPageFragment());
                        break;
                    case R.id.home_page_nav_menu_item_myinfo:
                        replaceFragment(new ShowMyInfoPageFragment());
                        break;
                }
                return true;
            }
        });
    }

    /**
     * 替换碎片
     *
     * @param fragment
     */
    protected void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(replaceFragmentLayout, fragment);
        fragmentTransaction.commit();
    }

    /**
     * 获取当前设置的默认界面
     *
     * @return
     */
    private Fragment getDefultFragment() {
        switch (defultSelectId) {
            case OVER_PAGE_NUM:
                return new ShowOverviewPageFragment();
            case ADD_LIST_PAGE_NUM:
                return new ShowAddListPageFragment();
            case MY_INFO_PAGE_NUM:
                return new ShowMyInfoPageFragment();
            default:
                return new ShowOverviewPageFragment();
        }
    }


}