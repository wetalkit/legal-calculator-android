package mk.wetalkit.legalcalculator.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import mk.wetalkit.legalcalculator.data.LegalService;
import mk.wetalkit.taxcalculator.R;

/**
 * Created by nikolaminoski on 9/30/17.
 */

public class ServicesAdapter extends BaseAdapter {

    private final LegalService[] mLegalServices;
    private final Context mContext;

    public ServicesAdapter(Context context, LegalService[] legalServices) {
        mLegalServices = legalServices;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mLegalServices.length;
    }

    @Override
    public LegalService getItem(int i) {
        return mLegalServices[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int index, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = (ViewGroup) LayoutInflater.from(mContext).inflate(R.layout.view_service, viewGroup, false);
            viewHolder = new ViewHolder(view);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.setData(getItem(index));
        return view;
    }

    private class ViewHolder {
        private final TextView mTextViewTitle;

        public ViewHolder(View viewGroup) {
            viewGroup.setTag(this);
            mTextViewTitle = viewGroup.findViewById(R.id.textView_title);
        }

        public void setData(LegalService legalService) {
            mTextViewTitle.setText(legalService.getTitle());
        }
    }
}
