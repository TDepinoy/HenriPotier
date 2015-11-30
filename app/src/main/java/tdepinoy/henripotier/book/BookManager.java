package tdepinoy.henripotier.book;

import javax.inject.Inject;

import tdepinoy.henripotier.api.HttpApiManager;

public class BookManager {

    private HttpApiManager httpApiManager;

    @Inject
    public BookManager(HttpApiManager httpApiManager) {
        this.httpApiManager = httpApiManager;
    }

    public void books() {
        httpApiManager.books();
    }
}
