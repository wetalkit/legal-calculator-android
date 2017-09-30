package mk.wetalkit.legalcalculator.data;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by nikolaminoski on 9/30/17.
 */

public class LegalService implements Serializable {
    private String title;
    private UserInput[] inputs;
    private HashMap<String,Cost> costs;

    public String getTitle() {
        return title;
    }

    public UserInput[] getInputs() {
        return inputs;
    }

    public HashMap<String, Cost> getCosts() {
        return costs;
    }
}
