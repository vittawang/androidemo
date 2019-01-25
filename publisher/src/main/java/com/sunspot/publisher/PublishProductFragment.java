package com.sunspot.publisher;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by duanbo on 17/11/16.
 */

public class PublishProductFragment extends DialogFragment implements View.OnClickListener {
    private String REQUEST_PAGE = "Android_PublishProductFragment";

    private View createLiveRoomLayout;
    private View createVideoLayout;
    private View createNovelLayout;
    private View cancelBtn;
    private int from;

    public static void showPublishProductFragment(FragmentActivity activity , int from) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment prev = fragmentManager.findFragmentByTag("PublishProductFragment");
        if (prev != null) {
            ft.remove(prev);
        }
        PublishProductFragment publishProductFragment = new PublishProductFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("from", from);
        //fragment保存参数，传入一个Bundle对象
        publishProductFragment.setArguments(bundle);
        ft.add(publishProductFragment, "PublishProductFragment");
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.LibraryDialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_publish_product, container, false);
        initWidget(view);
        return view;
    }

    private void initWidget(View view) {
        createLiveRoomLayout = view.findViewById(R.id.create_live_room_layout);
        createLiveRoomLayout.setOnClickListener(this);

        createVideoLayout = view.findViewById(R.id.create_video_layout);
        createVideoLayout.setOnClickListener(this);

        createNovelLayout = view.findViewById(R.id.create_novel_layout);
        createNovelLayout.setOnClickListener(this);

        cancelBtn = view.findViewById(R.id.cancel_btn);
        cancelBtn.setOnClickListener(this);

        from = getArguments().getInt("from");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_live_room_layout:
                //开直播
//                CreateLiveActivity.launch(getActivity());
                dismiss();
                break;
            case R.id.create_novel_layout:
                //写小说
                final FragmentActivity context = getActivity();
//                StoryCreateActivity.launch(context);
                dismiss();
                break;
            case R.id.create_video_layout:
                //拍视频
                final FragmentActivity contextvideo = getActivity();
//                NewSelectMaterialActivity.launch(contextvideo, from);
                dismiss();
                break;
            case R.id.cancel_btn:
                //取消
                dismiss();
                break;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Dialog dialog = getDialog();
        Window window = dialog.getWindow();
        if (window != null) {
            window.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置
            window.setWindowAnimations(R.style.LibraryAnimFade_two);  //添加动画
            window.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            window.setDimAmount(0.3f);
        }
        createLiveRoomLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                showAnimation(createVideoLayout, 860);

                ValueAnimator anim = ObjectAnimator.ofFloat(cancelBtn, "TranslationY", CommonUtils.dip2px(getContext(), 32), CommonUtils.dip2px(getContext(), 0));
                anim.setDuration(200);
                anim.start();
                createLiveRoomLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showAnimation(createLiveRoomLayout, 660);
                    }
                }, 80);
                createLiveRoomLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showAnimation(createNovelLayout, 660);
                    }
                }, 100);
            }
        }, 300);


    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void showAnimation(View view, int duration) {
        ValueAnimator anim = ObjectAnimator.ofFloat(view, "TranslationY", CommonUtils.dip2px(getContext(), 50), CommonUtils.dip2px(getContext(), 15));
        anim.setInterpolator(new PublishProductInterpolator());
        anim.setDuration(duration);
        anim.start();
    }
}
