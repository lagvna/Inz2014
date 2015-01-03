package com.lagvna.perfectday;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Toast;

import com.lagvna.adapters.TabsPagerAdapter;
import com.lagvna.customtypes.Gift;
import com.lagvna.customtypes.Note;
import com.lagvna.helpers.DataHelper;
import com.lagvna.tasks.GetAllGiftsTask;
import com.lagvna.tasks.GetAllNotesTask;
import com.lagvna.tasks.RemoveGiftTask;
import com.lagvna.tasks.RemoveNoteTask;

@SuppressWarnings("deprecation")
public class EventActivity extends FragmentActivity implements
		ActionBar.TabListener {

	public ArrayList<Note> noteArr;
	public ArrayList<Gift> giftArr;
	private ViewPager viewPager;
	private ActionBar actionBar;
	public TabsPagerAdapter mAdapter;
	private String[] tabs = { "Podsumowanie", "Tablica", "Notatki", "Prezenty" };
	public String name;
	public String place;
	public String date;
	public String description;
	public String code;

	public int currNote = -1;

	private ProgressDialog progressDialog;

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

		getNotes();
		getGifts();

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

	public void delete(int d) {
		new RemoveNoteTask(this, "note", noteArr.get(d).getId(), d).execute();
	}

	public void deleteGift(int d) {
		new RemoveGiftTask(this, "gift", giftArr.get(d).getId(), d).execute();
	}

	private void getNotes() {
		new GetAllNotesTask(this, DataHelper.getInstance().getEventId())
				.execute();
	}

	private void getGifts() {
		new GetAllGiftsTask(this, DataHelper.getInstance().getEventId())
				.execute();
	}

	public void showProgressDial() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Łączenie z serwerem");
		progressDialog.show();
	}

	public void hideProgressDial() {
		progressDialog.hide();
	}

	public void raiseError(String error) {
		Toast.makeText(this, error, Toast.LENGTH_LONG).show();
	}

	public void createNoteList(ArrayList<Note> noteArr) {
		this.noteArr = noteArr;
		if (noteArr.size() > 0) {
			currNote = 0;
		}
	}

	public void createGiftList(ArrayList<Gift> giftArr) {
		this.giftArr = giftArr;
	}

	private void getExtras() {
		Bundle extras = getIntent().getExtras();
		name = extras.getString("name");
		name.replace("_", " ");
		place = extras.getString("place");
		place.replace("_", " ");
		date = extras.getString("date");
		date.replace("_", " ");
		code = extras.getString("code");
		code.replace("_", " ");
		description = extras.getString("description");
		description.replace("_", " ");
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		AdapterView.AdapterContextMenuInfo info;
		
		try {
			info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		} catch (ClassCastException e) {
			Log.e("perfectday", "bad menuInfo", e);
			return false;
		}

		int toDel = info.position;

		if (item.getTitle() == "Usuń") {
			deleteGift(toDel);
		}

		return false;
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
	}
}