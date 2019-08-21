package com.sunspot.pk;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * -------------------------------------
 * 作者：vitta
 * -------------------------------------
 * 时间：2019/8/20 上午11:40
 * -------------------------------------
 * 描述：PK活动投票view
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class PKBottomView extends FrameLayout {

    /**
     * 红蓝方站我按钮，红蓝方人数
     */
    private TextView redBtn, blueBtn, redCountTv, blueCountTv;
    /**
     * 红蓝方支持进度
     */
    private TiltProgressView leftProgress, rightProgress;
    /**
     * 红蓝方支持人数布局，站我布局，进度条布局
     */
    private View redSupportLL, blueSupportLL, voteLayout, progressContainer;

    private VoteListener voteListener;

    /**
     * 1、未开始
     * 2、活动中
     * 3、已结束
     * <p>
     * 1、处理好3种起始状态
     * 2、只有站我状态是可点击的，并且做动画
     */

    public PKBottomView(@NonNull Context context) {
        this(context, null);
    }

    public PKBottomView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PKBottomView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.act_pk_vote, this, true);
        voteLayout = findViewById(R.id.vote_layout);
        redBtn = (TextView) findViewById(R.id.red_tv);
        blueBtn = (TextView) findViewById(R.id.blue_tv);
        leftProgress = (TiltProgressView) findViewById(R.id.left_progress);
        rightProgress = (TiltProgressView) findViewById(R.id.right_progress);
        progressContainer = findViewById(R.id.progress_container);
        redSupportLL = findViewById(R.id.red_count_layout);
        blueSupportLL = findViewById(R.id.blue_count_layout);
        redCountTv = (TextView) findViewById(R.id.red_count_tv);
        blueCountTv = (TextView) findViewById(R.id.blue_count_tv);
        progressSetting(leftProgress);
        progressSetting(rightProgress);
    }

    private void progressSetting(TiltProgressView view) {
        view.setAngle(80);
        view.setDownColor(Color.TRANSPARENT);
    }

    public void setData() {
        //todo
        //根据活动状态展示不同的效果
        setActive(false,24,75);
    }

    /**
     * 未开始
     */
    private void setNotStarted() {
        stateVisibleSetting(true, false);
        redBtn.setText(R.string.not_started);
        blueBtn.setText(R.string.not_started);
        redBtn.setClickable(false);
        blueBtn.setClickable(false);
    }

    /**
     * 活动中
     *
     * @param hasVoted    是否自己投过票啦
     * @param redSupport  红方支持人数
     * @param blueSupport 蓝方支持人数
     */
    private void setActive(boolean hasVoted, int redSupport, int blueSupport) {
        if (hasVoted) {
            //已经投过票啦，展示投票进度
            updateVoteResult(redSupport, blueSupport);
        } else {
            //没投过票，展示按钮
            stateVisibleSetting(true, false);
            redBtn.setText(R.string.vote_for_me);
            blueBtn.setText(R.string.vote_for_me);
            redBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (voteListener != null) {
                        voteListener.onSupportRedClick(v);
                    }
                }
            });
            blueBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (voteListener != null) {
                        voteListener.onSupportBlueClick(v);
                    }
                }
            });
        }
    }

    /**
     * 已结束
     *
     * @param redSupport  红方支持人数
     * @param blueSupport 蓝方支持人数
     */
    private void setEnded(int redSupport, int blueSupport) {
        updateVoteResult(redSupport, blueSupport);
    }

    private void updateVoteResult(int redSupport, int blueSupport) {
        stateVisibleSetting(false, true);
        redCountTv.setText(String.format(getContext().getString(R.string.vote_people_count), String.valueOf(redSupport)));
        blueCountTv.setText(String.format(getContext().getString(R.string.vote_people_count), String.valueOf(blueSupport)));
        double totalCount = redSupport + blueSupport;
        leftProgress.setFraction(redSupport / totalCount);
        rightProgress.setFraction(blueSupport / totalCount);
    }

    private void stateVisibleSetting(boolean voteBtnShow, boolean progressShow) {
        voteLayout.setVisibility(voteBtnShow ? VISIBLE : GONE);
        progressContainer.setVisibility(progressShow ? VISIBLE : GONE);
        redSupportLL.setVisibility(progressShow ? VISIBLE : GONE);
        blueSupportLL.setVisibility(progressShow ? VISIBLE : GONE);
    }

    public void setVoteListener(VoteListener voteListener) {
        this.voteListener = voteListener;
    }

    public void doVoteAnim(int redSupportCount, int blueSupportCount) {
        if (redBtn.getVisibility() != VISIBLE || blueBtn.getVisibility() != VISIBLE) {
            return;
        }
        //btn渐隐 385ms
        animateBtn(redBtn);
        animateBtn(blueBtn);
        //间隔 385 + 231ms，进度条move 3080ms 先加速后减速
        float totalCount = redSupportCount + blueSupportCount;
        animateVoteProgress(leftProgress, redSupportCount / totalCount);
        animateVoteProgress(rightProgress, blueSupportCount / totalCount);
        //间隔 385 + 308ms，文字渐显，时间80ms
        animateVotePeople(redSupportLL);
        animateVotePeople(blueSupportLL);
        //间隔 385 + 1080ms，count[0-end]先加速后减速渐变，时间3080ms
        animateVoteCount(redCountTv, redSupportCount);
        animateVoteCount(blueCountTv, blueSupportCount);
    }

    /**
     * 按钮
     */
    private void animateBtn(View btn) {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(btn, "Alpha", 0f, 1f);
        alpha.setDuration(385);
        alpha.start();
    }

    /**
     * 进度条
     */
    private void animateVoteProgress(final TiltProgressView view, float endFraction) {
        view.setFraction(0);
        view.setVisibility(VISIBLE);
        ValueAnimator animator = ValueAnimator.ofFloat(0f, endFraction);
        animator.setDuration(3000);
        animator.setStartDelay(385 + 231);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setFraction((float) animation.getAnimatedValue());
            }
        });
        animator.start();
    }

    /**
     * 站队人数layout
     */
    private void animateVotePeople(View peopleLayout) {
        peopleLayout.setAlpha(0);
        peopleLayout.setVisibility(VISIBLE);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(peopleLayout, "Alpha", 0, 1f);
        alpha.setDuration(100);
        alpha.setStartDelay(385 + 308);
        alpha.start();
    }

    /**
     * 文字变化
     */
    private void animateVoteCount(final TextView view, int supportCount) {
        view.setText(" ");
        ValueAnimator animator = ValueAnimator.ofInt(0, supportCount);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setStartDelay(385 + 1080);
        animator.setDuration(3000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setText(String.valueOf(animation.getAnimatedValue()));
            }
        });
        animator.start();
    }

    interface VoteListener {

        void onSupportRedClick(View view);

        void onSupportBlueClick(View view);
    }
}
