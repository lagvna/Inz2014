package com.lagvna.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lagvna.fragments.GiftFragment;
import com.lagvna.fragments.NotesFragment;
import com.lagvna.fragments.SummaryFragment;
import com.lagvna.fragments.WallFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {
	public SummaryFragment sf;
	public WallFragment wf;
	public NotesFragment nf;
	public GiftFragment gf;
	
	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			sf = new SummaryFragment();
			return sf;
		case 1:
			wf = new WallFragment();
			return wf;
		case 2:
			nf = new NotesFragment();
			return nf;
		case 3:
			gf = new GiftFragment();
			return gf;
		}

		return null;
	}

	@Override
	public int getCount() {
		return 4;
	}

}
