package com.ptit.bookecommerce.activity.view;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ptit.bookecommerce.R;
import com.ptit.bookecommerce.R.id;
import com.ptit.bookecommerce.R.layout;
import com.ptit.bookecommerce.R.menu;
import com.ptit.bookecommerce.activity.adapter.ListBookAdapter;
import com.ptit.bookecommerce.model.Book;
import com.ptit.bookecommerce.utils.Constants;
import com.ptit.bookecommerce.utils.MyDialog;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

public class BooksByGenreActivity extends Activity implements OnClickListener {

	private List<Book> data;
	private GridView gvBookByGenre;
	private MyDialog dialog;
	private Button btnLoad;
	private int curentPage;
	private RequestParams params;
	private int genreId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_books_by_genre);
		
		// get action bar   
        ActionBar actionBar = getActionBar();
 
        // Enabling Up / Back navigation
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("List Book In Genre");
		
		// init comps
		data = new ArrayList<Book>();
		gvBookByGenre = (GridView) findViewById(R.id.gvBooksByGenre);
		dialog = new MyDialog();
		curentPage = 1;
		// get data from genre activity
		Intent callerIntent = getIntent();
		Bundle receivedData = callerIntent.getBundleExtra("genre");
		genreId = receivedData.getInt("genre_id");
		Log.d("Genre ID", genreId + "");

		btnLoad = (Button) findViewById(R.id.btnLoadBookByGenre);
		btnLoad.setOnClickListener(this);

		// load books by given category_id
		params = new RequestParams();
		String url = Constants.URL_BOOKS_BY_GENRE;
		params.put("genre_id", genreId + "");
		params.put("page", curentPage + "");
		Log.d("URL", url);
		Log.d("URL", params.toString());
		getData(params, url);
	}

	public void getData(RequestParams params, String url) {
		// Make RESTful webservice call using AsyncHttpClient object
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
				dialog.startDialog(BooksByGenreActivity.this, "Loading books",
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

				Log.d("OUTPUT BOOKGENRE CONNECT", "Fail : " + statusCode);

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
							BooksByGenreActivity.this, R.layout.item_book, data);
					gvBookByGenre.setAdapter(adapter);
					gvBookByGenre.setSelection(data.size() - 1);
					gvBookByGenre
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.books_by_genre, menu);
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
		curentPage += 1;
		params.put("genre_id", genreId + "");

		params.put("page", curentPage + "");
		getData(params, Constants.URL_BOOKS_BY_GENRE);
	}
}
