package com.lhj.dragvideoview;

import android.os.Build;
import android.transition.ChangeBounds;
import android.transition.ChangeClipBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeScroll;
import android.transition.ChangeTransform;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionSet;

/**
 * Created by Administrator on 2018/1/19.
 */

public class DetailsTransition extends TransitionSet {
    public DetailsTransition() {
        setOrdering(ORDERING_TOGETHER);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            addTransition(new ChangeBounds()).//根据宽高变化
            addTransition(new ChangeTransform());//根据位置变化
//            addTransition(new ChangeImageTransform());
//            addTransition(new ChangeClipBounds());
//            addTransition(new ChangeScroll());
//            addTransition(new Explode());
//            addTransition(new Fade());
//            addTransition(new Slide());
        }
    }
}
