package tdepinoy.henripotier.api.event;

import java.util.List;

import tdepinoy.henripotier.model.Book;

public class BooksEvent {

    private List<Book> books;

    public BooksEvent(List<Book> books) {
        this.books = books;
    }

    public List<Book> getBooks() {
        return books;
    }
}
