package tdepinoy.henripotier.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CommercialOffers {
    private final List<CommercialOffer> commercialOffers;

    @JsonCreator
    public CommercialOffers(@JsonProperty("offers")List<CommercialOffer> commercialOffers) {
        this.commercialOffers = commercialOffers;
    }

    @JsonProperty("offers")
    public List<CommercialOffer> getCommercialOffers() {
        return commercialOffers;
    }
}
