package vn.nsn.app.iotp.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class VerticalViewPager extends ViewPager {

    public void setEnabledSwipe(boolean enabled) {
        this.enabledSwipe = enabled;
    }

    private boolean enabledSwipe;

    public VerticalViewPager(Context context) {
        super(context);
        init();

    }

    public void init() {
        setPageTransformer(true, new VerticalViewPagerTransform());
        setOverScrollMode(OVER_SCROLL_NEVER);
        this.enabledSwipe = true;
    }

    public VerticalViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private MotionEvent swapXY(MotionEvent event) {
        float x = getWidth();
        float y = getHeight();

        float newX = (event.getY() / y) * y;
        float newY = (event.getX() / x) * x;

        event.setLocation(newX, newY);
        return event;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (this.enabledSwipe) {
            boolean intercept = super.onInterceptTouchEvent(swapXY(ev));
            swapXY(ev);
            return intercept;
        }

        return false;


    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (this.enabledSwipe) {
            return super.onTouchEvent(swapXY(ev));
        }

        return false;

    }

    private class VerticalViewPagerTransform implements ViewPager.PageTransformer {

        private static final float Min_Scale = 0.65f;

        @Override
        public void transformPage(View page, float position) {

//            if (position < -1) {
//                page.setAlpha(0);
//            } else if (position <= 0) {
//                page.setAlpha(1);
//                page.setTranslationX(page.getWidth() * -position);
//                page.setTranslationY(page.getHeight() * position);
//                page.setScaleX(1);
//                page.setScaleY(1);
//            } else if (position <= 1) {
//                page.setAlpha(1 - position);
//                page.setTranslationX(page.getWidth() * -position);
//                page.setTranslationY(0);
//                float scaleFactor = Min_Scale + (1 - Min_Scale) * (1 - Math.abs(position));
//                page.setScaleX(scaleFactor);
//                page.setScaleY(scaleFactor);
//            } else if (position > 1) {
//                page.setAlpha(0);
//            }

            if (position < -1) {
                // [-infinity, -1], view page is off-screen to the left


                // hide the page.

                page.setVisibility(View.INVISIBLE);

            } else if (position <= 1) {
                // [-1, 1], page is on screen


                // show the page

                page.setVisibility(View.VISIBLE);

                // get page back to the center of screen since it will get swipe horizontally by default.

                page.setTranslationX(page.getWidth() * -position);

                // set Y position to swipe in vertical direction.

                float y = position * page.getHeight();
                page.setTranslationY(y);

            } else {
                // [1, +infinity], page is off-screen to the right


                // hide the page.

                page.setVisibility(View.INVISIBLE);
            }
        }
    }
}