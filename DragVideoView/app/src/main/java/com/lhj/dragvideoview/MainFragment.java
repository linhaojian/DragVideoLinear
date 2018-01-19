package com.lhj.dragvideoview;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/1/18.
 */

public class MainFragment extends Fragment {
    private LinearLayout linear1,linear2,linear3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1,container,false);
        initView(view);
        initListeners();
        return view;
    }

    private void initView(View view) {
        linear1 = (LinearLayout)view.findViewById(R.id.linear1);
        linear2 = (LinearLayout)view.findViewById(R.id.linear2);
        linear3 = (LinearLayout)view.findViewById(R.id.linear3);
    }

    private void initListeners() {
        linear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnFragment(linear2);
            }
        });
    }

    private void turn(View view){
//        Intent intent = new Intent(MainActivity.this,DragVideoViewActivity.class);
//        ActivityOptions activityOptions = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            activityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,view,"dragview");
//        }
//        startActivity(intent,activityOptions.toBundle());
    }

    private void turnFragment(View view){
        DragVideoViewFragment dragVideoViewFragment = new DragVideoViewFragment();
        dragVideoViewFragment.setSharedElementEnterTransition(new DetailsTransition());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dragVideoViewFragment.setEnterTransition(new Fade());
        }
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment1, dragVideoViewFragment)
//                        .add(R.id.fragment1, dragVideoViewFragment)
                .addToBackStack(null)
                .addSharedElement(view, ViewCompat.getTransitionName(view))
                .commit();
    }

}
