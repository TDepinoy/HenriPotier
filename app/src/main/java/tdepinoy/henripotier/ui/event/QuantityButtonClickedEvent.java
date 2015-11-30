package tdepinoy.henripotier.ui.event;

import tdepinoy.henripotier.model.Book;

public class QuantityButtonClickedEvent {

    private Book book;
    private Integer quantity;

    public QuantityButtonClickedEvent(Book book, Integer quantity) {
        this.book = book;
        this.quantity = quantity;
    }

    public Book getBook() {
        return book;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
