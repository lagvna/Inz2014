package com.lagvna.perfectday;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreenActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_splash_screen);

		final Thread mainThread = new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				Intent intent = new Intent(SplashScreenActivity.this,
						LoginActivity.class);
				SplashScreenActivity.this.startActivity(intent);
				SplashScreenActivity.this.finish();
			}
		};

		mainThread.start();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}