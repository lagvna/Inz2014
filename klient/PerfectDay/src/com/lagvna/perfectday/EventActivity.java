package com.lagvna.perfectday;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.lagvna.adapters.TabsPagerAdapter;

@SuppressWarnings("deprecation")
public class EventActivity extends FragmentActivity implements
		ActionBar.TabListener {

	private ViewPager viewPager;
	private ActionBar actionBar;
	public TabsPagerAdapter mAdapter;
	private String[] tabs = { "Podsumowanie", "Tablica", "Notatki", "Prezenty" };
	public String name;
	public String place;
	public String date;
	public String description;
	public String code;

	// private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event);

		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayUseLogoEnabled(false);

		getExtras();
		//System.out.println(name);

		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}

		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	private void getExtras() {
		Bundle extras = getIntent().getExtras();
		name = extras.getString("name");
		place = extras.getString("place");
		date = extras.getString("date");
		code = extras.getString("code");
		description = extras.getString("description");
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onTabReselected(Tab arg0, android.app.FragmentTransaction arg1) {

	}

	@Override
	public void onTabSelected(Tab arg0, android.app.FragmentTransaction arg1) {
		System.out.println(arg0.getText());
		viewPager.setCurrentItem(arg0.getPosition());
	}

	@Override
	public void onTabUnselected(Tab arg0, android.app.FragmentTransaction arg1) {

	}

	public void onBackPressed() {
		EventActivity.this.finish();
		// overridePendingTransition(R.anim.in_left, R.anim.out_right);
	}
}