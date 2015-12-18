package com.ptit.bookecommerce.activity.adapter;

import com.ptit.bookecommerce.activity.view.GenreFragment;
import com.ptit.bookecommerce.activity.view.ListBookFragment;
import com.ptit.bookecommerce.utils.Constants;

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
			bundle.putString("type", Constants.TAB_ALL);
			fragment.setArguments(bundle);
			break;
		case 1:
			fragment = new ListBookFragment();
			bundle.putString("type", Constants.TAB_NEW);
			fragment.setArguments(bundle);
			break;
		case 2:
			fragment = new ListBookFragment();
			bundle.putString("type", Constants.TAB_RECOMMEND);
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