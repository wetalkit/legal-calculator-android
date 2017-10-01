package mk.wetalkit.legalcalculator.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by nikolaminoski on 10/1/17.
 */

public class FieldAttributes implements Serializable {
    private String placeholder;
//    @JsonAdapter(FieldOptionsTypeAdapter.class)
    private FieldOptions options;

    public String getPlaceholder() {
        return placeholder;
    }

    public List<Option> getOptions() {
        return options;
    }


}
