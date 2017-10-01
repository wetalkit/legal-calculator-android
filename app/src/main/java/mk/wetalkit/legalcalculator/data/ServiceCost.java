package mk.wetalkit.legalcalculator.data;

import java.io.Serializable;

/**
 * Created by nikolaminoski on 9/30/17.
 */

public class ServiceCost implements Serializable {
    private String title;
    private String description;
    private Cost[] costs;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Cost[] getCosts() {
        return costs;
    }
}
