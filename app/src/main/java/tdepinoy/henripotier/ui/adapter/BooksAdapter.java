package tdepinoy.henripotier.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import tdepinoy.henripotier.HenriPotierApplication;
import tdepinoy.henripotier.R;
import tdepinoy.henripotier.model.Book;
import tdepinoy.henripotier.ui.event.AddToCartButtonClickedEvent;

public class BooksAdapter extends BaseAdapter {

    private static final int WIDTH = 300;
    private static final int HEIGHT = 400;

    private final LayoutInflater inflater;
    private final Picasso picasso;

    private List<Book> books;

    @Inject
    EventBus eventBus;

    public BooksAdapter(Context context, List<Book> books) {
        HenriPotierApplication.getApp().getComponent().inject(this);
        this.inflater = LayoutInflater.from(context);
        this.picasso = Picasso.with(context);
        this.books = books;
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
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = inflater.inflate(R.layout.book_item, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        Book book = getItem(position);
        picasso.load(book.getCoverUrl())
                .centerCrop()
                .resize(WIDTH, HEIGHT)
                .into(holder.cover);

        holder.title.setText(book.getTitle());
        holder.price.setText(String.format("%.2f", book.getPrice()));
        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = getItem(position);
                eventBus.post(new AddToCartButtonClickedEvent(book));
            }
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

        @Bind(R.id.book_add)
        Button addToCart;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
