package tdepinoy.henripotier.cart;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.TreeMap;

import de.greenrobot.event.EventBus;
import tdepinoy.henripotier.cart.event.CartAddedItemEvent;
import tdepinoy.henripotier.cart.event.CartQuantityChangeEvent;
import tdepinoy.henripotier.cart.event.CartRemovedItemEvent;
import tdepinoy.henripotier.model.Book;
import tdepinoy.henripotier.model.Cart;
import tdepinoy.henripotier.preferences.CartPreferences;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartManagerTest {

    @Mock
    CartPreferences cartPreferences;

    @Mock
    EventBus bus;

    @InjectMocks
    CartManager cartManager = new CartManager(cartPreferences, bus);

    Cart cart;

    Book b1, b2, b3;

    @Before
    public void init() {
        b1 = new Book("isbn1", "titre1", "cover1", 10.0);
        b2 = new Book("isbn2", "titre2", "cover2", 20.0);
        b3 = new Book("isbn3", "titre3", "cover3", 30.0);


        TreeMap<Book, Integer> map = new TreeMap<>();
        map.put(b1, 1);
        map.put(b2, 2);
        map.put(b3, 3);
        cart = new Cart(map);
    }

    @Test
    public void emptyCart() {
        Cart emptyCart = new Cart(new TreeMap<Book, Integer>());
        when(cartPreferences.getCart()).thenReturn(emptyCart);
        assertThat(cartManager.isCartEmpty()).isTrue();
    }

    @Test
    public void filledCart() {
        when(cartPreferences.getCart()).thenReturn(cart);
        assertThat(cartManager.isCartEmpty()).isFalse();
    }

    @Test
    public void addToCart() {
        Book b4 = new Book("isbn4", "titre4", "cover4", 40.0);
        when(cartPreferences.getCart()).thenReturn(cart);
        cartManager.addToCart(b4);
        assertThat(cartManager.getCart().getItems()).containsKey(b4);
        assertThat(cartManager.getCount()).isEqualTo(4);
        ArgumentCaptor<CartAddedItemEvent> cartAddedItemEventArgumentCaptor = ArgumentCaptor.forClass(CartAddedItemEvent.class);
        verify(bus).post(cartAddedItemEventArgumentCaptor.capture());
    }

    @Test
    public void removeFromCart() {
        when(cartPreferences.getCart()).thenReturn(cart);
        cartManager.removeFromCart(b2);
        assertThat(cartManager.getCart().getItems()).doesNotContainKey(b2);
        ArgumentCaptor<CartRemovedItemEvent> cartRemovedItemEventArgumentCaptor = ArgumentCaptor.forClass(CartRemovedItemEvent.class);
        verify(bus).post(cartRemovedItemEventArgumentCaptor.capture());
    }

    @Test
    public void changeQuantity() {
        when(cartPreferences.getCart()).thenReturn(cart);
        cartManager.changeQuantity(b1, 3);
        assertThat(cartManager.getCart().getQuantity(b1)).isEqualTo(3);
        ArgumentCaptor<CartQuantityChangeEvent> cartQuantityChangeEventArgumentCaptor = ArgumentCaptor.forClass(CartQuantityChangeEvent.class);
        verify(bus).post(cartQuantityChangeEventArgumentCaptor.capture());
    }

    @Test
    public void total() {
        when(cartPreferences.getCart()).thenReturn(cart);
        assertThat(cartManager.getTotal()).isEqualTo(140.0);
    }

}