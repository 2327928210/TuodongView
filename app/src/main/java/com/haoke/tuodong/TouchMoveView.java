package com.haoke.tuodong;

import android.content.Context;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class TouchMoveView extends RelativeLayout {
    private View mTargetView;
    private View mRedView, mBlueView, mGreenView, mYellowView;
    private RectF mRedRect, mBlueRect, mGreenRect, mYellowRect, mTargetRect;

    private View mParentView;

    public TouchMoveView(Context context) {
        this(context, null);
    }

    public TouchMoveView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTargetView = findViewById(R.id.target_view);
        mRedView = findViewById(R.id.red);
        mBlueView = findViewById(R.id.blue);
        mGreenView = findViewById(R.id.green);
        mYellowView = findViewById(R.id.yellow);

        mParentView = findViewById(R.id.parent_view);
    }

    private View mTouchedView;
    private float mInitX;
    private float mInitY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        initRect();
        float x = event.getX();
        float y = event.getY();
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mTouchedView = getTouchedView((int) x, (int) y);
                if (null != mTouchedView) {
                    mInitX = mTouchedView.getX();
                    mInitY = mTouchedView.getY();
                }

                return true;
            case MotionEvent.ACTION_MOVE:
                if (null != mTouchedView) {
                    float moveX = x - mTouchedView.getWidth() / 2;
                    float moveY = y - mTouchedView.getHeight() / 2;
                    mTouchedView.setX(moveX);
                    mTouchedView.setY(moveY);

                    if (isTwoViewClose(mTouchedView, mTargetView)) {
                        int colorRes = getColorRes(mTouchedView);
//					mTargetView.setBackgroundColor(getResources().getColor(colorRes));

                        touchTargetListener.onTouchTarget(mTargetView);
                    } else {


//					mTargetView.setBackgroundColor(getResources().getColor(R.color.black));
                    }
                }
                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (null != mTouchedView) {
                    mTouchedView.setX(mInitX);
                    mTouchedView.setY(mInitY);
                }

                touchTargetListener.onTouchTargetEnd(mTargetView);
//			mTargetView.setBackgroundColor(getResources().getColor(R.color.black));
                return true;
            default:
                return false;
        }
    }

    private int getColorRes(View view) {
        if (null == view) {
            return R.color.black;
        }
        int id = view.getId();
        switch (id) {
            case R.id.blue:
                return R.color.blue;
            case R.id.red:
                return R.color.red;
            case R.id.green:
                return R.color.green;
            case R.id.yellow:
                return R.color.yellow;
            default:
                return R.color.black;
        }
    }

    private View getTouchedView(int x, int y) {

        if (mRedRect.contains(x, y)) {
            return mRedView;
        }

        if (mBlueRect.contains(x, y)) {
            return mBlueView;
        }

        if (mGreenRect.contains(x, y)) {
            return mGreenView;
        }

        if (mYellowRect.contains(x, y)) {
            return mYellowView;
        }
        return null;

    }

    private void initRect() {
        if (null == mRedRect) {
            mRedRect = getRect(mRedView);
        }

        if (null == mBlueRect) {
            mBlueRect = getRect(mBlueView);
        }
        if (null == mGreenRect) {
            mGreenRect = getRect(mGreenView);
        }
        if (null == mYellowRect) {
            mYellowRect = getRect(mYellowView);
        }
        if (null == mTargetRect) {
            mTargetRect = getNormalRect(mTargetView);
        }
    }

    private RectF getNormalRect(View view) {
        return new RectF(view.getX(), view.getY(), view.getX() + view.getWidth(), view.getY() + view.getHeight());
    }

    private RectF getRect(View view) {
        RectF rectf = new RectF(view.getX(), view.getY() + mParentView.getY(), view.getX() + view.getWidth(), view.getY() + mParentView.getY() + view.getHeight());
        return rectf;
    }

    /**
     * �ж�����View�Ƿ��ཻ
     *
     * @param src
     * @param target
     * @return
     */
    private boolean isTwoViewClose(View src, View target) {
        RectF rectSrc = getNormalRect(src);
        RectF rectTar = getNormalRect(target);
        return rectSrc.intersect(rectTar);
    }

    private TouchTargetListener touchTargetListener;

    public void setTouchTargetListener(TouchTargetListener touchTargetListener) {
        this.touchTargetListener = touchTargetListener;
    }

    public interface TouchTargetListener {

        void onTouchTarget(View tagetView);

        void onTouchTargetEnd(View tagetView);
    }

}
