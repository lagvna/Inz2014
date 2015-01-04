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
import com.lagvna.lists.Wall;
import com.lagvna.tasks.GetAllGiftsTask;
import com.lagvna.tasks.GetAllNotesTask;
import com.lagvna.tasks.GetWallTask;
import com.lagvna.tasks.RemoveGiftTask;
import com.lagvna.tasks.RemoveNoteTask;

@SuppressWarnings("deprecation")
public class EventActivity extends FragmentActivity implements
		ActionBar.TabListener {

	public ArrayList<Note> noteArr;
	public ArrayList<Gift> giftArr;
	public ArrayList<Wall> wallArr;
	private ViewPager viewPager;
	private ActionBar actionBar;
	public TabsPagerAdapter mAdapter;
	private String[] tabs = { "Podsumowanie", "Tablica", "Notatki", "Prezenty/Przedmioty" };
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

		wallArr = new ArrayList<Wall>();

		getExtras();

		getNotes();
		getGifts();
		// getWall();

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

	public void getWall() {
		new GetWallTask(this, DataHelper.getInstance().getEventId()).execute();
	}

	public void showProgressDial() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Łączenie z serwerem");
		progressDialog.show();
	}

	public void hideProgressDial() {
		progressDialog.dismiss();
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

	public void createWall(ArrayList<Wall> wallArr) {
		this.wallArr = wallArr;
		mAdapter.wf.createList();
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