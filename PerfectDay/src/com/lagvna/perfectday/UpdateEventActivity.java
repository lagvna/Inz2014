package com.lagvna.perfectday;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lagvna.helpers.DatePickerFragment;
import com.lagvna.tasks.AddEventTask;
import com.lagvna.tasks.UpdateEventTask;

public class UpdateEventActivity extends FragmentActivity {
	private ProgressDialog progressDialog;
	private int isFormal;
	private String eventName;
	private String eventDate;
	private String eventPlace;
	private String eventDescription;
	private Button confirmButton;
	private EditText nameTxt;
	private Button dateTxt;
	private EditText placeTxt;
	private EditText descriptionTxt;
	private Boolean isNew;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_update_event);

		getExtras();

		confirmButton = (Button) findViewById(R.id.confirmButton);
		dateTxt = (Button) findViewById(R.id.dateButton);
		nameTxt = (EditText) findViewById(R.id.eventName);
		placeTxt = (EditText) findViewById(R.id.eventPlace);
		descriptionTxt = (EditText) findViewById(R.id.eventDesc);

		confirmButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				confirmData();
			}
		});

		dateTxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DialogFragment newFragment = new DatePickerFragment();
				newFragment.show(getSupportFragmentManager(), "datePicker");
			}
		});
	}

	private void confirmData() {
		eventName = nameTxt.getText().toString();
		eventDate = (String) dateTxt.getText();
		eventPlace = placeTxt.getText().toString();
		eventDescription = descriptionTxt.getText().toString();

		if (eventName.equals("") || eventDate.equals("")
				|| eventPlace.equals("") || eventDescription.equals("")) {
			Toast.makeText(this, "Wprowadź wszystkie dane!", Toast.LENGTH_LONG)
					.show();
		} else {
			if (isNew) {
				new AddEventTask(this, eventName, eventDate, eventPlace,
						eventDescription, isFormal).execute();
			} else {
				new UpdateEventTask(this, eventName, eventDate, eventPlace,
						eventDescription, isFormal).execute();

			}
		}
	}

	public void setEventDetails(String name, String place, String date,
			String description, String code) {
		Intent returnIntent = new Intent();
		returnIntent.putExtra("name", name);
		returnIntent.putExtra("place", place);
		returnIntent.putExtra("date", date);
		returnIntent.putExtra("description", description);
		returnIntent.putExtra("code", code);
		setResult(Activity.RESULT_OK, returnIntent);
	}

	private void getExtras() {
		Bundle extras = getIntent().getExtras();
		isFormal = extras.getInt("eventType");
		isNew = extras.getBoolean("isNew");
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
