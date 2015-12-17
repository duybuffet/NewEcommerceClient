package com.ptit.bookecommerce.activity.view;

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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.ptit.bookecommerce.R;
import com.ptit.bookecommerce.R.array;
import com.ptit.bookecommerce.R.drawable;
import com.ptit.bookecommerce.R.id;
import com.ptit.bookecommerce.R.layout;
import com.ptit.bookecommerce.R.menu;
import com.ptit.bookecommerce.R.string;
import com.ptit.bookecommerce.activity.adapter.DrawerItemCustomAdapter;
import com.ptit.bookecommerce.activity.adapter.ObjectDrawerItem;
import com.ptit.bookecommerce.model.Cart;
import com.ptit.bookecommerce.model.Customer;
import com.ptit.bookecommerce.utils.Constants;

@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity implements OnClickListener {

	private static final String TAG = MainActivity.class.getSimpleName();
	public static Cart cart = new Cart();

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private ObjectDrawerItem[] mDrawerItmes;

	// Display profile
	private RelativeLayout profileBox;
	private TextView tvUsername;

	public static int friendlyUrl = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTitle = mDrawerTitle = getTitle();

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		setUpProfile();
		setUpNavigation();

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

		handleRenderLayout(savedInstanceState);

	}

	private void setUpProfile() {
		// TODO Auto-generated method stub
		Customer customer = null;
		profileBox = (RelativeLayout) findViewById(R.id.profileBox);
		tvUsername = (TextView) findViewById(R.id.tv_userame);

		if ((customer = Customer.customerLogin) != null) {
			if (!customer.getFullName().equals("")) {
				tvUsername.setText(customer.getFullName());
			} else {
				tvUsername.setText("Click to edit name");
			}
		} else {
			tvUsername.setText("Not yet login");
		}

		profileBox.setOnClickListener(this);

	}

	private void setUpNavigation() {
		// TODO Auto-generated method stub
		// icon for listview item
		String[] loadedItems = getResources().getStringArray(
				R.array.drawer_titles);

		mDrawerItmes = new ObjectDrawerItem[5];

		mDrawerItmes[0] = new ObjectDrawerItem(R.drawable.ic_action_home,
				loadedItems[0]);
		mDrawerItmes[1] = new ObjectDrawerItem(R.drawable.ic_action_about,
				loadedItems[1]);
		mDrawerItmes[2] = new ObjectDrawerItem(R.drawable.ic_action_help,
				loadedItems[2]);
		mDrawerItmes[3] = new ObjectDrawerItem(R.drawable.ic_action_settings,
				loadedItems[3]);
		if (Customer.customerLogin == null) {
			mDrawerItmes[4] = new ObjectDrawerItem(R.drawable.ic_action_login,
					loadedItems[4]);
		} else {
			mDrawerItmes[4] = new ObjectDrawerItem(R.drawable.ic_action_logout,
					loadedItems[5]);
		}

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
	}

	private void handleRenderLayout(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// Set the default content area to item 0
		// when the app opens for the first time

		int fragment = Constants.FRAGMENT_HOME;
		Intent callerIntent = getIntent();
		Bundle extras = callerIntent.getExtras();
		if (extras != null) {
			if (extras.containsKey(Constants.MESSAGE)) {
				Bundle receivedData = callerIntent
						.getBundleExtra(Constants.MESSAGE);
				String res = receivedData.getString(Constants.MESSAGE_RES);
				if (receivedData.containsKey(Constants.MESSAGE_FRAGMENT)) {
					fragment = receivedData.getInt(Constants.MESSAGE_FRAGMENT);
				}

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
					
				case Constants.CHANGE_PROFILE_SUCCESS:
					showMessage(Constants.CHANGE_PROFILE_SUCCESS);
					break;

				default:
					break;
				}
			} else if (extras.containsKey(Constants.MESSAGE_CHANGE_VIEW)) {
				Bundle receivedData = callerIntent
						.getBundleExtra(Constants.MESSAGE_CHANGE_VIEW);
				fragment = receivedData.getInt(Constants.MESSAGE_CHANGE_VIEW);
			}

			if (extras.containsKey(Constants.MESSAGE_FRAGMENT)) {
				Bundle receivedFragment = callerIntent
						.getBundleExtra(Constants.MESSAGE_FRAGMENT);
				fragment = receivedFragment.getInt(Constants.MESSAGE_FRAGMENT);
				Log.e("fragment friendlyUrl", fragment + "");
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
				navigateTo(Constants.FRAGMENT_CART);
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
			if (position < 1) {
				navigateTo(position);
			} else if (position == 4) {
				if (Customer.customerLogin != null) {
					navigateTo(Constants.FRAGMENT_LOGOUT);
				} else {
					Log.e("Navigate to", "Login");
					navigateTo(Constants.FRAGMENT_LOGIN);
				}
			}
		}
	}

	private void navigateTo(int fragmentType) {
		switch (fragmentType) {
		case Constants.FRAGMENT_HOME:
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
		case Constants.FRAGMENT_PROFILE:
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.content_frame, ProfileFragment.newInstance(),
							ProfileFragment.TAG).commit();
			break;

		case Constants.FRAGMENT_CART:
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.content_frame, CartFragment.newInstance(),
							CartFragment.TAG).commit();
			break;

		case Constants.FRAGMENT_LOGIN:
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.content_frame, LoginFragment.newInstance(),
							LoginFragment.TAG).commit();
			break;

		case Constants.FRAGMENT_LOGOUT:
			Customer.customerLogin = null;
			setUpNavigation();
			setUpProfile();
			navigateTo(Constants.FRAGMENT_HOME);
			break;

		case Constants.FRAGMENT_CHECKOUT:
			Intent i = new Intent(this, CheckoutActivity.class);
			startActivity(i);
			break;
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == profileBox) {
			if (Customer.customerLogin != null) {
				navigateTo(Constants.FRAGMENT_PROFILE);
			}
		}
	}

}
