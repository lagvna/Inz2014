package com.lagvna.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.lagvna.adapters.MyExpandableListAdapter;
import com.lagvna.lists.Group;
import com.lagvna.perfectday.R;

public class WallFragment extends Fragment {
	SparseArray<Group> groups = new SparseArray<Group>();

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_wall, container,
				false);
		createData();
		ExpandableListView listView = (ExpandableListView) rootView
				.findViewById(R.id.listView);
		MyExpandableListAdapter adapter = new MyExpandableListAdapter(
				getActivity(), groups);
		listView.setAdapter(adapter);

		return rootView;
	}

	public void createData() {
		Group group = new Group("Wpis: 2-12-2014, 01:43");
		group.children
				.add("Przypominam, że kąpiel przed imprezą jest obowiązkowa!");
		group.children.add("bez sensu...");
		groups.append(0, group);

		Group group2 = new Group("Wpis: 1-12-2014, 12:32");
		for (int i = 0; i < 5; i++) {
			group2.children
					.add("Przypominam, że kąpiel przed imprezą jest obowiązkowa!"
							+ i);
		}
		groups.append(1, group2);

		Group group3 = new Group("Wpis: 29-11-2014, 16:02");
		for (int i = 0; i < 5; i++) {
			group3.children
					.add("Przypominam, że kąpiel przed imprezą jest obowiązkowa!"
							+ i);
		}
		groups.append(1, group3);
	}
}
