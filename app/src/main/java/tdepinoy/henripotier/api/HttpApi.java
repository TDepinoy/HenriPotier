package tdepinoy.henripotier.api;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import tdepinoy.henripotier.model.Book;
import tdepinoy.henripotier.model.CommercialOffer;
import tdepinoy.henripotier.model.CommercialOffers;

public interface HttpApi {

    @GET("/books")
    Call<List<Book>> books();

    @GET("/books/{isbn}/commercialOffers")
    Call<CommercialOffers> commercialOffers(@Path("isbn")String isbnList);
}
