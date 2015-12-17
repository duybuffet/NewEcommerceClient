package com.ptit.bookecommerce.activity.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.paulusworld.drawernavigationtabs.R;
import com.ptit.bookecommerce.model.Genre;
import com.squareup.picasso.Picasso;

public class GenreArrayAdapter extends ArrayAdapter<Genre> {
	private Context context;
	private int layoutId;
	private List<Genre> objects;

	public GenreArrayAdapter(Context context, int layoutId,
			List<Genre> objects) {
		super(context, layoutId, objects);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.layoutId = layoutId;
		this.objects = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(context);
		convertView = inflater.inflate(layoutId, null);
		if (objects.size() > 0 && position >= 0) {
			final TextView tvCatName = (TextView) convertView
					.findViewById(R.id.tvGenreName);
			final ImageView ivCatImage = (ImageView) convertView
					.findViewById(R.id.ivGenreImage);
			tvCatName.setText(objects.get(position).getName());
//			Picasso.with(context)
//					.load(objects.get(position).getCover().toString().trim())
//					.resize(80, 80).into(ivCatImage);
			// ivCatImage.setImageResource(R.drawable.ic_launcher);
		}
		return convertView;
	}
}
