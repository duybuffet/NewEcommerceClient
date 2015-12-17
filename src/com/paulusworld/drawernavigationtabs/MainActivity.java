package com.paulusworld.drawernavigationtabs;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.ptit.bookecommerce.activity.adapter.DrawerItemCustomAdapter;
import com.ptit.bookecommerce.activity.adapter.ObjectDrawerItem;
import com.ptit.bookecommerce.activity.view.CartFragment;
import com.ptit.bookecommerce.activity.view.ProfileFrament;
import com.ptit.bookecommerce.model.Cart;
import com.ptit.bookecommerce.utils.Constants;

@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity {

	private static final String TAG = MainActivity.class.getSimpleName();
	public static Cart cart = new Cart();

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private ObjectDrawerItem[] mDrawerItmes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTitle = mDrawerTitle = getTitle();

		String[] loadedItems = getResources().getStringArray(
				R.array.drawer_titles);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// icon for listview item
		mDrawerItmes = new ObjectDrawerItem[4];

		mDrawerItmes[0] = new ObjectDrawerItem(R.drawable.ic_action_home,
				loadedItems[0]);
		mDrawerItmes[1] = new ObjectDrawerItem(R.drawable.ic_action_person,
				loadedItems[1]);
		mDrawerItmes[2] = new ObjectDrawerItem(R.drawable.ic_action_about,
				loadedItems[2]);
		mDrawerItmes[3] = new ObjectDrawerItem(R.drawable.ic_action_settings,
				loadedItems[3]);

		DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this,
				R.layout.listview_item_row, mDrawerItmes);

		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		// Add items to the ListView
		// mDrawerList.setAdapter(new ArrayAdapter<String>(this,
		// R.layout.drawer_list_item, mDrawerItmes));

		mDrawerList.setAdapter(adapter);

		// Set the OnItemClickListener so something happens when a
		// user clicks on an item.
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// Enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		// Set the default content area to item 0
		// when the app opens for the first time

		int fragment = 0;
		Intent callerIntent = getIntent();
		Bundle extras = callerIntent.getExtras();
		if (extras != null) {
			if (extras.containsKey("message")) {
				Bundle receivedData = callerIntent.getBundleExtra("message");
				String res = receivedData.getString("res");

				switch (res) {
				case Constants.LOGIN_SUCCESS:
					showMessage("Login successfully!");
					break;

				case Constants.REGISTER_SUCCESS:
					showMessage("Register successfully!");
					break;

				case Constants.CHECKOUT_SUCCESS:
					showMessage("Place order successfully! We will contact you soon!");
					break;

				default:
					break;
				}
			}

			if (extras.containsKey("fragment")) {
				Bundle receivedFragment = callerIntent
						.getBundleExtra("fragment");
				fragment = receivedFragment.getInt("fragment");
			}
		}

		if (savedInstanceState == null) {
			navigateTo(fragment);
		}

	}

	private void showMessage(String string) {
		// TODO Auto-generated method stub
		Toast.makeText(this, string, Toast.LENGTH_LONG).show();
	}

	/*
	 * If you do not have any menus, you still need this function in order to
	 * open or close the NavigationDrawer when the user clicking the ActionBar
	 * app icon.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		} else {
			switch (item.getItemId()) {
			case R.id.action_search:
				// search action
				return true;
			case R.id.action_cart:
				// cart click
				navigateTo(2);
				return true;
			}
		}
		return super.onOptionsItemSelected(item);
	}

	/*
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main_actions, menu);

		// Associate searchable configuration with the SearchView
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
				.getActionView();
		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(getComponentName()));

		return super.onCreateOptionsMenu(menu);
	}

	private class DrawerItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (position < 2) {
				navigateTo(position);
			}
		}
	}

	private void navigateTo(int position) {
		Log.v(TAG, "List View Item: " + position);

		switch (position) {
		case 0:
			/*
			 * getSupportFragmentManager() .beginTransaction()
			 * .add(R.id.content_frame, ItemOne.newInstance(),
			 * ItemOne.TAG).commit();
			 */
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.content_frame, TabbedActivity.newInstance(),
							TabbedActivity.TAG).commit();
			break;
		case 1:
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.content_frame, ProfileFrament.newInstance(),
							ProfileFrament.TAG).commit();
			break;

		case 2:
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.content_frame, CartFragment.newInstance(),
							CartFragment.TAG).commit();
			break;
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	
}
