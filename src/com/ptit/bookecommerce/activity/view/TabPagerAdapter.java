package com.ptit.bookecommerce.activity.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabPagerAdapter extends FragmentPagerAdapter {

	public TabPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {
		Fragment fragment = null;
		Bundle bundle = new Bundle();
		switch (index) {
		case 0:
			fragment = new ListBookFragment();
			bundle.putString("type", "all");
			fragment.setArguments(bundle);
			break;
		case 1:
			fragment = new ListBookFragment();
			bundle.putString("type", "new");
			fragment.setArguments(bundle);
			break;
		case 2:
			fragment = new ListBookFragment();
			bundle.putString("type", "recommendation");
			fragment.setArguments(bundle);
			break;
		case 3:
			fragment = new GenreFragment();
			break;
		}
		return fragment;
	}

	@Override
	public int getCount() {
		return 4;
	}
}