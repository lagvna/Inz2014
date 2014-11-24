package com.lagvna.perfectday;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lagvna.helpers.DataHelper;
import com.lagvna.tasks.AddGuestTask;

public class AddGuestActivity extends Activity {
	private EditText name;
	private EditText surname;
	private EditText email;
	private EditText telephone;
	private String nameTxt;
	private String surnameTxt;
	private String emailTxt;
	private String telephoneTxt;
	private String eventId;
	private ProgressDialog progressDialog;
	private Button addGuest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_add_guest);

		name = (EditText) findViewById(R.id.guestName);
		surname = (EditText) findViewById(R.id.guestSurname);
		email = (EditText) findViewById(R.id.guestMail);
		telephone = (EditText) findViewById(R.id.guestTel);

		addGuest = (Button) findViewById(R.id.addGuestButton);

		addGuest.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				nameTxt = name.getText().toString();
				surnameTxt = surname.getText().toString();
				emailTxt = email.getText().toString();
				telephoneTxt = telephone.getText().toString();
				eventId = DataHelper.getInstance().getEventId();

				processInput();
				addGuest();
				cleanEditTexts();
			}
		});
	}

	private void processInput() {
		emailTxt.replace("@", ".at.");
		nameTxt.replace(" ", "");
		surnameTxt.replace(" ", "");
		emailTxt.replace(" ", "");
		telephoneTxt.replace(" ", "");
	}

	private void addGuest() {
		new AddGuestTask(this, nameTxt, surnameTxt, emailTxt, telephoneTxt,
				eventId).execute();
	}

	private void cleanEditTexts() {
		name.setText("");
		surname.setText("");
		email.setText("");
		telephone.setText("");
	}

	public void showProgressDial() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Łączenie z serwerem");
		progressDialog.show();
	}

	public void hideProgressDial() {
		progressDialog.hide();
	}

	public void setNewGuest(String name, String surname, String email,
			String phone, String eventid) {
		Toast.makeText(this, name + " " + surname + " " + email + " "
				+ telephone + " " + eventid, Toast.LENGTH_LONG);
	}
}
