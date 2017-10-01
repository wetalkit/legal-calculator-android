package mk.wetalkit.legalcalculator.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.StringRes;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by nikolaminoski on 12/7/16.
 */

public class HtmlTextView extends android.support.v7.widget.AppCompatTextView {
    public HtmlTextView(Context context) {
        super(context);
    }

    public HtmlTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }


    public HtmlTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        setHtml(getText().toString());
    }

    public void setHtml(String html) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            setText(Html.fromHtml(html, 0));
        } else {
            setText(Html.fromHtml(html));
        }
    }

    public void setHtml(@StringRes int resId) {
        setHtml(getResources().getString(resId));
    }
}
