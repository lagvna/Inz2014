package com.lagvna.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lagvna.fragments.GuestGiftFragment;
import com.lagvna.fragments.GuestSummaryFragment;
import com.lagvna.fragments.GuestWallFragment;

public class GuestTabsPagerAdapter extends FragmentPagerAdapter {
	public GuestSummaryFragment sf;
	public GuestWallFragment wf;
	public GuestGiftFragment gf;

	public GuestTabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			sf = new GuestSummaryFragment();
			return sf;
		case 1:
			wf = new GuestWallFragment();
			return wf;
		case 2:
			gf = new GuestGiftFragment();
			return gf;
		}

		return null;
	}

	@Override
	public int getCount() {
		return 3;
	}

}
