package mk.wetalkit.legalcalculator.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by nikolaminoski on 9/30/17.
 */

public class TotalCost implements Serializable {
    @SerializedName("costs")
    private ServiceCost serviceCost[];
    @SerializedName("total-min")
    private int totalMin;
    @SerializedName("total-max")
    private int totalMax;

    public int getTotalMax() {
        return totalMax;
    }

    public ServiceCost[] getServiceCosts() {
        return serviceCost;
    }

    public int getTotalMin() {
        return totalMin;
    }
}
