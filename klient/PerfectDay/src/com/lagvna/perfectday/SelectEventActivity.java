package com.lagvna.perfectday;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.lagvna.adapters.ListViewAdapter;
import com.lagvna.customtypes.Event;
import com.lagvna.helpers.DataHelper;
import com.lagvna.lists.CustomRow;
import com.lagvna.tasks.GetAllEventsTask;
import com.lagvna.tasks.RemoveEventTask;

public class SelectEventActivity extends ListActivity {
	private Button addEvent;
	private AlertDialog.Builder eventTypeDialog;
	private int eventType;
	private ArrayList<CustomRow> CustomRow_data;
	public ArrayList<Event> eventArr;
	private ListViewAdapter adapter;
	private String name;
	private String place;
	private String date;
	private String description;
	private String code;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_select_event);

		new GetAllEventsTask(this).execute();

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
				Intent i = new Intent(SelectEventActivity.this,
						EventActivity.class);
				i.putExtra("name", eventArr.get(position).getName());
				i.putExtra("place", eventArr.get(position).getPlace());
				i.putExtra("date", eventArr.get(position).getDate());
				i.putExtra("code", eventArr.get(position).getCode());
				i.putExtra("description", eventArr.get(position).getNote());

				DataHelper.getInstance().setEventId(
						eventArr.get(position).getEventId());
				SelectEventActivity.this.startActivity(i);
			}
		});

		addEvent = (Button) findViewById(R.id.addNewEvent);

		addEvent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				eventTypeDialog.show();
			}
		});

		eventTypeDialog = new AlertDialog.Builder(this);
		eventTypeDialog.setIcon(R.drawable.ic_launcher);
		eventTypeDialog.setTitle("Typ imprezy");
		eventTypeDialog.setMessage("Jakie wydarzenie organizujesz?");
		eventTypeDialog.setPositiveButton("Formalne",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						setEventType(1);

						Intent intent = new Intent(SelectEventActivity.this,
								UpdateEventActivity.class);
						intent.putExtra("eventType", eventType);
						intent.putExtra("isNew", true);
						SelectEventActivity.this.startActivityForResult(intent,
								1);
					}
				});
		eventTypeDialog.setNegativeButton("Nieformalne",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						Intent intent = new Intent(SelectEventActivity.this,
								UpdateEventActivity.class);
						intent.putExtra("eventType", eventType);
						intent.putExtra("isNew", true);
						SelectEventActivity.this.startActivityForResult(intent,
								1);
					}
				});

	}

	public void createList(ArrayList<Event> eventArr) {
		this.eventArr = eventArr;
		adapter.notifyDataSetChanged();
		CustomRow_data.clear();
		for (int i = 0; i < eventArr.size(); i++) {
			String date = eventArr.get(i).getDate();
			date.replace("_", " ");
			String title = eventArr.get(i).getName();
			title.replace("_", " ");
			CustomRow_data.add(new CustomRow(R.drawable.lvsel, title, date));
		}
	}

	@Override
	/**
	 * Metoda tworzaca menu, pozwalajace po dluzszym kliknieciu w rekord
	 * na usuniecie nagrania.
	 */
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		AdapterView.AdapterContextMenuInfo info;
		try {
			info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		} catch (ClassCastException e) {
			Log.e("perfectday", "bad menuInfo", e);
			return;
		}

		menu.setHeaderTitle("Menu");
		menu.add(0, v.getId(), 0, "Usuń");
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

		String toDel = CustomRow_data.get(info.position).title;

		if (item.getTitle() == "Usuń") {
			delete(toDel);
		}

		return false;
	}

	public void delete(String d) {
		for (int i = 0; i < eventArr.size(); i++) {
			if (eventArr.get(i).getName().equals(d)) {
				int position = i;
				new RemoveEventTask(this, "event",
						eventArr.get(i).getEventId(), position).execute();
				break;
			}
		}
	}

	public void postDelete(int position) {
		eventArr.remove(position);
		adapter.notifyDataSetChanged();
		createList(eventArr);
	}

	public int getEventType() {
		return eventType;
	}

	public void setEventType(int eventType) {
		this.eventType = eventType;
	}

	public void showToast(String result) {
		Toast.makeText(this, Html.fromHtml(result), Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
			System.out.println("Jestem tutaj");
			Bundle extras = data.getExtras();
			name = (String) extras.getString("name");
			place = (String) extras.getString("place");
			date = (String) extras.getString("date");
			description = (String) extras.getString("description");
			code = (String) extras.getString("code");

			System.out.println(name);

			Intent intent = new Intent(SelectEventActivity.this,
					EventActivity.class);
			intent.putExtra("name", name);
			intent.putExtra("place", place);
			intent.putExtra("date", date);
			intent.putExtra("description", description);
			intent.putExtra("code", code);
			SelectEventActivity.this.startActivity(intent);
		}
	}

	public void showProgressDial() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Łączenie z serwerem");
		progressDialog.show();
	}

	public void hideProgressDial() {
		progressDialog.hide();
	}
}
