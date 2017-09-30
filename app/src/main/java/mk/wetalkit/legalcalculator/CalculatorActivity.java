package mk.wetalkit.legalcalculator;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mk.wetalkit.legalcalculator.data.UserInput;
import mk.wetalkit.taxcalculator.R;
import mk.wetalkit.legalcalculator.data.LegalService;
import mk.wetalkit.legalcalculator.data.Option;

public class CalculatorActivity extends AppCompatActivity {

    public static final String EXTRA_SERVICE = "service_data";

    private LinearLayout mLayoutInputs;
    private LinearLayout mLayoutReport;
    private LegalService mLegalService;

    private List<InputFieldView> mInputViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (InputFieldView fieldView : mInputViews) {
                    Log.i("Field", fieldView.getVarName() + "\t" + fieldView.getValue());
                }
            }
        });

        mLayoutInputs = findViewById(R.id.layout_inputs);
        mLayoutReport = findViewById(R.id.layout_report);

        mLegalService = (LegalService) getIntent().getSerializableExtra(EXTRA_SERVICE);

        mInputViews = new ArrayList<>();
        for (UserInput input : mLegalService.getInputs()) {
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

    private abstract class InputFieldView {
        private UserInput userInput;
        protected View view;

        public InputFieldView(UserInput userInput, @LayoutRes int resLayout) {
            this.userInput = userInput;
            this.view = getLayoutInflater().inflate(resLayout, mLayoutInputs, false);
            ((TextView) this.view.findViewById(R.id.textView_title)).setText(userInput.getName());
            if (TextUtils.isEmpty(userInput.getComment())) {
                this.view.findViewById(R.id.textView_comments).setVisibility(View.GONE);
            } else {
                ((TextView) this.view.findViewById(R.id.textView_comments)).setText(userInput.getComment());
            }
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
