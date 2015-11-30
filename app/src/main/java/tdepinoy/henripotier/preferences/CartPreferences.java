package tdepinoy.henripotier.preferences;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.TreeMap;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import tdepinoy.henripotier.cart.serialization.CartModule;
import tdepinoy.henripotier.model.Book;
import tdepinoy.henripotier.model.Cart;

public class CartPreferences extends AbstractPreference {

    private static final String CART_PREFERENCE_NAME = "CART_PREFERENCE";
    private static final String CART_PREFERENCE_KEY = "CART";

    private final Logger logger = LoggerFactory.getLogger(CartPreferences.class);

    @Inject
    public CartPreferences(Context context) {
        super(context);
    }

    @Override
    protected String getPreferenceName() {
        return CART_PREFERENCE_NAME;
    }

    public Cart getCart() {
        String jsonCart = getString(CART_PREFERENCE_KEY, null);
        if(TextUtils.isEmpty(jsonCart)) {
            return initCart();
        }
        else {
            ObjectMapper mapper = getCartObjectMapper();
            Cart cart = null;
            try {
                cart = mapper.readValue(jsonCart, Cart.class);
                logger.debug("Json cartPreferences: {}", jsonCart);
            } catch (IOException e) {
                logger.error("Error while deserializing cartPreferences: ", e);
            }
            return cart;
        }
    }

    public void setCart(Cart cart) {
        ObjectMapper mapper = getCartObjectMapper();
        String jsonCart = null;
        try {
            jsonCart = mapper.writeValueAsString(cart);
            logger.debug("Json cartPreferences: {}", jsonCart);
        } catch (JsonProcessingException e) {
            logger.error("Error while serializing cartPreferences: ", e);
        }
        setString(CART_PREFERENCE_KEY, jsonCart);
    }

    @NonNull
    private ObjectMapper getCartObjectMapper() {
        ObjectMapper mapper =  new ObjectMapper();
        mapper.registerModule(new CartModule());
        return mapper;
    }

    private Cart initCart() {
        TreeMap<Book, Integer> cartMap = new TreeMap<>();
        return new Cart(cartMap);
    }
}
