package tdepinoy.henripotier.api.event;

import tdepinoy.henripotier.model.CommercialOffers;

public class CommercialOfferEvent {
    private CommercialOffers commercialOffers;

    public CommercialOfferEvent(CommercialOffers commercialOffers) {
        this.commercialOffers = commercialOffers;
    }

    public CommercialOffers getCommercialOffers() {
        return commercialOffers;
    }
}
