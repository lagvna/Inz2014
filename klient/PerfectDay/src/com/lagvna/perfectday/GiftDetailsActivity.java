package com.lagvna.perfectday;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.lagvna.helpers.DataHelper;

public class GiftDetailsActivity extends Activity {
	private Button signup;
	private TextView name;
	private TextView link;
	private TextView shop;
	private TextView desc;

	private String nameTxt;
	private String linkTxt;
	private String shopTxt;
	private String descTxt;

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

		if (DataHelper.getInstance().getIsOrganizer()) {
			signup.setEnabled(false);
		}

		signup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO zapisy dla goscia
			}
		});

		getExtras();

		name.setText(nameTxt);
		link.setText(linkTxt);
		shop.setText(shopTxt);
		desc.setText(descTxt);
	}

	private void getExtras() {
		Bundle extras = getIntent().getExtras();
		nameTxt = extras.getString("name");
		linkTxt = extras.getString("link");
		shopTxt = extras.getString("shop");
		descTxt = extras.getString("description");
	}
}
