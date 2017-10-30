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
    @SerializedName("total_min")
    private float totalMin;
    @SerializedName("total_max")
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
        if (totalMax > 0 && totalMax != totalMin) {
            return String.format(Locale.ENGLISH, "%,d - %,d МКД", (int) totalMin, (int) totalMax).replace(",", ".");
        } else {
            return String.format(Locale.ENGLISH, "%,d МКД", (int) total).replace(",", ".");
        }
    }
}
