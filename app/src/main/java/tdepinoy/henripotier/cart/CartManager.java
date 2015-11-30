package tdepinoy.henripotier.cart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import tdepinoy.henripotier.cart.event.CartAddedItemEvent;
import tdepinoy.henripotier.cart.event.CartQuantityChangeEvent;
import tdepinoy.henripotier.cart.event.CartRemovedItemEvent;
import tdepinoy.henripotier.model.Book;
import tdepinoy.henripotier.model.Cart;
import tdepinoy.henripotier.preferences.CartPreferences;

public class CartManager {

    private final Logger logger = LoggerFactory.getLogger(CartManager.class);

    private EventBus bus;
    private CartPreferences cartPreferences;

    @Inject
    public CartManager(CartPreferences preferences, EventBus bus) {
        this.cartPreferences = preferences;
        this.bus = bus;
    }

    public Cart getCart() {
        return cartPreferences.getCart();
    }

    public void addToCart(Book book) {
        if (book == null) {
            logger.error("Cannot insert a null book to the cart");
            return;
        }
        Cart cart = cartPreferences.getCart();
        cart.addToCart(book);
        cartPreferences.setCart(cart);
        bus.post(new CartAddedItemEvent());
    }

    public void removeFromCart(Book book) {
        if (book == null) {
            logger.error("Cannot remove a null book from the cart");
            return;
        }
        Cart cart = cartPreferences.getCart();
        cart.removeFromCart(book);
        cartPreferences.setCart(cart);
        bus.post(new CartRemovedItemEvent());
    }

    public void changeQuantity(Book book, Integer quantity) {
        Cart cart = cartPreferences.getCart();
        if (book == null || (quantity.equals(cart.getQuantity(book)))) {
            logger.warn("Illegal quantity update: #quantity{} #book{}", quantity, book);
            return;
        }
        cart.changeQuantity(book, quantity);
        cartPreferences.setCart(cart);
        bus.post(new CartQuantityChangeEvent());
    }

    public boolean isCartEmpty() {
        Cart cart = cartPreferences.getCart();
        return cart.getItems() == null || cart.getItems().isEmpty();
    }

    public int getCount() {
        if (isCartEmpty()) {
            return 0;
        }
        else {
            Cart cart = cartPreferences.getCart();
            return cart.getItems().size();
        }
    }

    public Double getTotal() {
        Cart cart = cartPreferences.getCart();
        Double total = 0.0;
        for(Map.Entry<Book, Integer> entry : cart.getItems().entrySet()) {
            Book book = entry.getKey();
            Integer quantity = entry.getValue();
            total += book.getPrice() * quantity.doubleValue();
        }
        return total;
    }
}
