package com.lagvna.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lagvna.perfectday.EventActivity;
import com.lagvna.perfectday.GuestsActivity;
import com.lagvna.perfectday.R;

public class SummaryFragment extends Fragment {
	private TextView name;
	private TextView place;
	private TextView date;
	private TextView code;
	private TextView description;
	private EventActivity ea;
	private Button guestList;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_summary, container,
				false);

		ea = (EventActivity) getActivity();

		name = (TextView) rootView.findViewById(R.id.eventName);
		place = (TextView) rootView.findViewById(R.id.eventPlace);
		date = (TextView) rootView.findViewById(R.id.eventDate);
		code = (TextView) rootView.findViewById(R.id.code);
		description = (TextView) rootView.findViewById(R.id.desc);

		name.setText(ea.name);
		place.setText(ea.place);
		date.setText(ea.date);
		code.setText(ea.code);
		description.setText(ea.description);

		guestList = (Button) rootView.findViewById(R.id.guestList);

		guestList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), GuestsActivity.class);
				startActivity(i);
			}
		});

		return rootView;
	}
}
