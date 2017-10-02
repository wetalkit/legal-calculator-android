package mk.wetalkit.legalcalculator.data;

import java.io.Serializable;

import mk.wetalkit.legalcalculator.BuildConfig;

/**
 * Created by nikolaminoski on 9/30/17.
 */

public class ServicesResponse implements Serializable {
    private long timestamp = System.currentTimeMillis();
    private int appVersion = BuildConfig.VERSION_CODE;
    private LegalService[] services;

    public LegalService[] getServices() {
        return services;
    }

    public long getAge() {
        return System.currentTimeMillis() - timestamp;
    }

    public boolean isDeprecated() {
        return appVersion != BuildConfig.VERSION_CODE;
    }
}
