package com.example.liao.isuke.anim;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.liao.isuke.utils.UIUtils;


/**
 * Created by liao on 2017/11/27.
 */

public class LayoutAnimation {
    public static void setAnimation(View view, int time) {
        Animation animation = AnimationUtils.makeInAnimation(UIUtils.getContext(), true);
        animation.setDuration(time);
        view.setAnimation(animation);
    }

    public static void setAnimation(View view1, View view2, View view3) {
        setAnimation(view1, 500);
        setAnimation(view2, 600);
        setAnimation(view3, 700);
    }

    public static void setAnimation(View view1, View view2, View view3, View view4, View view5) {
        setAnimation(view1, 500);
        setAnimation(view2, 600);
        setAnimation(view3, 700);
        setAnimation(view4, 800);
        setAnimation(view5, 900);
    }
    public static void setAnimation(View view1, View view2, View view3, View view4) {
        setAnimation(view1, 500);
        setAnimation(view2, 600);
        setAnimation(view3, 700);
        setAnimation(view4, 800);
    }
}
