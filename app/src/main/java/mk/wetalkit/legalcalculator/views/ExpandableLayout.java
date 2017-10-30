package mk.wetalkit.legalcalculator.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;

/**
 * Created by nikolaminoski on 9/30/17.
 */

public class ExpandableLayout extends FrameLayout {
    private boolean mIsExpanded = false;
    private int mOriginalHeight;

    public ExpandableLayout(Context context) {
        super(context);
    }

    public ExpandableLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandableLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ExpandableLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mOriginalHeight = Math.max(getMeasuredHeight(), mOriginalHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mOriginalHeight = Math.max(getMeasuredHeight(), mOriginalHeight);
        if (mOriginalHeight > 0) {
            getLayoutParams().height = mIsExpanded ? mOriginalHeight : 0;
        }
    }

    public void minimize(int width) {

    }

    public void maximize() {

    }

    public boolean toggle() {
        int height = getMeasuredHeight();
        if (mIsExpanded) {
            ValueAnimator anim = ValueAnimator.ofInt(height, 0);
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int val = (Integer) valueAnimator.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = getLayoutParams();
                    layoutParams.height = val;
                    setLayoutParams(layoutParams);
                }
            });
            anim.setInterpolator(new AccelerateDecelerateInterpolator());
            anim.setDuration(400);
            anim.start();
            return mIsExpanded = false;
        } else {
            ValueAnimator anim = ValueAnimator.ofInt(0, mOriginalHeight);
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int val = (Integer) valueAnimator.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = getLayoutParams();
                    layoutParams.height = val;
                    setLayoutParams(layoutParams);
                }
            });
            anim.setInterpolator(new AccelerateDecelerateInterpolator());
            anim.setDuration(400);
            anim.start();
            return mIsExpanded = true;
        }

    }

}
