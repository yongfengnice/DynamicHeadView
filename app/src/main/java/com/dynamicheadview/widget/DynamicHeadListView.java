package com.dynamicheadview.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by yongfengnice on 5/26/2017.
 */

public class DynamicHeadListView extends ListView {
    private View mDynamicView;
    private int mDynamicViewHeight;
    private float mDownY;
    private ValueAnimator mValueAnimator;

    public DynamicHeadListView(Context context) {
        this(context, null);
    }

    public DynamicHeadListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DynamicHeadListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
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

    public void setDynamicView(View dynamicView) {
        mDynamicView = dynamicView;
    }

    public void setDynamicViewHeight(int dynamicViewHeight) {
        mDynamicViewHeight = dynamicViewHeight;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mDynamicViewHeight == 0) {
            mDynamicViewHeight = mDynamicView.getHeight();
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
            default:
                handleActionUp(ev.getRawY());
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
            //Negative to check scrolling up, positive to check scrolling down
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
