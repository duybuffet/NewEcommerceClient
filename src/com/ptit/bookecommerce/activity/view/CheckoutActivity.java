package com.ptit.bookecommerce.activity.view;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ptit.bookecommerce.R;
import com.ptit.bookecommerce.model.Customer;
import com.ptit.bookecommerce.utils.Constants;

public class CheckoutActivity extends Activity implements OnClickListener {
	private EditText edShippingAddr, edShippingName, edShippingPhone, edNote;
	private Button btnPlaceOrder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkout);
		
		
		// get action bar   
        ActionBar actionBar = getActionBar();
 
        // Enabling Up / Back navigation
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Order Books");
		
		edShippingAddr = (EditText) findViewById(R.id.edShippingAddre);
		edShippingName = (EditText) findViewById(R.id.edShippingName);
		edShippingPhone = (EditText) findViewById(R.id.edShippingPhone);
		edNote = (EditText) findViewById(R.id.edNote);
		btnPlaceOrder = (Button) findViewById(R.id.btnPlaceOrder);
		btnPlaceOrder.setOnClickListener(this);
		Customer c;
		if ((c = Customer.customerLogin) != null
				&& MainActivity.cart.getListItems().size() > 0) {
			edShippingAddr.setText(c.getStreetNumber() + "\n" + c.getWard()
					+ "\n" + c.getDistrict() + "\n" + c.getCity() + "\n"
					+ c.getPostalCode());
			edShippingName.setText(c.getFullName());
			edShippingPhone.setText(c.getPhone());
		} else {
			showMessage("Your cart is empty or you haven'n login yet");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.checkout, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == btnPlaceOrder) {
			if (edShippingAddr.getText().toString().equals("")
					|| edShippingName.getText().toString().equals("")
					|| edShippingPhone.getText().toString().equals("")) {
				showMessage("Please fill all the neccessary information");
			} else {
				RequestParams params = new RequestParams();
				params.put("total", MainActivity.cart.getTotal() + "");
				params.put("customer_id", Customer.customerLogin.getId() + "");
				params.put("shipping_address", edShippingAddr.getText()
						.toString());
				params.put("shipping_phone", edShippingPhone.getText()
						.toString());
				params.put("shipping_fullname", edShippingName.getText()
						.toString());
				params.put("note", edNote.getText().toString());

				Gson gson = new Gson();
				String res = gson.toJson(MainActivity.cart.getListItems());
				params.put("list_item", res);
				Log.e("PARAMS", params.toString());
				sendData(params, Constants.URL_ORDER);
			}
		}
	}

	public void sendData(RequestParams params, String url) {
		// Make RESTful webservice call using AsyncHttpClient object
		AsyncHttpClient client = new AsyncHttpClient();
		client.post(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
			}

			@Override
			public void onFailure(int statusCode, Throwable error,
					String content) {
				// TODO Auto-generated method stub

				Log.e("OUTPUT CAT CONNECT", "Fail : " + content);
				showMessage("Place order failed!");
			}

			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub				
				MainActivity.cart.deleteAll();
				Intent intent = new Intent(CheckoutActivity.this,
						MainActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString(Constants.MESSAGE_RES, Constants.CHECKOUT_SUCCESS);
				intent.putExtra(Constants.MESSAGE, bundle);
				startActivity(intent);
			}
		});

	}

	private void showMessage(String msg) {
		// TODO Auto-generated method stub
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
}
