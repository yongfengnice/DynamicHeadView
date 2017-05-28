package com.dynamicheadview.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yongfengnice on 5/28/2017.
 */

public class DynamicHeadRecyclerView extends RecyclerView {
    private View mDynamicView;
    private int mDynamicViewHeight;
    private float mDownY;
    private ValueAnimator mValueAnimator;

    public DynamicHeadRecyclerView(Context context) {
        this(context, null);
    }

    public DynamicHeadRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DynamicHeadRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setDynamicView(View dynamicView) {
        mDynamicView = dynamicView;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (getChildCount() <= 0) {
            return super.onTouchEvent(ev);
        }
        if (mDynamicViewHeight == 0) {
            mDynamicViewHeight = mDynamicView.getHeight();
            mValueAnimator = ValueAnimator.ofInt(0, mDynamicViewHeight);
            mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Object animatedValue = animation.getAnimatedValue();
                    if (animatedValue instanceof Integer) {
                        ViewGroup.LayoutParams params = mDynamicView.getLayoutParams();
                        params.height = Integer.parseInt(animatedValue.toString());
                        mDynamicView.setLayoutParams(params);
                    }
                }
            });
        }

        if (mValueAnimator != null && mValueAnimator.isRunning()) {
            return true;
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = ev.getRawY();
                float dy = moveY - mDownY;
                mDownY = moveY;
                boolean handleMotionEvent = handleActionMove(dy);// consume the event ?
                if (handleMotionEvent) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                boolean handleMoveUp = handleActionUp(ev.getRawY());
                if (handleMoveUp) {
                    return true;
                }
                break;
        }

        return super.onTouchEvent(ev);
    }

    private boolean handleActionUp(float upY) {
        int height = mDynamicView.getHeight();
        if (height <= mDynamicViewHeight / 2) {
            mValueAnimator.setIntValues(height, 0);
        } else {
            mValueAnimator.setIntValues(height, mDynamicViewHeight);
        }
        mValueAnimator.start();
        return height > 0 && height < mDynamicViewHeight;
    }

    private boolean handleActionMove(float dy) {
        dy = dy / 2;
        int height = mDynamicView.getHeight();
        if (dy > 0) {//forward down
            if (!canScrollVertically(-1) && height < mDynamicViewHeight) {//first item is visible
                ViewGroup.LayoutParams params = mDynamicView.getLayoutParams();
                params.height = Math.round(height + dy);
                if (params.height > mDynamicViewHeight) {
                    params.height = mDynamicViewHeight;
                }
                mDynamicView.setLayoutParams(params);
                return true;
            }
        } else {//forward up
            if (height > 0) {//head view is larger than zero
                ViewGroup.LayoutParams params = mDynamicView.getLayoutParams();
                params.height = Math.round(height - Math.abs(dy));
                if (params.height < 0) {
                    params.height = 0;
                }
                mDynamicView.setLayoutParams(params);
                return true;
            }
        }
        return false;
    }

}
