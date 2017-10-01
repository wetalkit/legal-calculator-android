package mk.wetalkit.legalcalculator.data;

import java.io.Serializable;

/**
 * Created by nikolaminoski on 9/30/17.
 */

public class Option implements Serializable {
    private String label;
    private String value;

    public Option() {
    }

    public Option(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getTitle() {
        return label;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", label, value);
    }
}
