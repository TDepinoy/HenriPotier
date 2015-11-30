package tdepinoy.henripotier.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Percentage extends CommercialOffer {

    @JsonCreator
    public Percentage(@JsonProperty("type") String type, @JsonProperty("value") Double value) {
        super(type, value);
    }

    @Override
    public Double compute(Double total) {
        return total - total * value / 100.0;
    }
}
