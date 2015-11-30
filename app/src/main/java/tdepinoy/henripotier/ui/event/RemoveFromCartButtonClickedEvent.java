package tdepinoy.henripotier.ui.event;

import tdepinoy.henripotier.model.Book;

public class RemoveFromCartButtonClickedEvent {

    private Book book;

    public RemoveFromCartButtonClickedEvent(Book book) {
        this.book = book;
    }

    public Book getBook() {
        return book;
    }
}
