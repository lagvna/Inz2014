package com.lagvna.fragments;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.lagvna.customtypes.Note;
import com.lagvna.perfectday.AddNoteActivity;
import com.lagvna.perfectday.R;

public class NotesFragment extends Fragment {
	private ArrayList<Note> notesArr;

	private Button next;
	private Button prev;
	private Button addNote;
	private Button deleteNote;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_notes, container,
				false);

		next = (Button) rootView.findViewById(R.id.next);
		prev = (Button) rootView.findViewById(R.id.prev);
		addNote = (Button) rootView.findViewById(R.id.addNoteF);
		deleteNote = (Button) rootView.findViewById(R.id.delNoteF);

		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		prev.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

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
				// TODO Auto-generated method stub

			}
		});

		return rootView;
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

			//notesArr.add(new Note(id, isDone, description));
		}
	}
}
