package com.ptit.bookecommerce.activity.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ptit.bookecommerce.R;
import com.ptit.bookecommerce.model.Book;
import com.ptit.bookecommerce.utils.Device;
import com.squareup.picasso.Picasso;

public class ListBookAdapter extends ArrayAdapter<Book> {
	Context context;
	int resource;
	List<Book> books;
	Point p;

	public ListBookAdapter(Context context, int resource, List<Book> books) {
		super(context, resource, books);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.resource = resource;
		this.books = books;
		p = Device.getSizeImage(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Book b = books.get(position);
		LayoutInflater inflater = LayoutInflater.from(context);
		convertView = inflater.inflate(resource, null);
		TextView title = (TextView) convertView.findViewById(R.id.book_title);
		ImageView cover = (ImageView) convertView.findViewById(R.id.book_cover);

		TextView priceDiscount = (TextView) convertView
				.findViewById(R.id.book_item_price_discount);

		title.setText(books.get(position).getTitle());
		
		if (b.getDiscount() > 0) {
			priceDiscount.setText("Price: $" + b.getPrice() + " - Discount: -" + b.getDiscount() + "%");
		} else {
			priceDiscount.setText("Price: $" + b.getPrice());
		}
		Picasso.with(context).load(books.get(position).getCoverUrl().trim())
				.error(R.drawable.no_photo).resize(p.x, p.y).into(cover);
		return convertView;
	}
}
