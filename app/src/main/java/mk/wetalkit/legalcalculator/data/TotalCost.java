package mk.wetalkit.legalcalculator.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Locale;

/**
 * Created by nikolaminoski on 9/30/17.
 */

public class TotalCost implements Serializable {
    @SerializedName("costs")
    private ServiceCost serviceCost[];
    @SerializedName("total-min")
    private float totalMin;
    @SerializedName("total-max")
    private float totalMax;
    private float total;

    public float getTotalMax() {
        return totalMax;
    }

    public ServiceCost[] getServiceCosts() {
        return serviceCost;
    }

    public float getTotalMin() {
        return totalMin;
    }

    public float getTotal() {
        return total;
    }

    public String getPrintableTotal() {
        if (total > 0) {
            return String.format(Locale.ENGLISH, "%,d МКД", (int) total).replace(",", ".");
        } else {
            return String.format(Locale.ENGLISH, "%,d - %,d МКД", (int) totalMin, (int) totalMax).replace(",", ".");
        }
    }
}
