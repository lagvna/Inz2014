package com.lagvna.perfectday;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lagvna.helpers.DataHelper;
import com.lagvna.tasks.AddNoteTask;

public class AddNoteActivity extends Activity {
	private EditText note;
	private Button add;
	private String eventId;
	private String noteTxt;

	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_add_note);

		note = (EditText) findViewById(R.id.noteText);
		add = (Button) findViewById(R.id.addNote);

		eventId = DataHelper.getInstance().getEventId();

		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!note.getText().toString().equals("")) {
					noteTxt = note.getText().toString();
					System.err.println(noteTxt);
					processInput();
					addNote();
				} else {
					raiseError("Musisz wpisać jakąś notatkę!");
				}
			}
		});
	}

	private void addNote() {
		new AddNoteTask(this, noteTxt, eventId).execute();
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

	public void setNoteDetails(String description, String isDone, String id) {
		Intent returnIntent = new Intent();
		returnIntent.putExtra("description", description);
		returnIntent.putExtra("isDone", isDone);
		returnIntent.putExtra("id", id);
		setResult(Activity.RESULT_OK, returnIntent);
	}

	private void processInput() {
		noteTxt = noteTxt.replace(" ", "%20");
	}
}
