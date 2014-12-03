package com.lagvna.perfectday;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class CompanyActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_company);
	}

	public void browseAdverts(View v) {
		Intent i = new Intent(CompanyActivity.this, BrowseAdvertsActivity.class);
		CompanyActivity.this.startActivity(i);
	}

	public void addAdvert(View v) {
		Intent i = new Intent(CompanyActivity.this, AddAdvertActivity.class);
		CompanyActivity.this.startActivity(i);
	}
}
