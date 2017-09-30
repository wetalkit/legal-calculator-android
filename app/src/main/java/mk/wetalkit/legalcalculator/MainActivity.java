package mk.wetalkit.legalcalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import mk.wetalkit.taxcalculator.R;
import mk.wetalkit.legalcalculator.adapters.ServicesAdapter;
import mk.wetalkit.legalcalculator.data.LegalService;
import mk.wetalkit.legalcalculator.data.ServicesResponse;

public class MainActivity extends AppCompatActivity {

    private ListView mListViewServices;
    private ServicesResponse mServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListViewServices = findViewById(R.id.listView_services);

        mServices = Sample.getData(ServicesResponse.class);

        mListViewServices.setAdapter(new ServicesAdapter(this, mServices.getServices()));
        mListViewServices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LegalService legalService = mServices.getServices()[i];
                Log.i("main", legalService.getTitle());

                Intent intent = new Intent(MainActivity.this, CalculatorActivity.class);
                intent.putExtra(CalculatorActivity.EXTRA_SERVICE, legalService);
                startActivity(intent);
            }
        });
    }
}
