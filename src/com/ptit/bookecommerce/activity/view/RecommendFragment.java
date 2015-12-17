package com.ptit.bookecommerce.activity.view;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ptit.bookecommerce.R;
import com.ptit.bookecommerce.activity.adapter.HomeAdapter;
import com.ptit.bookecommerce.activity.adapter.ListBookAdapter;
import com.ptit.bookecommerce.model.Book;
import com.ptit.bookecommerce.utils.Constants;
import com.ptit.bookecommerce.utils.MyDialog;

public class RecommendFragment extends Fragment implements OnClickListener {
	HomeAdapter adapter;
	GridView gripView;
	View view;
	MyDialog myDialog = new MyDialog();
	ArrayList<Book> data;
	int curentPage;
	RequestParams params;
	String type, url;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_oauth, null);
		curentPage = 1;
		data = new ArrayList<Book>();
		params = new RequestParams();
		gripView = (GridView) view.findViewById(R.id.listRecommendBooks);
		
		

		getData(params, Constants.URL_BOOKS_NEW);
		return view;
	}

	public void getData(RequestParams params, String url) {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub

				super.onStart();
				myDialog.startDialog(view.getContext(), "Books Load",
						"Loading!Please wait...");
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				myDialog.endDialog();
			}

			@Override
			public void onFailure(int statusCode, Throwable error,
					String content) {
				// TODO Auto-generated method stub

				Log.d("Out put", "Fail");
			}

			@Override
			public void onSuccess(String response) {

				try {
					// JSON Object

					JSONObject jsonObject = new JSONObject(response);
					JSONArray arr = jsonObject.getJSONArray("books");
					for (int i = 0; i < arr.length(); i++) {
						JSONObject obj = arr.getJSONObject(i);
						Book b = new Book();
						b.setId(obj.getInt("id"));
						b.setTitle(obj.getString("title"));
						b.setCoverUrl(Constants.URL_BOOK_IMAGE + obj.getString("cover_url"));
						b.setPrice(Float.parseFloat(obj.getString("price")));
						b.setDiscount(obj.getInt("discount"));
						data.add(b);
					}

					gripView.setAdapter(new ListBookAdapter(getActivity()
							.getBaseContext(), R.layout.item_book, data));
					gripView.setOnItemClickListener(new OnItemClickListener() {
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							transformActivity(data.get(position).getId());
						}
					});
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	public void transformActivity(int book_id) {
		Intent intent = new Intent(view.getContext(), BookDetailsActivity.class);
		intent.putExtra("book_id", String.valueOf(book_id));
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		params.put("type", type);
		curentPage += 1;
		params.put("page", String.valueOf(curentPage));
		getData(params, url);
	}

}