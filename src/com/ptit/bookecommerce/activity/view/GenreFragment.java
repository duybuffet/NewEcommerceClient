package com.ptit.bookecommerce.activity.view;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.paulusworld.drawernavigationtabs.R;
import com.ptit.bookecommerce.activity.adapter.GenreArrayAdapter;
import com.ptit.bookecommerce.model.Genre;
import com.ptit.bookecommerce.utils.Constants;
import com.ptit.bookecommerce.utils.MyDialog;

public class GenreFragment extends Fragment {
	private List<Integer> listIds;
	private ListView lvGenre;
	private MyDialog dialog;
	private View view;
	private List<Genre> genres;
	
	public final static String TAG = GenreFragment.class.getSimpleName();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	public static GenreFragment newInstance() {
		return new GenreFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		/* init comps */
		view = inflater.inflate(R.layout.activity_genre, null);
		lvGenre = (ListView) view.findViewById(R.id.lvGenre);
		listIds = new ArrayList<Integer>();
		dialog = new MyDialog();

		/* load data */
		RequestParams params = new RequestParams();
		String url = Constants.URL_GENRE;
		getData(params, url);

		return view;
	}

	public void getData(RequestParams params, String url) {
		// Make RESTful webservice call using AsyncHttpClient object
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
				dialog.startDialog(view.getContext(), "Loading Genres",
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

				Log.d("OUTPUT CAT CONNECT", "Fail : " + statusCode);

			}

			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub

				try {
					// JSON Object
					// JSONObject obj = new JSONObject(response);
					// Log.d("JSON OBJ", obj.toString());
					JSONArray arr = new JSONArray(response);
					genres = new ArrayList<Genre>();
					for (int i = 0; i < arr.length(); i++) {
						JSONObject obj = arr.getJSONObject(i);
						Genre gen = new Genre();
						gen.setId(Integer.parseInt(obj.getString("id")));
						gen.setName(obj.getString("name"));
//						cat.setCover(obj.getString("cover"));
//						cat.setTotal(Integer.parseInt(obj.getString("total")));
						// testData.add(obj.getString("name") + " ("
						// + obj.getString("total") + ")");
						listIds.add(obj.getInt("id"));
						genres.add(gen);
					}

					GenreArrayAdapter adapter = new GenreArrayAdapter(
							view.getContext(), R.layout.genre_item_layout,
							genres);
					lvGenre.setAdapter(adapter);
					lvGenre
							.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> arg0,
										View arg1, int arg2, long arg3) {
									// TODO Auto-generated method stub
									Bundle bundle = new Bundle();
									bundle.putInt("genre_id",
											genres.get(arg2).getId());
									Intent intent = new Intent(view
											.getContext(),
											BooksByGenreActivity.class);
									intent.putExtra("genre", bundle);
									startActivity(intent);
								}

							});

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}

}
