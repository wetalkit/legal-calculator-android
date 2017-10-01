package mk.wetalkit.legalcalculator.data;

import java.io.Serializable;

/**
 * Created by nikolaminoski on 9/30/17.
 */

public class LegalService implements Serializable {
    private int procedure_id;
    private String title;
    private UserInput[] items;

    public String getTitle() {
        return title;
    }

    public UserInput[] getInputs() {
        return items;
    }

    public int getId() {
        return procedure_id;
    }
}
