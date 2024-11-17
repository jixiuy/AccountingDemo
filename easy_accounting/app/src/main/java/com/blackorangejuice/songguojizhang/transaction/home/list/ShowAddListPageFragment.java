package com.blackorangejuice.songguojizhang.transaction.home.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.transaction.home.list.in.account.edit.AddEditAccountPageActivity;
import com.blackorangejuice.songguojizhang.transaction.home.list.in.account.show.ShowAccountListPageFragment;
import com.blackorangejuice.songguojizhang.transaction.home.list.in.event.edit.AddEditEventPageActivity;
import com.blackorangejuice.songguojizhang.transaction.home.list.in.event.show.ShowEventListPageFragment;
import com.blackorangejuice.songguojizhang.utils.basic.BasicFragment;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalConstant;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ShowAddListPageFragment extends BasicFragment {
    private View thisView;
    private TextView accountTextView;
    private TextView eventTextView;
    FloatingActionButton floatingActionButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        thisView = inflater.inflate(R.layout.show_add_list_page, container, false);
        return thisView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findView();
        init();
        setListener();
    }

    /**
     * 每次当前页面可见时判断
     */
    @Override
    public void onResume() {
        super.onResume();
        refreshCurrentPage();
    }

    /**
     * 替换碎片
     *
     * @param fragment
     */
    protected void replaceFragment(Fragment fragment) {
        // 这里更换为子碎片管理器
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.show_add_list_page_fragment, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void init() {
        refreshCurrentPage();
        // 默认记账页面
        replaceFragment(new ShowAccountListPageFragment());

    }

    @Override
    public void findView() {

        accountTextView = thisView.findViewById(R.id.show_add_list_page_account_textview);
        eventTextView = thisView.findViewById(R.id.show_add_list_page_event_textview);
        // 新增按钮
        floatingActionButton = thisView.findViewById(R.id.show_add_list_page_add_button);
    }

    @Override
    public void setListener() {
        // 绑定监听器
        accountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalInfo.currentAddPage = GlobalConstant.ADD_PAGE_ACCOUNT;
                refreshCurrentPage();
            }
        });
        eventTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalInfo.currentAddPage = GlobalConstant.ADD_PAGE_EVENT;
                refreshCurrentPage();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (GlobalInfo.currentAddPage) {
                    case GlobalConstant.ADD_PAGE_ACCOUNT:
                        // 跳到新增账本界面
                        AddEditAccountPageActivity.startThisActivity(getActivity(),"");
                        break;
                    case GlobalConstant.ADD_PAGE_EVENT:
                        // 跳到新增事件界面
                        AddEditEventPageActivity.startThisActivity(getActivity(),"");
                        break;
                }
            }
        });
    }

    /**
     * 根据当前全局信息中的当前添加页面来设置碎片中要展示的页面
     */
    private void refreshCurrentPage() {
        switch (GlobalInfo.currentAddPage) {
            case GlobalConstant.ADD_PAGE_ACCOUNT:
                // 更换标签颜色
                accountTextView.setTextColor(0xff323232);
                eventTextView.setTextColor(0xff808080);
                // 切换碎片
                replaceFragment(new ShowAccountListPageFragment());
                break;
            case GlobalConstant.ADD_PAGE_EVENT:
                // 更换标签颜色
                eventTextView.setTextColor(0xff323232);
                accountTextView.setTextColor(0xff808080);
                // 切换碎片
                replaceFragment(new ShowEventListPageFragment());
                break;
        }
    }

}
