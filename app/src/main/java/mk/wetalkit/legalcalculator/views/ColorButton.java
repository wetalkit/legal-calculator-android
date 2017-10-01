package mk.wetalkit.legalcalculator.views;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;


import mk.wetalkit.legalcalculator.R;

import static android.view.Gravity.CENTER;

/**
 * Created by nikolaminoski on 12/9/16.
 */

public class ColorButton extends AppCompatButton {
    public ColorButton(Context context) {
        super(context);
    }

    public ColorButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        int[] attrsArray = new int[]{android.R.attr.tint};
        TypedArray ta = getContext().obtainStyledAttributes(attrs, attrsArray);
        int color = ta.getColor(0, Color.BLUE);
        ta.recycle();
        setColor(color);
    }

    public ColorButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        int[] attrsArray = new int[]{android.R.attr.tint};
        TypedArray ta = getContext().obtainStyledAttributes(attrs, attrsArray, defStyleAttr, 0);
        int color = ta.getColor(0, Color.BLUE);
        ta.recycle();
        setColor(color);
    }

    public void setColor(@ColorInt int color) {
        if (getBackground() == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ViewCompat.setBackground(this, getResources().getDrawable(android.R.drawable.btn_default, getContext().getTheme()));
            } else {
                ViewCompat.setBackground(this, getResources().getDrawable(android.R.drawable.btn_default));
            }
            setGravity(CENTER);
        }
        ViewCompat.setBackgroundTintList(this, ColorStateList.valueOf(color));
        ViewCompat.setBackgroundTintMode(this, PorterDuff.Mode.MULTIPLY);
    }
}
