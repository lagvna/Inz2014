package com.lagvna.perfectday;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lagvna.customtypes.Contact;
import com.lagvna.helpers.DataHelper;
import com.lagvna.tasks.GetAllContactsTask;
import com.lagvna.tasks.RemoveContactTask;

public class BrowseContactsActivity extends Activity {
	private ArrayList<Contact> contactArr;
	private TextView name;
	private TextView sector;
	private Button telephone;
	private Button email;
	private TextView note;
	private Button next;
	private Button addContact;
	private Button delete;

	private String nameTxt;
	private String sectorTxt;
	private String telephoneTxt;
	private String emailTxt;
	private String noteTxt;

	private ProgressDialog progressDialog;

	private int currContact = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_browse_contacts);

		name = (TextView) findViewById(R.id.contactName);
		sector = (TextView) findViewById(R.id.contactSector);
		telephone = (Button) findViewById(R.id.contactTelephone);
		email = (Button) findViewById(R.id.contactEmail);
		note = (TextView) findViewById(R.id.contactNote);
		next = (Button) findViewById(R.id.button2);
		addContact = (Button) findViewById(R.id.button1);
		delete = (Button) findViewById(R.id.delContact);

		new GetAllContactsTask(this, DataHelper.getInstance().getEventId())
				.execute();

		email.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri
						.fromParts("mailto", emailTxt, null));
				startActivity(Intent.createChooser(emailIntent,
						"Wyślij e-mail..."));
			}
		});

		telephone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					Intent callIntent = new Intent(Intent.ACTION_CALL);
					callIntent.setData(Uri.parse("tel:+48" + telephoneTxt));
					startActivity(callIntent);
				} catch (ActivityNotFoundException e) {
					e.printStackTrace();
				}
			}
		});

		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (currContact > -1) {
					if (currContact < contactArr.size() - 1) {
						currContact++;
						setCurrContact();
					} else {
						currContact = 0;
						setCurrContact();
					}
				}
			}
		});

		addContact.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(BrowseContactsActivity.this,
						AddContactActivity.class);
				BrowseContactsActivity.this.startActivityForResult(intent, 1);
			}
		});

		delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (currContact > -1) {
					int toDel = currContact;
					delete(toDel);
				}
			}
		});
	}

	public void delete(int d) {
		new RemoveContactTask(this, "contact", contactArr.get(d).getId(), d)
				.execute();
	}

	public void postDelete(int position) {
		contactArr.remove(position);
		currContact--;
		setCurrContact();
	}

	public void createList(ArrayList<Contact> contactArr) {
		this.contactArr = contactArr;

		if (contactArr.size() > 0) {
			currContact = 0;
			setCurrContact();
		}
	}

	private void setCurrContact() {
		if (currContact > -1) {
			Contact c = contactArr.get(currContact);

			nameTxt = c.getName();
			sectorTxt = c.getSector();
			emailTxt = c.getEmail();
			telephoneTxt = c.getTelephone();
			noteTxt = c.getNote();
		} else {
			nameTxt = "";
			sectorTxt = "";
			emailTxt = "";
			telephoneTxt = "";
			noteTxt = "";
		}

		emailTxt = emailTxt.replace(".at.", "@");
		nameTxt = nameTxt.replace("%20", " ");
		noteTxt = noteTxt.replace("%20", " ");

		name.setText(nameTxt);
		sector.setText(sectorTxt);
		email.setText(emailTxt);
		telephone.setText(telephoneTxt);
		note.setText(noteTxt);
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

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
			System.out.println("Jestem tutaj");
			Bundle extras = data.getExtras();
			String name = (String) extras.getString("name");
			String sector = (String) extras.getString("sector");
			String email = (String) extras.getString("email");
			String telephone = (String) extras.getString("telephone");
			String note = (String) extras.getString("note");
			String id = (String) extras.getString("id");

			System.out.println(name);

			contactArr
					.add(new Contact(id, name, sector, email, telephone, note));
			currContact++;
			setCurrContact();
		}
	}
}
