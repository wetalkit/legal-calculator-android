package mk.wetalkit.legalcalculator;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import mk.wetalkit.legalcalculator.adapters.ServicesAdapter;
import mk.wetalkit.legalcalculator.api.Api;
import mk.wetalkit.legalcalculator.data.LegalService;
import mk.wetalkit.legalcalculator.data.ServicesResponse;
import mk.wetalkit.legalcalculator.fragments.WelcomeFragment;
import mk.wetalkit.legalcalculator.utils.AnimatorUtils;
import mk.wetalkit.legalcalculator.utils.DataHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String FRAGMENT_WELCOME = "welcome";
    private ListView mListViewServices;
    private ServicesResponse mServices;
    private Handler mHandler = new Handler();
    private long mStartTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(android.R.id.content, new WelcomeFragment(), FRAGMENT_WELCOME);
        transaction.setCustomAnimations(R.anim.none, R.anim.slide_out_down);
        transaction.commit();

        mStartTime = System.currentTimeMillis();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mServices = DataHelper.load(MainActivity.this, ServicesResponse.class);
                if (mServices == null || mServices.getAge() > 12 * 3600 * 1000) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            loadDataFromApi();
                        }
                    });
                } else {
                    loadVIewsDelayed();
                }
            }
        }).start();
    }

    private void loadDataFromApi() {
        Api.create(MainActivity.this).getServices().enqueue(new Callback<ServicesResponse>() {
            @Override
            public void onResponse(Call<ServicesResponse> call, Response<ServicesResponse> response) {
                if (response.isSuccessful()) {
                    mServices = response.body();
                    if (!BuildConfig.DEBUG) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                DataHelper.store(MainActivity.this, mServices);
                            }
                        }).start();
                    }
                    loadVIewsDelayed();
                } else {
                    //TODO: Load default
                    new AlertDialog.Builder(MainActivity.this).setTitle("Грешка").setMessage("Неможе да се вчитаат правните услуги, проверете ја вашата интернет конекција и обидете се повторно.").setPositiveButton("Во Ред", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    }).show();
                }
            }

            @Override
            public void onFailure(Call<ServicesResponse> call, Throwable t) {
                t.printStackTrace();
                new AlertDialog.Builder(MainActivity.this).setTitle("Грешка").setMessage("Неможе да се вчитаат правните услуги, проверете ја вашата интернет конекција и обидете се повторно.").setPositiveButton("Во Ред", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).show();
            }
        });
    }

    private void loadVIewsDelayed() {
        long loadTime = System.currentTimeMillis() - mStartTime;
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadViews();
            }
        }, Math.max(3000 - loadTime, 5));
    }

    private void loadViews() {
        mListViewServices = findViewById(R.id.listView_services);

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

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(FRAGMENT_WELCOME);
        if (fragment instanceof WelcomeFragment) {
            ((WelcomeFragment) fragment).done();
        } else {
            fragmentManager.beginTransaction().remove(fragment).commit();
        }
    }

}
