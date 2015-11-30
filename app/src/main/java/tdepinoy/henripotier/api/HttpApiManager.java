package tdepinoy.henripotier.api;

import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.greenrobot.event.EventBus;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Converter.Factory;
import retrofit.Response;
import retrofit.Retrofit;
import tdepinoy.henripotier.HenriPotierApplication;
import tdepinoy.henripotier.R;
import tdepinoy.henripotier.api.event.BooksEvent;
import tdepinoy.henripotier.api.event.CommercialOfferEvent;
import tdepinoy.henripotier.model.Book;
import tdepinoy.henripotier.model.CommercialOffer;
import tdepinoy.henripotier.model.CommercialOffers;

@Singleton
public class HttpApiManager {

    private final Logger logger = LoggerFactory.getLogger(HttpApiManager.class);


    public static final String API_BASE_URL = "http://henri-potier.xebia.fr/";

    private static final int TIMEOUT = 30;

    private final HttpApi httpApi;
    private final EventBus bus;

    @Inject
    public HttpApiManager(EventBus bus, Factory converterFactory) {
        final OkHttpClient client = new OkHttpClient();
        client.setReadTimeout(TIMEOUT, TimeUnit.SECONDS);
        client.setConnectTimeout(TIMEOUT, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(converterFactory)
                .client(client)
                .build();

        this.bus = bus;

        httpApi = retrofit.create(HttpApi.class);
    }

    public void books() {
        Call<List<Book>> call = httpApi.books();
        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Response<List<Book>> response, Retrofit retrofit) {
                bus.post(new BooksEvent(response.body()));
            }

            @Override
            public void onFailure(Throwable t) {
                fail(t);
            }
        });
    }

    public void commercialOffers(String isbnList) {
        Call<CommercialOffers> call = httpApi.commercialOffers(isbnList);
        call.enqueue(new Callback<CommercialOffers>() {
            @Override
            public void onResponse(Response<CommercialOffers> response, Retrofit retrofit) {
                bus.post(new CommercialOfferEvent(response.body()));
            }

            @Override
            public void onFailure(Throwable t) {
                fail(t);
            }
        });
    }


    private void fail(Throwable throwable) {
        logger.error("Network unreachable: ", throwable);
        Toast.makeText(HenriPotierApplication.getApp(), R.string.network_error,Toast.LENGTH_LONG).show();
    }
}
