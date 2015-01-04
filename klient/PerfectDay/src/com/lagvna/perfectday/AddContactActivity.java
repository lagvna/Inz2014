package com.lagvna.perfectday;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.lagvna.helpers.DataHelper;
import com.lagvna.tasks.AddContactTask;

public class AddContactActivity extends Activity {
	private EditText name;
	private EditText telephone;
	private EditText email;
	private EditText note;
	private Spinner sector;

	private String nameTxt;
	private String sectorTxt;
	private String telephoneTxt;
	private String emailTxt;
	private String noteTxt;
	private String eventId;

	private Button addContact;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_add_contact);

		name = (EditText) findViewById(R.id.contactName);
		sector = (Spinner) findViewById(R.id.tradeSpinner);
		telephone = (EditText) findViewById(R.id.contactTelephone);
		email = (EditText) findViewById(R.id.contactEmail);
		note = (EditText) findViewById(R.id.contactNote);
		addContact = (Button) findViewById(R.id.button3);

		addItemsOnSectorSpinner();

		sector.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				sectorTxt = sector.getSelectedItem().toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		addContact.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!name.getText().toString().equals("")) {
					nameTxt = name.getText().toString();
				} else {
					nameTxt = "brak";
				}
				if (!note.getText().toString().equals("")) {
					noteTxt = note.getText().toString();
				} else {
					noteTxt = "brak";
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

				if (!nameTxt.equals("brak")) {
					addContact();
				} else {
					raiseError("Musisz podać przynajmniej nazwę kontaktu!");
				}
			}
		});
	}

	private void addItemsOnSectorSpinner() {

		List<String> list = new ArrayList<String>();
		list.add("Kwiaty");
		list.add("Orkiestra");
		list.add("Catering");
		list.add("Sala");
		list.add("Alkohol");
		list.add("Inne");

		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_dropdown_item, list);
		sector.setAdapter(spinnerArrayAdapter);

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

	public void setContactDetails(String name, String sector, String email,
			String telephone, String note, String id) {
		Intent returnIntent = new Intent();
		returnIntent.putExtra("name", name);
		returnIntent.putExtra("sector", sector);
		returnIntent.putExtra("email", email);
		returnIntent.putExtra("telephone", telephone);
		returnIntent.putExtra("note", note);
		returnIntent.putExtra("id", id);
		setResult(Activity.RESULT_OK, returnIntent);
		System.out.println("JESTEM TUTAJ!");
	}

	private void addContact() {
		new AddContactTask(this, nameTxt, sectorTxt, emailTxt, telephoneTxt,
				noteTxt, eventId).execute();
	}
}
