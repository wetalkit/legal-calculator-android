package mk.wetalkit.legalcalculator.data;

import java.io.Serializable;
import java.util.Locale;

/**
 * Created by nikolaminoski on 9/30/17.
 */

public class Cost implements Serializable {
    private String name;
    private float cost;

    public float getCost() {
        return cost;
    }

    public String getPrintableValue() {
        return String.format(Locale.ENGLISH, "%,d МКД", (int) cost).replace(",", ".");
    }

    public String getTitle() {
        return name;
    }
}
