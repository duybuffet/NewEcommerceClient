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
import com.ptit.bookecommerce.model.Ebook;
import com.ptit.bookecommerce.utils.Device;
import com.squareup.picasso.Picasso;

public class HomeAdapter extends ArrayAdapter<Ebook> {
	Context context;
	int resource;
	List<Ebook> ebooks;
	Point p;

	public HomeAdapter(Context context, int resource, List<Ebook> ebooks) {
		super(context, resource, ebooks);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.resource = resource;
		this.ebooks = ebooks;
		p = Device.getSizeImage(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(context);
		convertView = inflater.inflate(resource, null);
		TextView ebookname = (TextView) convertView
				.findViewById(R.id.book_title);
		ImageView ebook_img = (ImageView) convertView
				.findViewById(R.id.book_cover);

		ebookname.setText(ebooks.get(position).getName());
		Picasso.with(context).load(ebooks.get(position).getCover().trim())
				.resize(p.x, p.y).into(ebook_img);
		return convertView;
	}
}
