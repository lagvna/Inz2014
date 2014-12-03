package com.lagvna.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.lagvna.adapters.ListViewAdapter;
import com.lagvna.lists.CustomRow;
import com.lagvna.perfectday.R;

public class GiftFragment extends Fragment {
	private ArrayList<CustomRow> CustomRow_data;
	private ListViewAdapter listAdapter;
	private ListView lv;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_gift, container,
				false);
		CustomRow_data = new ArrayList<CustomRow>();

		CustomRow_data.add(new CustomRow(R.drawable.lvsel, "Odkurzacz", ""));
		CustomRow_data.add(new CustomRow(R.drawable.lvsel, "Stół", ""));
		CustomRow_data.add(new CustomRow(R.drawable.lvsel, "Sztućce", ""));
		CustomRow_data.add(new CustomRow(R.drawable.lvsel, "Miotła", ""));
		
		lv = (ListView) rootView.findViewById(R.id.listGifts);
		listAdapter = new ListViewAdapter(getActivity(), R.layout.element,
				CustomRow_data);
		lv.setAdapter(listAdapter);
		

		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			}
		});
		return rootView;
	}
}
