package mk.wetalkit.legalcalculator.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mk.wetalkit.legalcalculator.R;
import mk.wetalkit.legalcalculator.utils.AnimatorUtils;

/**
 * Created by nikolaminoski on 10/1/17.
 */

public class WelcomeFragment extends Fragment {

    private View mViewContinue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_splashscreen, container, false);
//        view.findViewById(R.id.textView_wetalkit).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/WeTalkItMK/")));
//                onInterrupt();
//            }
//        });
//        view.findViewById(R.id.textView_legahackers).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/skopjelegalhackers")));
//                onInterrupt();
//            }
//        });
//        (mViewContinue = view.findViewById(R.id.textView_continue)).setVisibility(View.INVISIBLE);
//        mViewContinue.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                close();
//            }
//        });
        return view;
    }

    private void close() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        if (isAdded() && getView() != null) {
            View v = getView();
            if (v != null) {
                v.animate().y(v.getHeight()).setDuration(400).setListener(new AnimatorUtils.RemoveFragmentOnFinish(fragmentManager, this));
                return;
            }
        }
        fragmentManager.beginTransaction().remove(this).commit();
    }

    private void onInterrupt() {
        if(mViewContinue.getVisibility() != View.VISIBLE) {
            mViewContinue.setVisibility(View.VISIBLE);
            mViewContinue.setAlpha(0);
            mViewContinue.animate().setStartDelay(300).alpha(1);
        }
    }

    public void done() {
        if(mViewContinue == null || mViewContinue.getVisibility() != View.VISIBLE) {
            close();
        }

    }
}
