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
import com.lagvna.tasks.AddResponseTask;
import com.lagvna.tasks.AddWallTask;

public class AddResponseActivity extends Activity {
	private EditText response;
	private Button add;
	private ProgressDialog progressDialog;

	private String eventId;
	private String id;
	private String responseTxt;
	private String authorTxt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_add_response);

		response = (EditText) findViewById(R.id.responseText);
		add = (Button) findViewById(R.id.addResponse);

		getExtras();

		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!response.getText().toString().equals("")) {
					responseTxt = response.getText().toString();
					authorTxt = DataHelper.getInstance().getAuthor();
					eventId = DataHelper.getInstance().getEventId();
					if (id.equals("new")) {
						addWall();
					} else {
						addResponse();
					}
				} else {
					raiseError("Musisz dodać jakąś odpowiedź!");
				}
			}
		});
	}

	private void addWall() {
		new AddWallTask(this, responseTxt, authorTxt, eventId).execute();
	}

	private void addResponse() {
		new AddResponseTask(this, responseTxt, authorTxt, id, eventId)
				.execute();
	}

	private void getExtras() {
		Bundle extras = getIntent().getExtras();
		id = extras.getString("id");
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

	public void setWallDetails(String note, String author, String date,
			String id) {
		Intent returnIntent = new Intent();
		returnIntent.putExtra("note", note);
		returnIntent.putExtra("author", author);
		returnIntent.putExtra("date", date);
		returnIntent.putExtra("wallid", "null");
		returnIntent.putExtra("id", id);
		setResult(Activity.RESULT_OK, returnIntent);
	}

	public void setResponseDetails(String note, String author, String date,
			String wallId, String id) {
		Intent returnIntent = new Intent();
		returnIntent.putExtra("note", note);
		returnIntent.putExtra("author", author);
		returnIntent.putExtra("date", date);
		returnIntent.putExtra("wallid", wallId);
		returnIntent.putExtra("id", id);
		setResult(Activity.RESULT_OK, returnIntent);
	}
}
