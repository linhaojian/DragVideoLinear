package com.lhj.dragvideoview;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.ChangeClipBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeScroll;
import android.transition.ChangeTransform;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;

/**
 * Created by Administrator on 2018/1/17.
 */

public class DragVideoViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dragvideoview);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setExitTransition(new Fade());//退出
            getWindow().setEnterTransition(new Fade());//进入
            getWindow().setReenterTransition(new Fade());//再次进入
            getWindow().setReturnTransition(new Fade());//返回
        }
    }
}
