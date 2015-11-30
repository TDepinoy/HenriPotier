package tdepinoy.henripotier.commercialoffer;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import tdepinoy.henripotier.api.HttpApiManager;
import tdepinoy.henripotier.model.Book;
import tdepinoy.henripotier.model.Cart;

public class CommercialOfferManager {

    private final HttpApiManager httpApiManager;
    private static final String SEPARATOR = ",";

    @Inject
    public CommercialOfferManager(HttpApiManager httpApiManager) {
        this.httpApiManager = httpApiManager;
    }

    public void commercialOffers(Cart cart) {
        List<Book> books = new ArrayList<>(cart.getItems().keySet());
        List<String> isbns = new ArrayList<>();
        for (Book book : books) {
            isbns.add(book.getIsbn());
        }
        httpApiManager.commercialOffers(TextUtils.join(SEPARATOR, isbns));
    }
}
