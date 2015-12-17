package com.ptit.bookecommerce.activity.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.paulusworld.drawernavigationtabs.MainActivity;
import com.paulusworld.drawernavigationtabs.R;
import com.ptit.bookecommerce.activity.view.CartFragment;
import com.ptit.bookecommerce.model.LineItem;
import com.ptit.bookecommerce.utils.Constants;

public class CartItemsArrayAdapter extends ArrayAdapter<LineItem> {
	Activity context = null;
	List<LineItem> lineItems = null;
	int layoutId;
	private ViewHolder holder;

	public CartItemsArrayAdapter(Activity context, int layoutId,
			List<LineItem> lineItems) {
		super(context, layoutId, lineItems);
		// TODO Auto-generated constructor stub
		this.lineItems = lineItems;
		this.layoutId = layoutId;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		LayoutInflater inflater = context.getLayoutInflater();
		convertView = inflater.inflate(layoutId, null);
		/*
		 * Recall that the variable position is sent in as an argument to this
		 * method. The variable simply refers to the position of the current
		 * object in the list. (The ArrayAdapter iterates through the list we
		 * sent it)
		 * 
		 * Therefore, i refers to the current Item object.
		 */
		final LineItem item = lineItems.get(position);
		if (item != null) {
			holder = new ViewHolder();
			holder.bookTitle = (TextView) convertView
					.findViewById(R.id.cart_book_title);
			holder.bookPriceVal = (TextView) convertView
					.findViewById(R.id.cart_book_price_val);
			holder.bookDiscountPriceVal = (TextView) convertView
					.findViewById(R.id.cart_book_price_discount_val);
			holder.qty = (Spinner) convertView.findViewById(R.id.spinner1);
			holder.cancel = (ImageButton) convertView
					.findViewById(R.id.deleteCartItem);
			holder.multiplication = (TextView) convertView
					.findViewById(R.id.cart_item_multiplication);
			holder.savingsVal = (TextView) convertView
					.findViewById(R.id.cart_item_savings_value);

			holder.bookTitle.setText(item.getBook().getTitle());

			holder.bookPriceVal.setText("$" + item.getBook().getPrice());

			holder.cancel.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					deleteItem(item);
				}
			});

			if (item.getBook().getDiscount() > 0)
				holder.bookDiscountPriceVal.setText(" - "
						+ item.getBook().getDiscount() + "%");

			double savingTotal = 0.0;
			if (item.getBook().getDiscount() > 0) {
				savingTotal = (Double.parseDouble(item.getBook().getDiscount()
						+ "") / 100.0)
						* item.getBook().getPrice() * item.getQuantity();
				holder.savingsVal.setText("$" + savingTotal);
			} else {
				holder.savingsVal.setText("" + savingTotal);
			}

			double total = item.getBook().getPrice() * item.getQuantity()
					- savingTotal;
			holder.multiplication.setText("$" + total);
			
			
			ArrayAdapter<String> aa = new ArrayAdapter<String>(context,
					R.layout.qty_spinner_item, Constants.qtyValues);
			aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			holder.qty.setAdapter(aa);
			holder.qty.setSelection(item.getQuantity());
			holder.qty.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					if (position == 0) {
						deleteItem(item);
					} else if (position != item.getQuantity()) {
						item.setQuantity(position);
						CartFragment.totalAmount.setText("Total: $" + MainActivity.cart.getTotal());
						notifyDataSetChanged();
					}
				}

				

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub
					
				}
				
			});
			
			
		}

		return convertView;
	}
	
	private void deleteItem(LineItem item) {
		// TODO Auto-generated method stub
		MainActivity.cart.delete(item.getBook());
		CartFragment.totalAmount.setText("Total: $" + MainActivity.cart.getTotal());
		CartFragment.itemCount.setText("(" + MainActivity.cart.getListItems().size() + ")");
		notifyDataSetChanged();
	}
}

class ViewHolder {
	TextView bookTitle;
	TextView bookPriceVal;
	TextView bookDiscountPriceVal;
	TextView savingsVal;
	TextView multiplication;
	ImageButton cancel;
	Spinner qty;

}
