package mk.wetalkit.legalcalculator.data;

import java.io.Serializable;

/**
 * Created by nikolaminoski on 9/30/17.
 */

public class ServicesResponse implements Serializable {
    private long timestamp = System.currentTimeMillis();
    private LegalService[] services;

    public LegalService[] getServices() {
        return services;
    }

    public long getAge() {
        return System.currentTimeMillis() - timestamp;
    }
}
