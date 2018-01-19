package com.lhj.dragvideoview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2018/1/17.
 */

public class DragVideoView extends LinearLayout {
    private static String TAG = "linhaojian";
    private static float scaleMinCoefficient = 0.5f;
    private float downX,downY,moveX,moveY,width,height;
    private float scale = 1,fade = 1,scaleDis;
    private View dragscaleView,dragsildefadeView;
    private float dragscaleViewOrginH,dragscaleViewOrginW,dragscaleViewchangeH;
    private ValueAnimator valueAnimator;
    private boolean isDrag,isScale;

    public DragVideoView(Context context){
        super(context);
    }

    public DragVideoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DragVideoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DragVideoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        dragscaleView = getChildAt(0);
        dragsildefadeView = getChildAt(1);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if(dragscaleViewOrginH==0){
            dragscaleViewOrginH = dragscaleView.getHeight();
            dragscaleViewOrginW = dragscaleView.getWidth();
            LinearLayout.LayoutParams layoutParams = (LayoutParams) dragsildefadeView.getLayoutParams();
            layoutParams.height = (int) (height-dragscaleViewOrginH);
            dragsildefadeView.setLayoutParams(layoutParams);
            scaleDis = layoutParams.height;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            if(!isDrag){
                downX = event.getX();
                downY = event.getY();
                if(isScale){//打开
                    if(downY>(height-dragscaleView.getHeight())&&downX>(width-dragscaleView.getWidth())){
                        isDrag = true;
                        return true;
                    }
                }else{//收缩
                    if(downY<dragscaleView.getHeight()){
                        isDrag = true;
                        return true;
                    }
                }
            }
        }else if(event.getAction()==MotionEvent.ACTION_MOVE){
            dealMove(event.getY());
        }else if(event.getAction()==MotionEvent.ACTION_UP){
            upAnim(event.getY());
        }
        return false;
    }

    private void dealMove(float y){
        LinearLayout.LayoutParams layoutParams = (LayoutParams) dragscaleView.getLayoutParams();
        if(isScale){
            moveY = downY - y;
            if(moveY<0){
                moveY=0;
            }else if(moveY>scaleDis){
                moveY = scaleDis;
            }
            scale = 1- ((moveY*scaleMinCoefficient/scaleDis));
            fade = ((moveY/scaleDis));
            layoutParams.leftMargin = (int) (dragscaleViewOrginW * (scale - scaleMinCoefficient));
            layoutParams.height = (int) (dragscaleViewchangeH / (scale));
            layoutParams.topMargin = (int) ((height - layoutParams.height - moveY));
            dragsildefadeView.setAlpha(fade);
        }else{
            moveY = y - downY;
            if(moveY<0){
                moveY=0;
            }else if(moveY>scaleDis){
                moveY = scaleDis;
            }
            scale = ((moveY*scaleMinCoefficient/scaleDis));
            fade = (moveY/scaleDis);
            layoutParams.topMargin = (int) (moveY+((dragscaleViewOrginH * (scale))));
            layoutParams.leftMargin = (int) (dragscaleViewOrginW * (scale));
            layoutParams.height = (int) (dragscaleViewOrginH * (1-scale));
            dragscaleView.setLayoutParams(layoutParams);
            dragscaleViewchangeH = layoutParams.height;
            dragsildefadeView.setAlpha(1-fade);
        }
        dragscaleView.setLayoutParams(layoutParams);
    }

    private void upAnim(float y){
        if(isScale){
            if(y<(height/2.0f)){//往上收
                valueAnimator = ValueAnimator.ofFloat(y,0);
            }else{//往下展开
                valueAnimator = ValueAnimator.ofFloat(y,height);
            }

            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float anim = (float) animation.getAnimatedValue();
                    dealMove(anim);
                    if(anim==0){
                        isDrag = false;
                        isScale = false;
                    }else if(anim==(height)){
                        isDrag = false;
                        isScale = true;
                    }
                }
            });
        }else{
            if(y<(height/2.0f)){//往上收
                valueAnimator = ValueAnimator.ofFloat(y,downY);
            }else{//往下展开
                valueAnimator = ValueAnimator.ofFloat(y,height);
            }

            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float anim = (float) animation.getAnimatedValue();
                    dealMove(anim);
                    if(anim==downY){
                        isDrag = false;
                        isScale = false;
                    }else if(anim==height){
                        isDrag = false;
                        isScale = true;
                    }
                }
            });
        }
        valueAnimator.setDuration(100);
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.start();

    }

}
