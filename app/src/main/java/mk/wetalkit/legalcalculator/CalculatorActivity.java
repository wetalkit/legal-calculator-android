package mk.wetalkit.legalcalculator;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mk.wetalkit.legalcalculator.api.Api;
import mk.wetalkit.legalcalculator.data.Cost;
import mk.wetalkit.legalcalculator.data.LegalService;
import mk.wetalkit.legalcalculator.data.Option;
import mk.wetalkit.legalcalculator.data.ServiceCost;
import mk.wetalkit.legalcalculator.data.TotalCost;
import mk.wetalkit.legalcalculator.data.UserInput;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> params = new HashMap<>();
                for (InputFieldView fieldView : mInputViews) {
                    params.put(fieldView.getVarName(), fieldView.getValue());
                }
                final ProgressDialog progressDialog = ProgressDialog.show(CalculatorActivity.this, null, "Се вчитува...", true);
                Api.create(CalculatorActivity.this).calculate(mLegalService.getId(), params).enqueue(new Callback<TotalCost>() {
                    @Override
                    public void onResponse(Call<TotalCost> call, Response<TotalCost> response) {
                        progressDialog.dismiss();
                        if (response.isSuccessful()) {
                            onResults(response.body());
                        } else {
                            new AlertDialog.Builder(CalculatorActivity.this).setTitle("Грешка").setMessage("Неможе да се направи пресметката, проверете ги внесените информации и обидете се повторно.").setPositiveButton("Во Ред", null).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<TotalCost> call, Throwable t) {
                        t.printStackTrace();
                        progressDialog.dismiss();
                        new AlertDialog.Builder(CalculatorActivity.this).setTitle("Грешка").setMessage("Неможе да се направи пресметката, проверете ја вашата интернет конекција и обидете се повторно.").setPositiveButton("Во Ред", null).show();
                    }
                });
            }
        });

        mLayoutMandatoryInputs = findViewById(R.id.layout_inputsMandatory);
        mLayoutInputs = findViewById(R.id.layout_inputs);
        mLayoutReport = findViewById(R.id.layout_report);
        mLayoutExpandable = findViewById(R.id.layout_expandable);

        mLegalService = (LegalService) getIntent().getSerializableExtra(EXTRA_SERVICE);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(mLegalService.getTitle());
            actionBar.setDisplayHomeAsUpEnabled(true);
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
    }

    private void onResults(TotalCost results) {
//        TotalCost results = Sample.getResults(TotalCost.class);
        for (ServiceCost serviceCost : results.getServiceCosts()) {
            {
                View view = getLayoutInflater().inflate(R.layout.view_cost, mLayoutReport, false);
                ((TextView) view.findViewById(R.id.textView_title)).setText(serviceCost.getDescription());
                mLayoutReport.addView(view);
            }
            for (Cost cost : serviceCost.getCosts()) {
                View view = getLayoutInflater().inflate(R.layout.view_service_cost, mLayoutReport, false);
                ((TextView) view.findViewById(R.id.textView_title)).setText(cost.getTitle());
                ((TextView) view.findViewById(R.id.textView_value)).setText(String.format("%,d МКД", cost.getCost()));
                mLayoutReport.addView(view);
            }
        }
        {
            View view = getLayoutInflater().inflate(R.layout.view_total_cost, mLayoutReport, false);
            ((TextView) view.findViewById(R.id.textView_title)).setText("Вкупно:");
            ((TextView) view.findViewById(R.id.textView_value)).setText(String.format("%,d - %,d МКД", results.getTotalMin(), results.getTotalMax()));
            mLayoutReport.addView(view);
        }
    }

    public void onExpandCollapseClick(View view) {
        if (mLayoutExpandable.toggle()) {
            view.animate().rotation(-180).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(400);
        } else {
            view.animate().rotation(0).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(400);
        }
    }

    private abstract class InputFieldView {
        private UserInput userInput;
        View view;

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
            return userInput.getName();
        }
    }

    private class NumberInputFieldView extends InputFieldView {
        private final EditText mEditTextValue;

        public NumberInputFieldView(UserInput input) {
            super(input, R.layout.view_field_number);
            mEditTextValue = this.view.findViewById(R.id.editText_value);
        }

        @Override
        public String getValue() {
            return mEditTextValue.getText().toString();
        }
    }

    private class OptionsInputFieldView extends InputFieldView {
        private final Spinner mSpinner;

        public OptionsInputFieldView(UserInput input) {
            super(input, R.layout.view_field_options);
            mSpinner = this.view.findViewById(R.id.spinner_value);

            mSpinner.setAdapter(new ArrayAdapter<Option>(CalculatorActivity.this, android.R.layout.simple_list_item_1, input.getOptions()));
        }

        @Override
        public String getValue() {
            return ((Option) mSpinner.getSelectedItem()).getValue();
        }
    }

}
