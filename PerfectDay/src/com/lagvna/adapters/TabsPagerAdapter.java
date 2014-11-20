package com.lagvna.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lagvna.fragments.GiftFragment;
import com.lagvna.fragments.InventoryFragment;
import com.lagvna.fragments.SummaryFragment;
import com.lagvna.fragments.WallFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			return new SummaryFragment();
		case 1:
			return new WallFragment();
		case 2:
			return new InventoryFragment();
		case 3:
			return new GiftFragment();
		}

		return null;
	}

	@Override
	public int getCount() {
		return 4;
	}

}
