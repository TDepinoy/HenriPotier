package tdepinoy.henripotier;

import android.app.Application;
import android.content.Context;

import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import de.greenrobot.event.EventBus;
import retrofit.Converter.Factory;
import retrofit.JacksonConverterFactory;
import tdepinoy.henripotier.ui.BooksActivity;
import tdepinoy.henripotier.ui.CartActivity;
import tdepinoy.henripotier.ui.adapter.BooksAdapter;
import tdepinoy.henripotier.ui.adapter.CartAdapter;

public class HenriPotierApplication extends Application {

    private static HenriPotierApplication app;
    private  HenriPotierComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        this.component = DaggerHenriPotierApplication_HenriPotierComponent.create();
    }

    public static HenriPotierApplication getApp() {
        return app;
    }

    public HenriPotierComponent getComponent() {
        return component;
    }

    @Component(modules = HenriPotierModule.class)
    @Singleton
    public interface HenriPotierComponent {
        void inject(BooksActivity activity);

        void inject(CartActivity activity);

        void inject(BooksAdapter adapter);

        void inject(CartAdapter adapter);
    }

    @Module
    public static class HenriPotierModule {

        @Provides
        @Singleton
        public EventBus provideBus() {
            return EventBus.getDefault();
        }

        @Provides
        public Factory provideConverter() {
            return JacksonConverterFactory.create();
        }

        @Provides
        public Context provideContext() {
            return getApp();
        }
    }
}
