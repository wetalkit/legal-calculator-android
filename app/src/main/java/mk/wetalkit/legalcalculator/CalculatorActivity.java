package mk.wetalkit.legalcalculator;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import mk.wetalkit.legalcalculator.api.Api;
import mk.wetalkit.legalcalculator.data.Cost;
import mk.wetalkit.legalcalculator.data.LegalService;
import mk.wetalkit.legalcalculator.data.Option;
import mk.wetalkit.legalcalculator.data.ServiceCost;
import mk.wetalkit.legalcalculator.data.TotalCost;
import mk.wetalkit.legalcalculator.data.UserInput;
import mk.wetalkit.legalcalculator.utils.CyrillicTranslator;
import mk.wetalkit.legalcalculator.utils.ShareBitmapUtil;
import mk.wetalkit.legalcalculator.views.ExpandableLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalculatorActivity extends AppCompatActivity {

    public static final String EXTRA_SERVICE = "service_data";

    private LinearLayout mLayoutInputs;
    private LinearLayout mLayoutReport;
    private LinearLayout mLayoutMandatoryInputs;

    private LegalService mLegalService;
    private List<InputFieldView> mInputViews;
    private int mLayoutInputsHeight;
    private ExpandableLayout mLayoutExpandable;
    private ScrollView mScrollViewContent;
    private View mViewExpander;
    private FloatingActionButton mFabCalculate;
    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        (mFabCalculate = findViewById(R.id.fab)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = CalculatorActivity.this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    view.clearFocus();
                }

                HashMap<String, String> params = new HashMap<>();
                for (InputFieldView fieldView : mInputViews) {
                    if (fieldView.validate()) {
                        params.put(fieldView.getVarName(), fieldView.getValue());
                    } else {
                        return;
                    }
                }
                final ProgressDialog progressDialog = ProgressDialog.show(CalculatorActivity.this, null, "Се вчитува...", true);
                Api.create(CalculatorActivity.this).calculate(mLegalService.getId(), params).enqueue(new Callback<TotalCost>() {
                    @Override
                    public void onResponse(Call<TotalCost> call, Response<TotalCost> response) {
                        progressDialog.dismiss();
                        if (response.isSuccessful()) {
                            mFabCalculate.hide();
                            onResults(response.body());
                        } else {
                            new AlertDialog.Builder(CalculatorActivity.this).setTitle("Грешка").setMessage("Неможе да се направи пресметката, проверете ги внесените информации и обидете се повторно.").setPositiveButton("Во Ред", null).show();
                            mFabCalculate.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<TotalCost> call, Throwable t) {
                        t.printStackTrace();
                        progressDialog.dismiss();
                        mFabCalculate.show();
                        new AlertDialog.Builder(CalculatorActivity.this).setTitle("Грешка").setMessage("Неможе да се направи пресметката, проверете ја вашата интернет конекција и обидете се повторно.").setPositiveButton("Во Ред", null).show();
                    }
                });
                mFabCalculate.hide();

                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Action")
                        .setAction("Calculate")
                        .setLabel(CyrillicTranslator.transliterate(mLegalService.getTitle()))
                        .build());
            }
        });

        mLayoutMandatoryInputs = findViewById(R.id.layout_inputsMandatory);
        mLayoutInputs = findViewById(R.id.layout_inputs);
        mLayoutReport = findViewById(R.id.layout_report);
        mLayoutExpandable = findViewById(R.id.layout_expandable);
        mScrollViewContent = findViewById(R.id.scrollView);
        mViewExpander = findViewById(R.id.view_expander);

        mLegalService = (LegalService) getIntent().getSerializableExtra(EXTRA_SERVICE);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(mLegalService.getTitle());
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }

        mInputViews = new ArrayList<>();
        for (UserInput input : mLegalService.getInputs()) {
            if (input != null) {
                switch (input.getType()) {
                    case Number:
                        mInputViews.add(new NumberInputFieldView(input));
                        break;
                    case Options:
                        mInputViews.add(new OptionsInputFieldView(input));
                        break;
                }
            }
        }
        if (mLayoutInputs.getChildCount() == 0) {
            mLayoutExpandable.setVisibility(View.GONE);
            mViewExpander.setVisibility(View.GONE);
        } else {
            mViewExpander.setVisibility(View.VISIBLE);
            mLayoutExpandable.setVisibility(View.VISIBLE);
        }

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName("Calculator - " + CyrillicTranslator.transliterate(mLegalService.getTitle()));
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

    }

    private void onResults(TotalCost results) {
        mLayoutReport.removeAllViews();
        for (ServiceCost serviceCost : results.getServiceCosts()) {
            {
                View view = getLayoutInflater().inflate(R.layout.view_cost, mLayoutReport, false);
                ((TextView) view.findViewById(R.id.textView_title)).setText(serviceCost.getDescription());
                mLayoutReport.addView(view);
            }
            for (Cost cost : serviceCost.getCosts()) {
                View view = getLayoutInflater().inflate(R.layout.view_service_cost, mLayoutReport, false);
                ((TextView) view.findViewById(R.id.textView_title)).setText(cost.getTitle());
                ((TextView) view.findViewById(R.id.textView_value)).setText(cost.getPrintableValue());
                mLayoutReport.addView(view);
            }
        }
        {
            View view = getLayoutInflater().inflate(R.layout.view_total_cost, mLayoutReport, false);
            ((TextView) view.findViewById(R.id.textView_title)).setText("Вкупно:");
            ((TextView) view.findViewById(R.id.textView_value)).setText(results.getPrintableTotal());
            mLayoutReport.addView(view);
        }

        getLayoutInflater().inflate(R.layout.view_total_share, mLayoutReport, true);

        findViewById(R.id.textView_wetalkit).setVisibility(View.INVISIBLE);
        findViewById(R.id.textView_legahackers).setVisibility(View.INVISIBLE);

        mLayoutReport.post(new Runnable() {
            @Override
            public void run() {
                mScrollViewContent.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    public void onExpandCollapseClick(View view) {
        if (mLayoutExpandable.toggle()) {
            view.animate().rotation(-180).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(400);
        } else {
            view.animate().rotation(0).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(400);
        }
    }

    public void onShareClick(View view) {
        view.setVisibility(View.INVISIBLE);
        findViewById(R.id.textView_wetalkit).setVisibility(View.VISIBLE);
        findViewById(R.id.textView_legahackers).setVisibility(View.VISIBLE);

        View viewContent = findViewById(R.id.layout_content);
        float density = getResources().getDisplayMetrics().density;
        Bitmap bitmap = Bitmap.createBitmap((int) (viewContent.getWidth() / density), (int) (viewContent.getHeight() / density), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(getResources().getColor(android.R.color.background_light));
        canvas.scale(1 / density, 1 / density);
        viewContent.draw(canvas);
        view.setVisibility(View.VISIBLE);
        findViewById(R.id.textView_wetalkit).setVisibility(View.INVISIBLE);
        findViewById(R.id.textView_legahackers).setVisibility(View.INVISIBLE);

        try {
            Uri uri = ShareBitmapUtil.getBitmapUri(this, bitmap, Bitmap.CompressFormat.PNG);
            if (uri != null) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                shareIntent.setDataAndType(uri, getContentResolver().getType(uri));
                shareIntent.setType(getContentResolver().getType(uri));
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, mLegalService.getTitle());
                startActivity(Intent.createChooser(shareIntent, "Одбери Апликација"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        bitmap.recycle();

        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .setLabel(CyrillicTranslator.transliterate(mLegalService.getTitle()))
                .build());


    }

    ////// INPUT FIELDS CLASSES //////
    private abstract class InputFieldView {
        protected UserInput userInput;
        protected View view;

        InputFieldView(UserInput userInput, @LayoutRes int resLayout) {
            this.userInput = userInput;
            this.view = getLayoutInflater().inflate(resLayout, mLayoutInputs, false);
            ((TextView) this.view.findViewById(R.id.textView_title)).setText(userInput.getName());
            if (TextUtils.isEmpty(userInput.getComment())) {
                this.view.findViewById(R.id.textView_comments).setVisibility(View.GONE);
            } else {
                ((TextView) this.view.findViewById(R.id.textView_comments)).setText(userInput.getComment());
            }
            if (userInput.isMandatory())
                mLayoutMandatoryInputs.addView(this.view);
            else
                mLayoutInputs.addView(this.view);
        }

        public abstract String getValue();

        public String getVarName() {
            return userInput.getVar();
        }

        public abstract boolean validate();
    }

    private class NumberInputFieldView extends InputFieldView {
        private final EditText mEditTextValue;

        public NumberInputFieldView(UserInput input) {
            super(input, R.layout.view_field_number);
            mEditTextValue = this.view.findViewById(R.id.editText_value);
            mEditTextValue.setHint(input.getPlaceholder());
            mEditTextValue.setText(input.getDefaultVal());
            mEditTextValue.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    try {
                        long value = Long.parseLong(charSequence.toString().replace(".", "").replace(",", ""));
                    } catch (Exception e) {

                    }
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    mFabCalculate.show();
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    try {
                        mEditTextValue.removeTextChangedListener(this);
                        int selStart = mEditTextValue.getSelectionStart();
                        int selEnd = mEditTextValue.getText().length() - mEditTextValue.getSelectionEnd();
                        long value = Long.parseLong(editable.toString().replace(".", "").replace(",", ""));
                        String newText = String.format(Locale.ENGLISH, "%,d", value).replace(",", ".");
                        if (!newText.equals(mEditTextValue.getText().toString())) {
                            mEditTextValue.setText(newText);
                            mEditTextValue.setSelection(newText.length() - selEnd);
                        }
                    } catch (Exception e) {
                    }
                    mEditTextValue.addTextChangedListener(this);

                }
            });
        }

        @Override
        public String getValue() {
            return mEditTextValue.getText().toString().replace(".", "");
        }

        @Override
        public boolean validate() {
            if (!userInput.isMandatory() || mEditTextValue.getText().length() > 0)
                return true;
            else {
                mEditTextValue.setError("Полето е задолжително");
                mEditTextValue.requestFocus();
                return false;
            }
        }
    }

    private class OptionsInputFieldView extends InputFieldView {
        private final Spinner mSpinner;

        public OptionsInputFieldView(UserInput input) {
            super(input, R.layout.view_field_options);
            mSpinner = this.view.findViewById(R.id.spinner_value);

            mSpinner.setAdapter(new ArrayAdapter<Option>(CalculatorActivity.this, android.R.layout.simple_list_item_1, input.getOptions()));
            mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    mFabCalculate.show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

        @Override
        public String getValue() {
            return ((Option) mSpinner.getSelectedItem()).getValue();
        }

        @Override
        public boolean validate() {
            return true;
        }
    }

}
