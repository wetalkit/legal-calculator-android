package mk.wetalkit.legalcalculator.data;

import java.io.Serializable;

/**
 * Created by nikolaminoski on 9/30/17.
 */

public class Option implements Serializable {
    private String title;
    private String value;

    public String getTitle() {
        return title;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", title, value);
    }
}
