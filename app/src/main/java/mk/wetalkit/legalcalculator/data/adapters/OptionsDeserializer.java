package mk.wetalkit.legalcalculator.data.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Map;

import mk.wetalkit.legalcalculator.data.FieldOptions;
import mk.wetalkit.legalcalculator.data.Option;

public class OptionsDeserializer implements JsonDeserializer<FieldOptions> {

    @Override
    public FieldOptions deserialize(JsonElement options, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        FieldOptions results = new FieldOptions();

        if (options.isJsonArray()) {
            for (JsonElement option : options.getAsJsonArray()) {
                String value = option.getAsString();
                results.add(new Option(value, String.valueOf(results.size())));
            }
        } else if (options.isJsonObject()) {
            for (Map.Entry<String, JsonElement> option : options.getAsJsonObject().entrySet()) {
                results.add(new Option(option.getKey(), option.getValue().getAsString()));
            }
        }
        return results;
    }
}