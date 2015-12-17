package com.ptit.bookecommerce.activity.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ptit.bookecommerce.R;
import com.ptit.bookecommerce.activity.adapter.CartItemsArrayAdapter;
import com.ptit.bookecommerce.model.Book;
import com.ptit.bookecommerce.model.Cart;
import com.ptit.bookecommerce.model.Customer;
import com.ptit.bookecommerce.utils.Constants;

public class CartFragment extends Fragment implements View.OnClickListener {
	public final static String TAG = CartFragment.class.getSimpleName();
	private Button btnCheckout;
	private View view;
	public static TextView totalAmount, itemCount;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	public static CartFragment newInstance() {
		return new CartFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		/* init comps */
		view = inflater.inflate(R.layout.fragment_cart, null);

		initComponents();
		return view;
	}

	private void initComponents() {
		// TODO Auto-generated method stub
		MainActivity activity = (MainActivity) getActivity();

		TextView itemText = (TextView) view.findViewById(R.id.item_text);
		itemCount = (TextView) view.findViewById(R.id.item_count);
		totalAmount = (TextView) view.findViewById(R.id.total_amount);
		btnCheckout = (Button) view.findViewById(R.id.btnCheckout);
		ListView lv1 = (ListView) view.findViewById(R.id.listView1);
		TextView cartEmpty = (TextView) view.findViewById(R.id.cart_empty);

		if (MainActivity.cart.getListItems().size() == 0 || MainActivity.cart.getListItems() == null) {
			itemText.setVisibility(view.INVISIBLE);
			itemCount.setVisibility(view.INVISIBLE);
			totalAmount.setVisibility(view.INVISIBLE);
			btnCheckout.setVisibility(view.INVISIBLE);
			lv1.setVisibility(view.INVISIBLE);
			cartEmpty.setVisibility(view.VISIBLE);
		}

		else {
			itemText.setVisibility(view.VISIBLE);
			itemCount.setVisibility(view.VISIBLE);
			totalAmount.setVisibility(view.VISIBLE);
			btnCheckout.setVisibility(view.VISIBLE);
			lv1.setVisibility(view.VISIBLE);
			cartEmpty.setVisibility(view.INVISIBLE);
			btnCheckout.setOnClickListener(CartFragment.this);
		}
		itemCount.setText("(" + MainActivity.cart.getListItems().size() + ")");
		totalAmount.setText("Total: $" + MainActivity.cart.getTotal());
		lv1.setAdapter(new CartItemsArrayAdapter(getActivity(), R.layout.item_cart_line, MainActivity.cart.getListItems()));				
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == btnCheckout) {
			if (MainActivity.cart.getListItems().size() == 0) {
				Toast.makeText(getActivity(), "Your cart is still empty. Go back and choose some stuffs", Toast.LENGTH_SHORT).show();
			} else {
				if (Customer.customerLogin == null) {					
					Toast.makeText(getActivity(), "You must login first to checkout!", Toast.LENGTH_SHORT).show();
					MainActivity.friendlyUrl = Constants.FRAGMENT_CHECKOUT;
					Log.e("friendlyUrl", MainActivity.friendlyUrl + "");
				} else {
					Intent i = new Intent(view.getContext(), CheckoutActivity.class);
					startActivity(i);
				}
				
			}
		} 
	}

}
