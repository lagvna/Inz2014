package com.lagvna.perfectday;

import java.util.ArrayList;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lagvna.adapters.ListViewAdapter;
import com.lagvna.customtypes.Guest;
import com.lagvna.helpers.DataHelper;
import com.lagvna.lists.CustomRow;
import com.lagvna.tasks.GetAllGuestsTask;
import com.lagvna.tasks.RemoveGuestTask;

public class GuestsActivity extends ListActivity {
	private ArrayList<CustomRow> CustomRow_data;
	private ListViewAdapter adapter;
	private Button addGuest;
	private ArrayList<Guest> guestArr;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_guests);

		new GetAllGuestsTask(this, DataHelper.getInstance().getEventId())
				.execute();

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
				showPopup(GuestsActivity.this,
						guestArr.get(position).getName(), guestArr
								.get(position).getSurname(),
						guestArr.get(position).getEmail(),
						guestArr.get(position).getTelephone());
			}
		});

		addGuest = (Button) findViewById(R.id.addNewGuest);

		addGuest.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(GuestsActivity.this,
						AddGuestActivity.class);
				GuestsActivity.this.startActivityForResult(i, 1);
			}
		});

		if (!DataHelper.getInstance().getIsOrganizer()) {
			addGuest.setEnabled(false);
		}
	}

	public void createList(ArrayList<Guest> guestArr) {
		this.guestArr = guestArr;
		adapter.notifyDataSetChanged();
		CustomRow_data.clear();
		for (int i = 0; i < guestArr.size(); i++) {
			String name = guestArr.get(i).getName();
			String surname = guestArr.get(i).getSurname();
			String email = guestArr.get(i).getEmail();
			String telephone = guestArr.get(i).getTelephone();
			CustomRow_data.add(new CustomRow(R.drawable.lvsel, name + " "
					+ surname, email + " " + telephone));
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
			System.out.println("Jestem tutaj");
			Bundle extras = data.getExtras();
			String name = (String) extras.getString("name");
			String surname = (String) extras.getString("surname");
			String email = (String) extras.getString("email");
			String telephone = (String) extras.getString("telephone");
			String id = (String) extras.getString("id");

			guestArr.add(new Guest(name, surname, email, telephone, id));
			createList(guestArr);
		}
	}

	private void showPopup(final Activity context, String name, String surname,
			final String email, final String telephone) {
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);

		LinearLayout viewGroup = (LinearLayout) context
				.findViewById(R.id.popup);
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = layoutInflater.inflate(R.layout.popup_layout, viewGroup);

		final PopupWindow popup = new PopupWindow(context);
		popup.setContentView(layout);
		popup.setWidth(LayoutParams.WRAP_CONTENT);
		popup.setHeight(LayoutParams.WRAP_CONTENT);
		popup.setFocusable(true);

		((TextView) popup.getContentView().findViewById(R.id.namePop))
				.setText(name);
		((TextView) popup.getContentView().findViewById(R.id.surnamePop))
				.setText(surname);
		((Button) popup.getContentView().findViewById(R.id.emailPop))
				.setText(email);

		((Button) popup.getContentView().findViewById(R.id.emailPop))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
								Uri.fromParts("mailto", email, null));
						startActivity(Intent.createChooser(emailIntent,
								"Wyślij e-mail..."));
					}
				});
		((Button) popup.getContentView().findViewById(R.id.phonePop))
				.setText(telephone);
		((Button) popup.getContentView().findViewById(R.id.phonePop))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						try {
							Intent callIntent = new Intent(Intent.ACTION_CALL);
							callIntent.setData(Uri.parse("tel:+48" + telephone));
							startActivity(callIntent);
						} catch (ActivityNotFoundException e) {
							e.printStackTrace();
						}
					}
				});

		popup.showAtLocation(layout, Gravity.CENTER, 10, 10);
	}

	@SuppressWarnings("unused")
	@Override
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
		for (int i = 0; i < guestArr.size(); i++) {
			if ((guestArr.get(i).getName() + " " + guestArr.get(i).getSurname())
					.equals(d)) {
				int position = i;
				new RemoveGuestTask(this, "guest", guestArr.get(i).getId(),
						position).execute();
				break;
			}
		}
	}

	public void postDelete(int position) {
		guestArr.remove(position);
		adapter.notifyDataSetChanged();
		createList(guestArr);
	}

	public void showProgressDial() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Łączenie z serwerem");
		progressDialog.show();
	}

	public void hideProgressDial() {
		progressDialog.dismiss();
	}
}
