package com.lagvna.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lagvna.customtypes.Note;
import com.lagvna.perfectday.AddNoteActivity;
import com.lagvna.perfectday.EventActivity;
import com.lagvna.perfectday.R;

public class NotesFragment extends Fragment {
	private EventActivity ea;

	private Button next;
	private Button prev;
	private Button addNote;
	private Button deleteNote;
	private TextView noteArea;

	public int currNote;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_notes, container,
				false);

		ea = (EventActivity) getActivity();

		next = (Button) rootView.findViewById(R.id.next);
		prev = (Button) rootView.findViewById(R.id.prev);
		addNote = (Button) rootView.findViewById(R.id.addNoteF);
		deleteNote = (Button) rootView.findViewById(R.id.delNoteF);
		noteArea = (TextView) rootView.findViewById(R.id.noteArea);

		currNote = ea.currNote;

		if (currNote > -1) {
			setCurrNote();
		}

		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (currNote > -1) {
					if (currNote < ea.noteArr.size() - 1) {
						currNote++;
						setCurrNote();
					}
				}
			}
		});

		prev.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (currNote > -1) {
					if (currNote > 0) {
						currNote--;
						setCurrNote();
					}
				}
			}
		});

		addNote.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), AddNoteActivity.class);
				startActivityForResult(i, 1);
			}
		});

		deleteNote.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (currNote > -1) {
					int toDel = currNote;
					ea.delete(toDel);
				}
			}
		});

		return rootView;
	}

	public void postDelete(int position) {
		ea.noteArr.remove(position);
		currNote = ea.noteArr.size() - 1;
		setCurrNote();
	}

	public void setCurrNote() {
		if (currNote > -1) {
			noteArea.setText(ea.noteArr.get(currNote).getDescription());
		} else {
			noteArea.setText("");
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		getActivity();
		if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
			Bundle extras = data.getExtras();
			String description = (String) extras.getString("description");
			String isDone = (String) extras.getString("isDone");
			String id = (String) extras.getString("id");

			ea.noteArr.add(new Note(id, isDone, description));
			currNote = ea.noteArr.size() - 1;
			setCurrNote();
		}
	}
}
