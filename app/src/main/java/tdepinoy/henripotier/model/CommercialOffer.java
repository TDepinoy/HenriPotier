package tdepinoy.henripotier.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import static com.fasterxml.jackson.annotation.JsonSubTypes.Type;


@JsonSubTypes({
        @Type (value = Percentage.class, name = "percentage"),
        @Type(value = Minus.class, name = "minus"),
        @Type(value = Slice.class, name = "slice")})
@JsonTypeInfo(use= JsonTypeInfo.Id.NAME, include= JsonTypeInfo.As.PROPERTY, property="type")
public abstract class CommercialOffer {

    protected String type;
    protected Double value;

    protected CommercialOffer(String type, Double value) {
        this.type = type;
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public abstract Double compute(Double total);
}
