package com.xohealth.club.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xohealth.club.R;
import com.xohealth.club.net.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc :
 * Created by xulc on 2018/12/22.
 */
public class FixTabIndicator extends LinearLayout implements View.OnClickListener {
    private float indicatorHeight;//指示器高度
    private int indicatorWidth; //指示器宽度
    private int indicatorPosition; //指示器当前位置
    private List<TextView> textViews;
    private Paint mPaint;

    private int startX = 0;
    private ValueAnimator animator;
    private TabClickListener tabClickListener;

    public FixTabIndicator(Context context) {
        this(context, null);
    }

    public FixTabIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FixTabIndicator);
        int indicatorColor = array.getColor(R.styleable.FixTabIndicator_tabIndicatorColor, ContextCompat.getColor(context, R.color.colorPrimary));//指示器颜色
        indicatorPosition = array.getInteger(R.styleable.FixTabIndicator_tabIndicatorPosition, 0);
        indicatorHeight = array.getDimension(R.styleable.FixTabIndicator_tabIndicatorHeight,6);//读取到的是px
        array.recycle();
        textViews = new ArrayList<>();
        mPaint = new Paint();
        mPaint.setColor(indicatorColor);
        mPaint.setStrokeWidth(indicatorHeight);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //把布局中的TextView统计到集合
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (!(child instanceof TextView)) {
                throw new RuntimeException("FixTabIndicator的子控件必须为TextView！");
            }
            textViews.add((TextView) child);
        }

        for (int i = 0; i < textViews.size(); i++) {
            TextView textView = textViews.get(i);
            mPaint.setTextSize(textView.getTextSize());
            //计算指示器的宽度
            indicatorWidth = (int) Math.max(indicatorWidth, mPaint.measureText(textView.getText().toString()));
            textView.setTag(i);
            textView.setOnClickListener(this);
        }

    }

    /**
     * 设置tab项点击监听
     * @param tabClickListener
     */
    public void setTabClickListener(TabClickListener tabClickListener) {
        this.tabClickListener = tabClickListener;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (startX == 0) {
            startX = (getWidth() / textViews.size() - indicatorWidth) / 2 + indicatorPosition * getWidth() / textViews.size();
            textViews.get(0).setSelected(true);
        }
        int stopX = startX + indicatorWidth;
        canvas.drawLine(startX, getHeight() - indicatorHeight/2, stopX, getHeight() - indicatorHeight/2, mPaint);
    }

    /**
     * 设置指示器当前位置
     * @param newPosition
     */
    public void setSelectPosition(final int newPosition) {
        if (newPosition == indicatorPosition)
            return;
        if (newPosition >= textViews.size()) {
            Log.i(Constant.TAG,"FixTabIndicator下标越界！");
            return;
        }
        if (animator != null && animator.isRunning()) {
            animator.cancel();
        }
        //生成当前的position和上一次的position之间的startX中间值
        animator = ValueAnimator.ofInt((getWidth() / textViews.size() - indicatorWidth) / 2 + indicatorPosition * getWidth() / textViews.size(), (getWidth() / textViews.size() - indicatorWidth) / 2 + newPosition * getWidth() / textViews.size());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //刷新
                startX = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                setTabTextColor(newPosition);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                startX = (getWidth() / textViews.size() - indicatorWidth) / 2 + newPosition * getWidth() / textViews.size();
                postInvalidate();
                setTabTextColor(newPosition);

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();

        this.indicatorPosition = newPosition;
    }

    /**
     * 设置TextView被选中状态
     * @param position
     */
    private void setTabTextColor(int position) {
        for (int i = 0; i < textViews.size(); i++) {
            textViews.get(i).setSelected(position == i);
        }
    }


    @Override
    public void onClick(View v) {
        if (v instanceof TextView && v.getTag() != null) {
            int position = (int) v.getTag();
            setSelectPosition(position);
            if (tabClickListener != null){
                tabClickListener.onTabClick(position);
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (animator != null){
            animator.cancel();
            animator = null;
        }
    }

    public interface TabClickListener{
        void onTabClick(int position);
    }
}
