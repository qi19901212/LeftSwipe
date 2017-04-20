package com.qi;

/**
 * Created by fengqi.sun on 2017/4/12.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * 基本功能RecyclerView 拉倒最后一个位置显示提示，可以跳转到详情
 * 来源修改http://blog.csdn.net/guolin_blog/article/details/9255575
 */
public class SwipeLeftView extends RelativeLayout {
    public static final int SCROLL_SPEED = -40;
    private SwipeListener mListener;
    private View rightView;
    private RecyclerView recyclerView;
    private ImageView arrowImg;
    private LayoutParams rightLayoutParams;
    private int hideRightWidth;
    private SwipeStatus curStatus = SwipeStatus.SWIPED;
    private SwipeStatus lastStatus = curStatus;
    private float xDown;
    private int touchSlop;
    private boolean firstOnLayout;
    private boolean swipeEnable;
    private LayoutParams recyclerViewParams;
    private TextView description;
    private boolean enable=true;


    public SwipeLeftView(Context context, AttributeSet attrs) {
        super(context, attrs);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed && !firstOnLayout) {
            rightView = getChildAt(1);
            arrowImg = (ImageView) rightView.findViewById(R.id.arrow);
            description = (TextView) rightView.findViewById(R.id.description);
            recyclerView = (RecyclerView) getChildAt(0);
            if (recyclerView.getWidth() >= getWidth()) {
                hideRightWidth = -rightView.getWidth();
                rightLayoutParams = (LayoutParams) rightView.getLayoutParams();
                recyclerViewParams = (LayoutParams) recyclerView.getLayoutParams();
                rightLayoutParams.rightMargin = hideRightWidth;
                rightLayoutParams.addRule(CENTER_VERTICAL);
                rightLayoutParams.addRule(ALIGN_PARENT_END);
                rightView.setVisibility(View.GONE);
            } else {
                rightView.setVisibility(View.GONE);
                swipeEnable = false;
            }
            firstOnLayout = true;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        IsEnAbleSwipe();
        if (swipeEnable) {
            rightView.setVisibility(View.VISIBLE);
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    xDown = event.getRawX();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float xMove = event.getRawX();
                    int distance = (int) (xMove - xDown);
                    // 不屏蔽上下滑动
                    if (Math.abs(distance)>10){
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                    if (distance >= 0 && rightLayoutParams.rightMargin <= hideRightWidth) {
                        return super.dispatchTouchEvent(event);
                    }
                    if (distance > -touchSlop) {
                        return false;
                    }
                    if (curStatus != SwipeStatus.SWIPING) {
                        if (rightLayoutParams.rightMargin >= 0) {
                            curStatus = SwipeStatus.RELEASE_SWIPE;
                        } else {
                            curStatus = SwipeStatus.LEFT_SWIPE;
                        }
                        rightLayoutParams.rightMargin = (-distance / 2) + hideRightWidth;
                        rightView.setLayoutParams(rightLayoutParams);
                        recyclerViewParams.rightMargin = (-distance / 2);
                        recyclerView.setLayoutParams(recyclerViewParams);
                        recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
                        recyclerView.getAdapter().notifyDataSetChanged();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                default:
                    if (curStatus == SwipeStatus.RELEASE_SWIPE) {
                        new RefreshingTask().execute();
                    } else if (curStatus == SwipeStatus.LEFT_SWIPE) {
                        new HideHeaderTask().execute();
                    }
                    break;
            }
            if (curStatus == SwipeStatus.LEFT_SWIPE
                    || curStatus ==  SwipeStatus.RELEASE_SWIPE) {
                updateRightView();
                recyclerView.setPressed(false);
                recyclerView.setFocusable(false);
                recyclerView.setFocusableInTouchMode(false);
                lastStatus = curStatus;
                return true;
            }
        }
        return super.dispatchTouchEvent(event);
    }

    private void setRightMargin(int rightMargin) {
        recyclerViewParams.rightMargin = rightMargin;
        recyclerView.setLayoutParams(recyclerViewParams);
        recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    public void setOnSwipeListener(SwipeListener listener) {
        mListener = listener;
    }

    /**
     * 滑动结束，恢复状态
     */
    public void finishSwiping() {
        curStatus = SwipeStatus.SWIPED;
        new HideHeaderTask().execute();
    }


    private void IsEnAbleSwipe() {
        View lastChild = recyclerView.getChildAt(recyclerView.getChildCount() - 1);
        if (lastChild != null) {
            if (lastChild.getRight() <= getWidth()) {
                swipeEnable = true;
            } else {
                if (rightLayoutParams.rightMargin != hideRightWidth) {
                    rightLayoutParams.rightMargin = hideRightWidth;
                    rightView.setLayoutParams(rightLayoutParams);
                }
                swipeEnable = false;
            }
        } else {
            swipeEnable = false;
        }
        if (!enable){
            swipeEnable = false;
        }
    }

    /**
     * 更新左拉头中的信息。
     */
    private void updateRightView() {
        if (lastStatus != curStatus) {
            if (curStatus == SwipeStatus.LEFT_SWIPE) {
                arrowImg.setVisibility(View.VISIBLE);
                description.setVisibility(View.VISIBLE);
                description.setText(R.string.swiping_to_detail);
                rotateArrow();
            } else if (curStatus == SwipeStatus.RELEASE_SWIPE) {
                description.setVisibility(View.VISIBLE);
                arrowImg.setVisibility(View.VISIBLE);
                description.setText(R.string.release_to_detail);
                rotateArrow();
            } else if (curStatus == SwipeStatus.SWIPING) {
                arrowImg.clearAnimation();
                description.setVisibility(GONE);
                arrowImg.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 箭头动画
     */
    private void rotateArrow() {
        float pivotX = arrowImg.getWidth() / 2f;
        float pivotY = arrowImg.getHeight() / 2f;
        float fromDegrees = 0f;
        float toDegrees = 0f;
        if (curStatus == SwipeStatus.LEFT_SWIPE) {
            fromDegrees = 180f;
            toDegrees = 360f;
        } else if (curStatus ==  SwipeStatus.RELEASE_SWIPE) {
            fromDegrees = 0f;
            toDegrees = 180f;
        }
        RotateAnimation animation = new RotateAnimation(fromDegrees, toDegrees, pivotX, pivotY);
        animation.setDuration(100);
        animation.setFillAfter(true);
        arrowImg.startAnimation(animation);
    }
    /**
     * @param enable false设置禁止左滑
     * */
    public void setEnable(boolean enable) {
        this.enable=enable;
    }


    class RefreshingTask extends AsyncTask<Void, Integer, Integer> {
        int spacing = 0;

        @Override
        protected Integer doInBackground(Void... params) {
            int topMargin = rightLayoutParams.rightMargin;
            int reMargin = recyclerViewParams.rightMargin;
            spacing = reMargin - topMargin;
            while (true) {
                topMargin = topMargin + SCROLL_SPEED;
                if (topMargin <= 0) {
                    topMargin = 0;
                    break;
                }
                publishProgress(topMargin);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            curStatus = SwipeStatus.SWIPING;
            publishProgress(0);
            return topMargin;
        }

        @Override
        protected void onProgressUpdate(Integer... topMargin) {
            updateRightView();
            rightLayoutParams.rightMargin = topMargin[0];
            if (topMargin[0] == 0) {
                setRightMargin(10);
            } else {
                setRightMargin(topMargin[0] + spacing);
            }
            rightView.setLayoutParams(rightLayoutParams);
        }

        @Override
        protected void onPostExecute(Integer topMargin) {
            finishSwiping();
            if (mListener != null) {
                mListener.onSwipeEnd();
            }
        }
    }


    class HideHeaderTask extends AsyncTask<Void, Integer, Integer> {

        @Override
        protected Integer doInBackground(Void... params) {
            int topMargin = rightLayoutParams.rightMargin;
            while (true) {
                topMargin = topMargin + SCROLL_SPEED;
                if (topMargin <= hideRightWidth) {
                    topMargin = hideRightWidth;
                    break;
                }
                publishProgress(topMargin);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return topMargin;
        }

        @Override
        protected void onProgressUpdate(Integer... topMargin) {
            rightLayoutParams.rightMargin = topMargin[0];
            rightView.setLayoutParams(rightLayoutParams);
        }

        @Override
        protected void onPostExecute(Integer topMargin) {
            rightLayoutParams.rightMargin = topMargin;
            setRightMargin(10);
            rightView.setLayoutParams(rightLayoutParams);
            curStatus = SwipeStatus.SWIPED;
        }
    }
}