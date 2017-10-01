package mk.wetalkit.legalcalculator.utils;

import android.animation.Animator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * Created by nikolaminoski on 11/30/16.
 */

public class AnimatorUtils {
    public static class GoneOnFinish implements Animator.AnimatorListener {
        private final View mView;

        public GoneOnFinish(View view) {
            mView = view;
        }

        @Override
        public void onAnimationStart(Animator animator) {

        }

        @Override
        public void onAnimationEnd(Animator animator) {
            mView.setVisibility(View.GONE);
        }

        @Override
        public void onAnimationCancel(Animator animator) {

        }

        @Override
        public void onAnimationRepeat(Animator animator) {

        }
    }

    public static class HideOnFinish implements Animator.AnimatorListener {
        private final View mView;

        public HideOnFinish(View view) {
            mView = view;
        }

        @Override
        public void onAnimationStart(Animator animator) {

        }

        @Override
        public void onAnimationEnd(Animator animator) {
            mView.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onAnimationCancel(Animator animator) {

        }

        @Override
        public void onAnimationRepeat(Animator animator) {

        }
    }

    /**
     * Simplifed listener for removing view from window manager on animation end.
     */
    public static class RemoveFromWMOnFinish implements Animator.AnimatorListener {
        private final View[] mViews;
        private final WindowManager mWindowManager;

        public RemoveFromWMOnFinish(WindowManager windowManager, View... views) {
            mViews = views;
            mWindowManager = windowManager;
        }

        @Override
        public void onAnimationStart(Animator animator) {

        }

        @Override
        public void onAnimationEnd(Animator animator) {
            for (View view : mViews) {
                if (view.getParent() != null) {
                    mWindowManager.removeViewImmediate(view);
                }
            }
        }

        @Override
        public void onAnimationCancel(Animator animator) {

        }

        @Override
        public void onAnimationRepeat(Animator animator) {

        }
    }

    public static class RemoveFragmentOnFinish implements Animator.AnimatorListener {
        private final FragmentManager mFragmentManager;
        private final Fragment mFragment;

        public RemoveFragmentOnFinish(FragmentManager fragmentManager, Fragment fragment) {
            this.mFragmentManager = fragmentManager;
            this.mFragment = fragment;
        }

        @Override
        public void onAnimationStart(Animator animator) {

        }

        @Override
        public void onAnimationEnd(Animator animator) {
            try {
                mFragmentManager.beginTransaction().remove(mFragment).commitAllowingStateLoss();
            } catch (Exception e) {
            }
        }

        @Override
        public void onAnimationCancel(Animator animator) {

        }

        @Override
        public void onAnimationRepeat(Animator animator) {

        }
    }

    public static class RemoveOnFinish implements Animator.AnimatorListener {
        private final View mView;

        public RemoveOnFinish(View view) {
            mView = view;
        }

        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            if (mView != null && mView.getParent() instanceof ViewGroup) {
                ((ViewGroup) mView.getParent()).removeView(mView);
            }
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }
}
