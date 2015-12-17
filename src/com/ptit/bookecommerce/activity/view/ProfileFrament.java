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

public class ProfileFrament extends Fragment implements OnClickListener {
	public final static String TAG = ProfileFrament.class.getSimpleName();
	private View view;
	private MyDialog dialog;

	/* Login components */
	private EditText edName;
	private EditText edPass;
	private Button btnLogin;
	private TextView tvRegister;
	private TextView tvLogin;
	private TextView txtError;
	private LayoutInflater inflater;

	/* Sign up components */
	private EditText edRePass;
	private Button btnRegister;

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	/* Profile components */
	private EditText edFullname, edEmail, edPhone, edCity, edDistrict, edWard,
			edStreetno, edPostal;
	private Button btnChangeProfile;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}

	public static ProfileFrament newInstance() {
		return new ProfileFrament();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.inflater = inflater;
		if (Customer.customerLogin == null) {
			view = inflater.inflate(R.layout.activity_login, null);
			initLoginComponents(view);
		} else {
			Log.e("Customer LOGIn IN PROFILE FRAGMENT",
					Customer.customerLogin.getId() + "-");
			Log.e("Customer LOGIn IN PROFILE FRAGMENT NAME",
					Customer.customerLogin.getFullName() + "-");
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

	private void initLoginComponents(View view) {
		// TODO Auto-generated method stub
		edName = (EditText) view.findViewById(R.id.editName);
		edPass = (EditText) view.findViewById(R.id.editPass);
		edRePass = (EditText) view.findViewById(R.id.editRePass);
		txtError = (TextView) view.findViewById(R.id.textError);

		btnLogin = (Button) view.findViewById(R.id.btnLogin);
		btnRegister = (Button) view.findViewById(R.id.btnRegister);

		tvRegister = (TextView) view.findViewById(R.id.tvRegister);
		tvLogin = (TextView) view.findViewById(R.id.tvLogin);

		btnLogin.setOnClickListener(ProfileFrament.this);
		btnRegister.setOnClickListener(ProfileFrament.this);
		tvRegister.setOnClickListener(ProfileFrament.this);
		tvLogin.setOnClickListener(ProfileFrament.this);
	}

	@SuppressLint("ShowToast")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		if (v == btnLogin) {
			String username = edName.getText().toString();
			String pass = edPass.getText().toString();
			if (username.trim().equals("") || pass.trim().equals("")) {
				txtError.setText("Please fill username and password");
			} else {
				RequestParams params = new RequestParams();
				params.put("username", username.trim());
				params.put("password", pass.trim());
				getDataLogin(params, Constants.URL_CUSTOMER_LOGIN);
			}

		} else if (v == btnRegister) {
			String username = edName.getText().toString();
			String pass = edPass.getText().toString();
			String repass = edRePass.getText().toString();
			if (username.trim().equals("") || pass.trim().equals("")
					|| !pass.equals(repass) || pass.length() < 6) {
				txtError.setText("Please fill username/password and password must same as retype password and at least 6 chars");
			} else {
				RequestParams params = new RequestParams();
				params.put("username", username.trim());
				params.put("password", pass.trim());
				getDataRegister(params, Constants.URL_CUSTOMER_REGISTER);
			}

		} else if (v == tvRegister) {
			btnLogin.setVisibility(View.GONE);
			btnRegister.setVisibility(View.VISIBLE);
			tvRegister.setVisibility(View.GONE);
			tvLogin.setVisibility(View.VISIBLE);
			edRePass.setVisibility(View.VISIBLE);
		} else if (v == tvLogin) {
			btnLogin.setVisibility(View.VISIBLE);
			btnRegister.setVisibility(View.GONE);
			tvRegister.setVisibility(View.VISIBLE);
			tvLogin.setVisibility(View.GONE);
			edRePass.setVisibility(View.GONE);
		} else if (v == btnChangeProfile) {
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

	public void getDataLogin(RequestParams params, String url) {
		// Make RESTful webservice call using AsyncHttpClient object
		AsyncHttpClient client = new AsyncHttpClient();
		String msg = "";
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

				Log.e("OUTPUT BOOKGENRE CONNECT", "Fail : " + content);
				txtError.setText("Login failed. Please use right username/password");
			}

			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.d("SUccess", response);
				try {
					JSONObject obj = new JSONObject(new JSONObject(response)
							.getString("customer_login"));
					if (Customer.customerLogin == null) {
						int id = obj.getInt("id");
						String fullName = !obj.getString("fullname")
								.equalsIgnoreCase("null") ? obj
								.getString("fullname") : "";
						String email = !obj.getString("email")
								.equalsIgnoreCase("null") ? obj
								.getString("email") : "";
						String phone = !obj.getString("phone")
								.equalsIgnoreCase("null") ? obj
								.getString("phone") : "";
						String city = !obj.getString("city").equalsIgnoreCase(
								"null") ? obj.getString("city") : "";
						String district = !obj.getString("district")
								.equalsIgnoreCase("null") ? obj
								.getString("district") : "";
						String ward = !obj.getString("ward").equalsIgnoreCase(
								"null") ? obj.getString("ward") : "";
						String streetNumber = !obj.getString("street_number")
								.equalsIgnoreCase("null") ? obj
								.getString("street_number") : "";
						String postalCode = !obj.getString("postal_code")
								.equalsIgnoreCase("null") ? obj
								.getString("postal_code") : "";

						Customer.customerLogin = new Customer(id, fullName,
								email, phone, city, district, ward,
								streetNumber, postalCode);

						Intent intent = new Intent(view.getContext(),
								MainActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("res", Constants.LOGIN_SUCCESS);
						intent.putExtra("message", bundle);
						startActivity(intent);
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					Log.e("JSONException", e.getMessage());
				}

			}
		});
	}

	public void getDataRegister(RequestParams params, String url) {
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

				Log.e("OUTPUT BOOKGENRE CONNECT", "Fail : " + content);
				txtError.setText("Username's existed. Please use another username");
			}

			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.d("Success", response);
				try {
					JSONObject obj = new JSONObject(new JSONObject(response)
							.getString("customer_register"));
					if (Customer.customerLogin == null) {
						int id = obj.getInt("id");

						Customer.customerLogin = new Customer();
						Customer.customerLogin.setId(id);
						Intent intent = new Intent(view.getContext(),
								MainActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("res", Constants.REGISTER_SUCCESS);
						bundle.putInt("fragment", Constants.FRAGMENT_PROFILE);
						intent.putExtra("message", bundle);
						startActivity(intent);
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					Log.e("JSONException", e.getMessage());
				}

			}
		});
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
				Customer.customerLogin.setDistrict(edDistrict.getText().toString());
				Customer.customerLogin.setEmail(edEmail.getText().toString());
				Customer.customerLogin.setFullName(edFullname.getText().toString());
				Customer.customerLogin.setPhone(edPhone.getText().toString());
				Customer.customerLogin.setPostalCode(edPostal.getText().toString());
				Customer.customerLogin.setStreetNumber(edStreetno.getText().toString());
				Customer.customerLogin.setWard(edWard.getText().toString());
				showMessage("Change profile successfully!");
			}
		});

	}

	private void showMessage(String msg) {
		// TODO Auto-generated method stub
		Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_LONG).show();
	}
}
