package com.blackorangejuice.songguojizhang.utils.view;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

/**
 * 给标签文字设置图片
 */
public class TextViewDrawable {
    // 调试所用颜色,注意前两位是透明度
    public static final int BLUE = 0xFF0099CC;
    public static final int ORANGE = 0xFF99CC66;
    public static final int BROWN = 0xFFCC9933;
    public static final int GREEN = 0xFF33CC33;
    public static final int RED = 0xFFFF6666;
    public static GradientDrawable getDrawable(int color){
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setColor(color);
        return drawable;
    }
}
