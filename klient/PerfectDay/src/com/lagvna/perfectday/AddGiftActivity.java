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
import com.lagvna.tasks.AddGiftTask;

public class AddGiftActivity extends Activity {
	private EditText name;
	private EditText shop;
	private EditText link;
	private EditText description;

	private String nameTxt;
	private String shopTxt;
	private String linkTxt;
	private String descriptionTxt;
	private String eventId;

	private Button addGift;

	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_add_gift);

		name = (EditText) findViewById(R.id.giftName);
		shop = (EditText) findViewById(R.id.giftShop);
		link = (EditText) findViewById(R.id.giftLink);
		description = (EditText) findViewById(R.id.giftDesc);
		addGift = (Button) findViewById(R.id.addGiftD);

		eventId = DataHelper.getInstance().getEventId();

		addGift.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!name.getText().toString().equals("")) {
					nameTxt = name.getText().toString();
					shopTxt = shop.getText().toString();
					linkTxt = link.getText().toString();
					descriptionTxt = description.getText().toString();
					processInput();
					addGift();
				} else {
					raiseError("Musisz wpisać przynajmniej nazwę przedmiotu!");
				}
			}
		});

	}

	private void processInput() {
		nameTxt = nameTxt.replace(" ", "%20");
		shopTxt = shopTxt.replace(" ", "%20");
		linkTxt = linkTxt.replace(" ", "%20");
		descriptionTxt = descriptionTxt.replace(" ", "%20");
	}

	private void addGift() {
		new AddGiftTask(this, nameTxt, shopTxt, linkTxt, descriptionTxt,
				eventId).execute();
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

	public void setGiftDetails(String name, String link, String shop,
			String description, String buyer, String id) {
		Intent returnIntent = new Intent();
		returnIntent.putExtra("name", name);
		returnIntent.putExtra("link", link);
		returnIntent.putExtra("shop", shop);
		returnIntent.putExtra("description", description);
		returnIntent.putExtra("buyer", buyer);
		returnIntent.putExtra("id", id);
		setResult(Activity.RESULT_OK, returnIntent);
	}
}
