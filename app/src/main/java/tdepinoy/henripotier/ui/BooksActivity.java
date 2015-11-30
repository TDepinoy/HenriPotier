package tdepinoy.henripotier.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import tdepinoy.henripotier.HenriPotierApplication;
import tdepinoy.henripotier.R;
import tdepinoy.henripotier.api.event.BooksEvent;
import tdepinoy.henripotier.book.BookManager;
import tdepinoy.henripotier.cart.CartManager;
import tdepinoy.henripotier.cart.event.CartAddedItemEvent;
import tdepinoy.henripotier.ui.adapter.BooksAdapter;
import tdepinoy.henripotier.ui.event.AddToCartButtonClickedEvent;

public class BooksActivity extends Activity {

    @Bind(R.id.books_list_view)
    ListView listView;

    @Bind(R.id.books_progress_bar)
    ProgressBar progressBar;

    @Inject
    EventBus eventBus;

    @Inject
    BookManager bookManager;

    @Inject
    CartManager cartManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.books_activity);
        HenriPotierApplication.getApp().getComponent().inject(this);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        eventBus.register(this);
        listView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        bookManager.books();
    }

    @Override
    protected void onPause() {
        super.onPause();
        eventBus.unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.books_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cart:
                Intent intent = new Intent(this, CartActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void onEventBackgroundThread(AddToCartButtonClickedEvent event) {
        cartManager.addToCart(event.getBook());
    }

    public void onEventMainThread(CartAddedItemEvent event) {
        Toast.makeText(this, R.string.cart_add_success, Toast.LENGTH_SHORT).show();
    }

    public void onEventMainThread(BooksEvent event) {
        BooksAdapter booksAdapter = new BooksAdapter(this, event.getBooks());
        listView.setAdapter(booksAdapter);
        progressBar.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
    }
}
