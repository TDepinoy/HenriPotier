package tdepinoy.henripotier.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Slice extends CommercialOffer {

    private Double sliceValue;

    @JsonCreator
    public Slice(@JsonProperty("type") String type, @JsonProperty("value") Double value, @JsonProperty("sliceValue") Double sliceValue) {
        super(type, value);
        this.sliceValue = sliceValue;
    }

    public Double getSliceValue() {
        return sliceValue;
    }

    public void setSliceValue(Double sliceValue) {
        this.sliceValue = sliceValue;
    }

    @Override
    public Double compute(Double total) {
        Double slices = total / sliceValue;
        return total - Math.floor(slices) * value;
    }
}
