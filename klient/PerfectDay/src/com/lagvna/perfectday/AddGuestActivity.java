package com.lagvna.perfectday;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
				if (!name.getText().toString().equals("")) {
					nameTxt = name.getText().toString();
				} else {
					nameTxt = "brak";
				}
				if (!surname.getText().toString().equals("")) {
					surnameTxt = surname.getText().toString();
				} else {
					surnameTxt = "brak";
				}
				if (!email.getText().toString().equals("")) {
					emailTxt = email.getText().toString();
				} else {
					emailTxt = "brak";
				}
				if (!telephone.getText().toString().equals("")) {
					telephoneTxt = telephone.getText().toString();
				} else {
					telephoneTxt = "brak";
				}
				eventId = DataHelper.getInstance().getEventId();

				if (!nameTxt.equals("brak") && !surnameTxt.equals("brak")) {
					addGuest();
				} else {
					raiseError("Musisz podać przynajmniej imię i nazwisko gościa!");
				}

			}
		});
	}

	private void addGuest() {
		new AddGuestTask(this, nameTxt, surnameTxt, emailTxt, telephoneTxt,
				eventId).execute();
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

	public void setGuestDetails(String name, String surname, String email,
			String telephone, String id) {
		Intent returnIntent = new Intent();
		returnIntent.putExtra("name", name);
		returnIntent.putExtra("surname", surname);
		returnIntent.putExtra("email", email);
		returnIntent.putExtra("telephone", telephone);
		returnIntent.putExtra("id", id);
		setResult(Activity.RESULT_OK, returnIntent);
		System.out.println("JESTEM TUTAJ!");
	}
}
