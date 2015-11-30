package tdepinoy.henripotier.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import tdepinoy.henripotier.HenriPotierApplication;
import tdepinoy.henripotier.R;
import tdepinoy.henripotier.model.Book;
import tdepinoy.henripotier.model.Cart;
import tdepinoy.henripotier.ui.event.QuantityButtonClickedEvent;
import tdepinoy.henripotier.ui.event.RemoveFromCartButtonClickedEvent;

public class CartAdapter extends BaseAdapter {

    private static final int WIDTH = 300;
    private static final int HEIGHT = 400;

    private final LayoutInflater inflater;
    private final Picasso picasso;
    private final Integer[] quantities = new Integer[] {
        1, 2, 3, 4, 5, 6, 7, 8, 9, 10
    };

    private Context context;

    private Cart cart;
    private List<Book> books;

    @Inject
    EventBus bus;

    public CartAdapter(Context context, Cart cart) {
        HenriPotierApplication.getApp().getComponent().inject(this);
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.picasso = Picasso.with(context);
        this.books = new ArrayList<>(cart.getItems().keySet());
        this.cart = cart;
    }

    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Book getItem(int position) {
        return books.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = inflater.inflate(R.layout.cart_book_item, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        final Book book = getItem(position);
        picasso.load(book.getCoverUrl())
                .centerCrop()
                .resize(WIDTH, HEIGHT)
                .into(holder.cover);

        holder.title.setText(book.getTitle());
        holder.price.setText(String.format("%.2f", book.getPrice()));
        holder.removeFromCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bus.post(new RemoveFromCartButtonClickedEvent(book));
            }
        });


        ArrayAdapter<Integer> quantitiesAdapter = new ArrayAdapter<Integer>(context, android.R.layout.simple_spinner_item, quantities);
        quantitiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        int quantityPosition = quantitiesAdapter.getPosition(cart.getQuantity(book));
        holder.setQuantity.setAdapter(quantitiesAdapter);
        holder.setQuantity.setSelection(quantityPosition);
        holder.setQuantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bus.post(new QuantityButtonClickedEvent(book, (Integer) holder.setQuantity.getSelectedItem()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        return view;
    }

    protected static class ViewHolder {
        @Bind(R.id.book_cover)
        ImageView cover;

        @Bind(R.id.book_title)
        TextView title;

        @Bind(R.id.book_price)
        TextView price;

        @Bind(R.id.book_remove)
        Button removeFromCart;

        @Bind(R.id.book_set_quantity)
        Spinner setQuantity;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
