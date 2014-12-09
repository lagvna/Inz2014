package com.lagvna.perfectday;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.lagvna.customtypes.Contact;

public class BrowseContactsActivity extends Activity {
	private ArrayList<Contact> contactArr;
	private TextView name;
	private TextView sector;
	private TextView telephone;
	private TextView email;
	private TextView note;
	private Button next;
	private Button addContact;

	private String nameTxt;
	private String sectorTxt;
	private String telephoneTxt;
	private String emailTxt;
	private String noteTxt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_browse_contacts);

		name = (TextView) findViewById(R.id.contactName);
		sector = (TextView) findViewById(R.id.contactSector);
		telephone = (TextView) findViewById(R.id.contactTelephone);
		email = (TextView) findViewById(R.id.contactEmail);
		note = (TextView) findViewById(R.id.contactNote);
		next = (Button) findViewById(R.id.button2);
		addContact = (Button) findViewById(R.id.button1);

		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		addContact.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(BrowseContactsActivity.this,
						AddContactActivity.class);
				BrowseContactsActivity.this.startActivityForResult(intent, 1);
				// TODO on result co ma sie dziac

			}
		});

		// task od pobierania
		// metody wrzucajace do listy
		// next
	}
}
