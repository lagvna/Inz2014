package com.lagvna.perfectday;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.lagvna.adapters.ListViewAdapter;
import com.lagvna.lists.CustomRow;

public class GuestsActivity extends ListActivity {
	private ArrayList<CustomRow> CustomRow_data;
	private ListViewAdapter adapter;
	private Button addGuest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_guests);

		CustomRow_data = new ArrayList<CustomRow>();

		adapter = new ListViewAdapter(this, R.layout.element, CustomRow_data);

		setListAdapter(adapter);
		ListView lv = getListView();
		lv.setTextFilterEnabled(true);
		registerForContextMenu(getListView());

		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// popup z danymi
			}
		});

		addGuest = (Button) findViewById(R.id.addNewGuest);

		addGuest.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(GuestsActivity.this,
						AddGuestActivity.class);
				GuestsActivity.this.startActivity(i);
			}
		});
	}
}
