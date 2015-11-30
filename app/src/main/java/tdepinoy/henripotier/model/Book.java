package tdepinoy.henripotier.model;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Book implements Comparable<Book> {

    private final String isbn;
    private final String title;
    private final String coverUrl;
    private final Double price;


    @JsonCreator
    public Book (@JsonProperty("isbn") String isbn,
                 @JsonProperty("title") String title,
                 @JsonProperty("cover") String coverUrl,
                 @JsonProperty("price") Double price) {
        this.isbn = isbn;
        this.title = title;
        this.coverUrl = coverUrl;
        this.price = price;
    }


    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return !(isbn != null ? !isbn.equals(book.isbn) : book.isbn != null);

    }

    @Override
    public int hashCode() {
        return isbn != null ? isbn.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "{\"isbn\": \"" + isbn + "\""
        + ",\"title\": \"" + title + "\""
        + ",\"price\":" + price
        + ",\"coverUrl\": \"" + coverUrl + "\""
        + '}';
    }

    @Override
    public int compareTo(@NonNull Book another) {
        return title.compareTo(another.title);
    }
}
