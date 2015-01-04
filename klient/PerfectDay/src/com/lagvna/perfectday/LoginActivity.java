package com.lagvna.perfectday;

import java.util.Arrays;
import java.util.List;

import org.apache.http.Header;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AppEventsLogger;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.UserInfoChangedCallback;
import com.lagvna.helpers.DataHelper;
import com.lagvna.tasks.GuestLoginTask;
import com.lagvna.tasks.LoginTask;

public class LoginActivity extends FragmentActivity {

	private LoginButton loginBtn;
	private Button organizerBtn;
	private Button guestBtn;
	private int eventType;
	private String code;
	private AlertDialog.Builder codeDialog;
	private ProgressDialog progressDialog;
	private LoginActivity la;
	private TextView userName;
	private UiLifecycleHelper uiHelper;
	private static final List<String> PERMISSIONS = Arrays
			.asList("publish_actions");

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		uiHelper = new UiLifecycleHelper(this, statusCallback);
		uiHelper.onCreate(savedInstanceState);

		DataHelper.getInstance().setServerUrl(
				"http://192.168.1.101:8000/pdapp/");

		la = this;

		setContentView(R.layout.activity_login);

		userName = (TextView) findViewById(R.id.user_name);
		loginBtn = (LoginButton) findViewById(R.id.fb_login_button);
		loginBtn.setUserInfoChangedCallback(new UserInfoChangedCallback() {
			@Override
			public void onUserInfoFetched(GraphUser user) {
				if (user != null) {
					userName.setText("Witaj, " + user.getName()
							+ ". Kim jesteś?");
					DataHelper.getInstance().setAuthor(user.getName());
				} else {
					userName.setText("Nie jesteś zalogowany.");
				}
			}
		});

		guestBtn = (Button) findViewById(R.id.guest);
		guestBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				buildCodeDialog();
			}
		});

		organizerBtn = (Button) findViewById(R.id.organizer);
		organizerBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this,
						SelectEventActivity.class);
				LoginActivity.this.startActivity(intent);
			}
		});

		buttonsEnabled(false);
	}

	private void buildCodeDialog() {
		codeDialog = new AlertDialog.Builder(this);
		codeDialog.setIcon(R.drawable.ic_launcher);
		codeDialog.setTitle("Wprowadź kod wydarzenia");
		codeDialog.setMessage("Kod");

		final EditText input = new EditText(this);
		input.setText("6n8hx");
		codeDialog.setView(input);
		codeDialog.setPositiveButton("Ok",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						if (!input.getText().toString().equals("")) {
							setCode(input.getText().toString());

							new GuestLoginTask(la, getCode()).execute();

						} else {
							raiseError("Musisz wprowadzić kod wydarzenia!");
						}
					}
				});

		codeDialog.setNegativeButton("Anuluj",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						setCode("");
					}
				});
		codeDialog.show();
	}

	public void setEventDetails(String name, String place, String date,
			String note, String code, String organizer, String id) {
		Intent intent = new Intent(LoginActivity.this, GuestEventActivity.class);
		DataHelper.getInstance().setEventId(id);

		intent.putExtra("name", name);
		intent.putExtra("place", place);
		intent.putExtra("date", date);
		intent.putExtra("note", note);
		intent.putExtra("code", code);
		intent.putExtra("organizer", organizer);

		LoginActivity.this.startActivity(intent);

	}

	public void raiseError(String error) {
		Toast.makeText(this, error, Toast.LENGTH_LONG).show();
	}

	public void showProgressDial() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Łączenie z serwerem");
		progressDialog.show();
	}

	public void hideProgressDial() {
		progressDialog.dismiss();
	}

	public void getCookies(Header[] headers) {
		String[] tmp = new String[3];
		int i = 0;
		try {
			for (Header h : headers) {
				if (h.getName().trim().equals("Set-Cookie")) {
					tmp[i] = h.getValue().split(";")[0].trim();
					System.err.println(tmp[i]);
					i++;
				}
			}
			DataHelper.getInstance().setCookies(tmp);
			System.out.println("SESJA: "
					+ DataHelper.getInstance().getSession());
		} catch (Exception ex) {
			Toast.makeText(this,
					"Coś poszło nie tak! Sprawdź połączenie internetowe",
					Toast.LENGTH_LONG).show();
			buttonsEnabled(false);

		}
	}

	private Session.StatusCallback statusCallback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			if (state.isOpened()) {
				buttonsEnabled(true);
				Log.d("LoginActivity", "Facebook session opened");

				String accessToken = session.getAccessToken();
				DataHelper.getInstance().setAccessToken(accessToken);
				String url = DataHelper.getInstance().getServerUrl()
						+ "login?access_token=" + accessToken;
				new LoginTask(url, la).execute();

			} else if (state.isClosed()) {
				buttonsEnabled(false);
				Log.d("LoginActivity", "Facebook session closed");
			}
		}
	};

	public void buttonsEnabled(boolean isEnabled) {
		guestBtn.setEnabled(isEnabled);
		organizerBtn.setEnabled(isEnabled);
	}

	public boolean checkPermissions() {
		Session s = Session.getActiveSession();
		if (s != null) {
			return s.getPermissions().contains("publish_actions");
		} else
			return false;
	}

	public void requestPermissions() {
		Session s = Session.getActiveSession();
		if (s != null)
			s.requestNewPublishPermissions(new Session.NewPermissionsRequest(
					this, PERMISSIONS));
	}

	@Override
	public void onResume() {
		super.onResume();
		uiHelper.onResume();
		buttonsEnabled(Session.getActiveSession().isOpened());
		AppEventsLogger.activateApp(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
		AppEventsLogger.deactivateApp(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onSaveInstanceState(Bundle savedState) {
		super.onSaveInstanceState(savedState);
		uiHelper.onSaveInstanceState(savedState);
	}

	public int getEventType() {
		return eventType;
	}

	public void setEventType(int eventType) {
		this.eventType = eventType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void onBackPressed() {
		LoginActivity.this.finish();
	}
}