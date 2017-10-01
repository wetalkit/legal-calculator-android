package mk.wetalkit.legalcalculator.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nikolaminoski on 10/1/17.
 */

public class Api {

    private static LegalCalcServices mInstance;

    public static LegalCalcServices create(final Context context) {
        if (mInstance == null) {
            SharedPreferences pref = context.getSharedPreferences("user", Context.MODE_PRIVATE);
            final String uuid = pref.getString("uuid", UUID.randomUUID().toString());
            pref.edit().putString("uuid", uuid).apply();

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            Retrofit.Builder builder = new Retrofit.Builder().baseUrl("http://pravenkalkulator.mk/").addConverterFactory(GsonConverterFactory.create(new Gson()));

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    try {
                        Request original = chain.request();
                        Request.Builder request = original.newBuilder().header("Accept", "application/json").header("uuid", uuid);
                        request.method(original.method(), original.body());
                        return chain.proceed(request.build());
                    } catch (Throwable e) {
                        throw new IOException(e.getMessage(), e);
                    }
                }
            });

            OkHttpClient client = httpClient.build();

            Retrofit retrofit = builder.client(client).build();
            mInstance = retrofit.create(LegalCalcServices.class);
        }
        return mInstance;
    }
}
