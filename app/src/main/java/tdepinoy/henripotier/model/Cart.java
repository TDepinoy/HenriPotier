package tdepinoy.henripotier.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Map;
import java.util.TreeMap;

import tdepinoy.henripotier.cart.serialization.CartDeserializer;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Cart {

    @JsonDeserialize(keyUsing = CartDeserializer.class)
    private TreeMap<Book, Integer> items;

    @JsonCreator
    public Cart(@JsonProperty("items") TreeMap<Book, Integer> items) {
        this.items = items;
    }

    public Map<Book, Integer> getItems() {
        return items;
    }

    public void addToCart(Book book) {
        if(items.containsKey(book)) {
            items.put(book, items.get(book) + 1);
        }
        else {
            items.put(book, 1);
        }
    }

    public void removeFromCart(Book book) {
        if(items.containsKey(book)) {
            items.remove(book);
        }
    }

    public void changeQuantity(Book book, Integer quantity) {
        if(items.containsKey(book)) {
            items.put(book, quantity);
        }
    }

    public Integer getQuantity(Book book) {
        Integer result = null;
        if (items.containsKey(book)) {
            result = items.get(book);
        }
        return result;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "items=" + items +
                '}';
    }
}
