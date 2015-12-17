package com.ptit.bookecommerce.activity.view;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ptit.bookecommerce.R;
import com.ptit.bookecommerce.activity.adapter.ListBookAdapter;
import com.ptit.bookecommerce.model.Book;
import com.ptit.bookecommerce.utils.Constants;
import com.ptit.bookecommerce.utils.MyDialog;

public class SearchResultsActivity extends Activity {

	private TextView txtQuery;
	private GridView gvSearchRs;
	private MyDialog dialog;
	private RequestParams params;
	private String url;
	private List<Book> data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_results);

		// get the action bar
		ActionBar actionBar = getActionBar();

		// Enabling Back navigation on Action Bar icon
		actionBar.setDisplayHomeAsUpEnabled(true);

		// init components
		txtQuery = (TextView) findViewById(R.id.txtQuery);
		gvSearchRs = (GridView) findViewById(R.id.gv_search_result);
		dialog = new MyDialog();
		params = new RequestParams();
		url = Constants.URL_BOOK_SEARCH;
		data = new ArrayList<Book>();

		handleIntent(getIntent());
	}

	@Override
	protected void onNewIntent(Intent intent) {
		setIntent(intent);
		handleIntent(intent);
	}

	/**
	 * Handling intent data
	 */
	private void handleIntent(Intent intent) {
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			params.add("key_word", query);
			params.add("search_type", Constants.SEARCH_TYPE_TITLE);
			getData(params, url);
			txtQuery.setText("Search Query: " + query);
		}

	}

	private void getData(RequestParams params2, String url2) {
		// TODO Auto-generated method stub
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
				dialog.startDialog(SearchResultsActivity.this, "Loading books",
						"Loading ...");
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				dialog.endDialog();
			}

			@Override
			public void onFailure(int statusCode, Throwable error,
					String content) {
				// TODO Auto-generated method stub

				Log.e("OUTPUT SEARCH CONNECT", "Fail : " + statusCode);

			}

			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub

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
						data.add(b);
					}

					Log.d("Data size", "" + data.size());
					ListBookAdapter adapter = new ListBookAdapter(
							SearchResultsActivity.this, R.layout.item_book, data);
					gvSearchRs.setAdapter(adapter);
					gvSearchRs.setSelection(data.size() - 1);
					gvSearchRs
							.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> arg0,
										View arg1, int position, long arg3) {
									// TODO Auto-generated method stub
									transformActivity(data.get(position)
											.getId());
								}

							});

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	private void transformActivity(int book_id) {
		Intent intent = new Intent(this, BookDetailsActivity.class);
		intent.putExtra("book_id", String.valueOf(book_id));
		startActivity(intent);
	}
}
