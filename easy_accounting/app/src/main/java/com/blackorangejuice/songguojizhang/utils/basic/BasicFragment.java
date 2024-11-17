package com.blackorangejuice.songguojizhang.utils.basic;

import androidx.fragment.app.Fragment;

public abstract class BasicFragment extends Fragment {
    public abstract void init();

    public abstract void findView();

    public abstract void setListener();
}
