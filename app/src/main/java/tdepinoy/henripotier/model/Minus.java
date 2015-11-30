package tdepinoy.henripotier.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Minus extends CommercialOffer{

    @JsonCreator
    public Minus(@JsonProperty("type") String type, @JsonProperty("value") Double value) {
        super(type, value);
    }

    @Override
    public Double compute(Double total) {
        return total - value;
    }
}
