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
import android.widget.TextView;
import android.widget.Toast;

import com.lagvna.helpers.DataHelper;
import com.lagvna.tasks.SignupForGiftTask;

public class GiftDetailsActivity extends Activity {
	private Button signup;
	private TextView name;
	private TextView link;
	private TextView shop;
	private TextView desc;

	private ProgressDialog progressDialog;

	private String nameTxt;
	private String linkTxt;
	private String shopTxt;
	private String descTxt;
	private String id;
	private String currentBuyer;
	private String buyer;
	private int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_gift_details);

		signup = (Button) findViewById(R.id.signupGift);
		name = (TextView) findViewById(R.id.giftName);
		link = (TextView) findViewById(R.id.giftLink);
		shop = (TextView) findViewById(R.id.giftShop);
		desc = (TextView) findViewById(R.id.giftDesc);

		buyer = DataHelper.getInstance().getAuthor();

		getExtras();
		
		if (DataHelper.getInstance().getIsOrganizer()) {
			signup.setEnabled(false);
		}

		if (currentBuyer.equals(buyer)) {
			signup.setText("Wypisz się!");
		}

		if (!currentBuyer.equals("brak") && !currentBuyer.equals(buyer)) {
			signup.setEnabled(false);
		}

		signup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				signup();
			}
		});

		name.setText(nameTxt);
		link.setText(linkTxt);
		shop.setText(shopTxt);
		desc.setText(descTxt);
	}

	private void signup() {
		if (currentBuyer.equals(buyer)) {
			new SignupForGiftTask(this, id, "brak").execute();
		} else {
			new SignupForGiftTask(this, id, buyer).execute();
		}

	}

	public void setSignupDetails(String buyer) {
		Intent returnIntent = new Intent();
		returnIntent.putExtra("buyer", buyer);
		returnIntent.putExtra("position", position);
		setResult(Activity.RESULT_OK, returnIntent);
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

	private void getExtras() {
		Bundle extras = getIntent().getExtras();
		id = extras.getString("id");
		nameTxt = extras.getString("name");
		linkTxt = extras.getString("link");
		shopTxt = extras.getString("shop");
		descTxt = extras.getString("description");
		position = extras.getInt("position");
		currentBuyer = extras.getString("currentbuyer");
	}
}
