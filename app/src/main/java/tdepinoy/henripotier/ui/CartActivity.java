package tdepinoy.henripotier.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import tdepinoy.henripotier.HenriPotierApplication;
import tdepinoy.henripotier.R;
import tdepinoy.henripotier.api.event.CommercialOfferEvent;
import tdepinoy.henripotier.cart.CartManager;
import tdepinoy.henripotier.cart.event.CartQuantityChangeEvent;
import tdepinoy.henripotier.cart.event.CartRemovedItemEvent;
import tdepinoy.henripotier.commercialoffer.CommercialOfferManager;
import tdepinoy.henripotier.model.CommercialOffer;
import tdepinoy.henripotier.model.CommercialOffers;
import tdepinoy.henripotier.ui.adapter.CartAdapter;
import tdepinoy.henripotier.ui.event.QuantityButtonClickedEvent;
import tdepinoy.henripotier.ui.event.RemoveFromCartButtonClickedEvent;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class CartActivity extends Activity {

    @Bind(R.id.cart_info_text_view)
    TextView cartInfoTextView;

    @Bind(R.id.cart_original_total_text_view)
    TextView cartOriginalTotalTextView;

    @Bind(R.id.cart_total_text_view)
    TextView cartTotalTextView;

    @Bind(R.id.cart_empty_button)
    Button emptyCartButton;

    @Bind(R.id.cart_list_view)
    ListView cartListView;

    @Inject
    EventBus eventBus;

    @Inject
    CartManager cartManager;

    @Inject
    CommercialOfferManager commercialOfferManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_activity);
        HenriPotierApplication.getApp().getComponent().inject(this);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        eventBus.register(this);
        if (cartManager.isCartEmpty()) {
            showEmptyCart();
            cartListView.setVisibility(GONE);
        } else {
            showCart();
            commercialOfferManager.commercialOffers(cartManager.getCart());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                Intent intent = new Intent(this, BooksActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        eventBus.unregister(this);
    }

    private void showEmptyCart() {
        emptyCartButton.setVisibility(VISIBLE);
        cartOriginalTotalTextView.setVisibility(GONE);
        cartTotalTextView.setVisibility(GONE);
        cartInfoTextView.setText(getString(R.string.cart_empty));
    }

    private void showCart() {
        emptyCartButton.setVisibility(GONE);
        cartOriginalTotalTextView.setVisibility(VISIBLE);
        cartTotalTextView.setVisibility(VISIBLE);
        populateAndShowCartList();
    }

    private void populateAndShowCartList() {
        cartListView.setAdapter(new CartAdapter(this, cartManager.getCart()));
        cartListView.setVisibility(VISIBLE);
        cartInfoTextView.setText(getString(R.string.cart_full, cartManager.getCount()));
    }

    @OnClick(R.id.cart_empty_button)
    public void returnHome() {
        Intent intent = new Intent(this, BooksActivity.class);
        startActivity(intent);
    }

    public void onEventBackgroundThread(RemoveFromCartButtonClickedEvent event) {
        cartManager.removeFromCart(event.getBook());
    }

    public void onEventBackgroundThread(QuantityButtonClickedEvent event) {
        cartManager.changeQuantity(event.getBook(), event.getQuantity());
    }

    public void onEventMainThread(CommercialOfferEvent event) {
        CommercialOffers commercialOffers = event.getCommercialOffers();
        if(commercialOffers != null) {
            Double total = cartManager.getTotal();
            Double reducedTotal = total;
            for (CommercialOffer offer : commercialOffers.getCommercialOffers()) {
                Double totalWithOffer = offer.compute(total);
                if (totalWithOffer < reducedTotal) {
                    reducedTotal = totalWithOffer;
                }
            }
            cartTotalTextView.setText(getString(R.string.cart_total, reducedTotal));
            cartOriginalTotalTextView.setText(getString(R.string.cart_original_total, total));
        }
        else {
            showEmptyCart();
        }
    }

    public void onEventMainThread(CartRemovedItemEvent event) {
        populateAndShowCartList();
        commercialOfferManager.commercialOffers(cartManager.getCart());
    }

    public void onEvent(CartQuantityChangeEvent event) {
        commercialOfferManager.commercialOffers(cartManager.getCart());
    }
}
