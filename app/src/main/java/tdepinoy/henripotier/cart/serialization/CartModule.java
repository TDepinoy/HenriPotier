package tdepinoy.henripotier.cart.serialization;

import com.fasterxml.jackson.databind.module.SimpleModule;

import tdepinoy.henripotier.model.Book;

public class CartModule extends SimpleModule {
    public CartModule() {
        addKeyDeserializer(Book.class, new CartDeserializer());
    }
}
