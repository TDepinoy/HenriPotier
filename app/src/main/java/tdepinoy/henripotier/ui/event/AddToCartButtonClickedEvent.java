package tdepinoy.henripotier.ui.event;

import tdepinoy.henripotier.model.Book;

public class AddToCartButtonClickedEvent {

    private Book book;

    public AddToCartButtonClickedEvent(Book book) {
        this.book = book;
    }

    public Book getBook() {
        return book;
    }
}
