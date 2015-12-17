package com.ptit.bookecommerce.activity.view;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.paulusworld.drawernavigationtabs.MainActivity;
import com.paulusworld.drawernavigationtabs.R;
import com.ptit.bookecommerce.activity.adapter.ListBookAdapter;
import com.ptit.bookecommerce.model.Book;
import com.ptit.bookecommerce.model.Customer;
import com.ptit.bookecommerce.utils.Constants;
import com.ptit.bookecommerce.utils.MyDialog;

public class ProfileFragment extends Fragment implements OnClickListener {
	public final static String TAG = ProfileFragment.class.getSimpleName();
	private View view;
	private MyDialog dialog;
	private LayoutInflater inflater;
	/* Profile components */
	private EditText edFullname, edEmail, edPhone, edCity, edDistrict, edWard,
			edStreetno, edPostal;
	private Button btnChangeProfile;

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	public static ProfileFragment newInstance() {
		return new ProfileFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.inflater = inflater;
		if (Customer.customerLogin != null) {
			view = inflater.inflate(R.layout.activity_profile, null);
			initProfileComponents(view);
		}

		return view;
	}

	private void initProfileComponents(View view) {
		// TODO Auto-generated method stub
		edFullname = (EditText) view.findViewById(R.id.edFullname);
		edEmail = (EditText) view.findViewById(R.id.edEmail);
		edPhone = (EditText) view.findViewById(R.id.edPhone);
		edCity = (EditText) view.findViewById(R.id.edCity);
		edDistrict = (EditText) view.findViewById(R.id.edDistrict);
		edWard = (EditText) view.findViewById(R.id.edWard);
		edStreetno = (EditText) view.findViewById(R.id.edStreetno);
		edPostal = (EditText) view.findViewById(R.id.edPostal);
		btnChangeProfile = (Button) view.findViewById(R.id.btnChangeProfile);

		if (Customer.customerLogin != null) {
			Customer c = Customer.customerLogin;
			if (c.getFullName() != null && !c.getFullName().equals(""))
				edFullname.setText(c.getFullName());

			if (c.getEmail() != null && !c.getEmail().equals(""))
				edEmail.setText(c.getEmail());

			if (c.getPhone() != null && !c.getPhone().equals(""))
				edPhone.setText(c.getPhone());

			if (c.getCity() != null && !c.getCity().equals(""))
				edCity.setText(c.getCity());

			if (c.getDistrict() != null && !c.getDistrict().equals(""))
				edDistrict.setText(c.getDistrict());

			if (c.getWard() != null && !c.getWard().equals(""))
				edWard.setText(c.getWard());

			if (c.getStreetNumber() != null && !c.getStreetNumber().equals(""))
				edStreetno.setText(c.getStreetNumber());

			if (c.getPostalCode() != null && !c.getPostalCode().equals(""))
				edPostal.setText(c.getPostalCode());
		} else {
			Log.e("CUSTOMER LOGIN NULL", "YES");
		}

		btnChangeProfile.setOnClickListener(this);
	}

	@SuppressLint("ShowToast")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		if (v == btnChangeProfile) {
			RequestParams params = new RequestParams();
			params.put("customer_id", Customer.customerLogin.getId() + "");
			params.put("fullname", edFullname.getText().toString());
			params.put("email", edEmail.getText().toString());
			params.put("phone", edPhone.getText().toString());
			params.put("city", edCity.getText().toString());
			params.put("district", edDistrict.getText().toString());
			params.put("ward", edWard.getText().toString());
			params.put("street_number", edStreetno.getText().toString());
			params.put("postal_code", edPostal.getText().toString());

			Log.e("PROFILE PARAMS", params.toString());
			sendData(params, Constants.URL_CUSTOMER_PROFILE);
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
				showMessage("Change profile failed!");
			}

			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.e("SUCCESS PROFILE", "Success : " + response);
				Customer.customerLogin.setCity(edCity.getText().toString());
				Customer.customerLogin.setDistrict(edDistrict.getText()
						.toString());
				Customer.customerLogin.setEmail(edEmail.getText().toString());
				Customer.customerLogin.setFullName(edFullname.getText()
						.toString());
				Customer.customerLogin.setPhone(edPhone.getText().toString());
				Customer.customerLogin.setPostalCode(edPostal.getText()
						.toString());
				Customer.customerLogin.setStreetNumber(edStreetno.getText()
						.toString());
				Customer.customerLogin.setWard(edWard.getText().toString());				
				
				Intent intent = new Intent(view.getContext(),
						MainActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString(Constants.MESSAGE_RES,
						Constants.CHANGE_PROFILE_SUCCESS);
				bundle.putInt(Constants.MESSAGE_FRAGMENT,
						Constants.FRAGMENT_HOME);
				intent.putExtra(Constants.MESSAGE, bundle);
				startActivity(intent);
			}
		});

	}

	private void showMessage(String msg) {
		// TODO Auto-generated method stub
		Toast.makeText(getActivity().getApplicationContext(), msg,
				Toast.LENGTH_LONG).show();
	}
}
