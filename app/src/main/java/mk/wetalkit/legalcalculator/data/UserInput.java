package mk.wetalkit.legalcalculator.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by nikolaminoski on 9/30/17.
 */

public class UserInput implements Serializable {
    public enum InputType {
        Number, Options
    }
    private String name;
    private String var;
    private InputType type;
    @SerializedName("default")
    private String defaultVal;
    private String comment;
    private List<Option> options;

    public String getName() {
        return name;
    }

    public String getVar() {
        return var;
    }

    public InputType getType() {
        return type;
    }

    public String getDefaultVal() {
        return defaultVal;
    }

    public String getComment() {
        return comment;
    }

    public List<Option> getOptions() {
        return options;
    }
}
