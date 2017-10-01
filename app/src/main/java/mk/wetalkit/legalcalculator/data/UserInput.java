package mk.wetalkit.legalcalculator.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
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
    private int type;
    private FieldAttributes attributes;
    private String comment;
    @SerializedName("is_mandatory")
    private int mandatory;

    public String getName() {
        return name;
    }

    public String getVar() {
        return var;
    }

    public InputType getType() {
        return InputType.values()[type - 1];
    }

    public String getDefaultVal() {
        return mandatory == 0 && attributes != null ? attributes.getPlaceholder() : "";
    }

    public String getPlaceholder() {
        return mandatory == 1 && attributes != null ? attributes.getPlaceholder() : "";
    }

    public String getComment() {
        return comment;
    }

    public List<Option> getOptions() {
        return attributes != null && attributes.getOptions() != null ? attributes.getOptions() : new ArrayList<Option>();
    }

    public boolean isMandatory() {
        return mandatory == 1;
    }
}
