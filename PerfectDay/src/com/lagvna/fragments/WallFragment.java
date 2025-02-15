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
		for (int j = 0; j < 5; j++) {
			Group group = new Group("Test " + j);
			for (int i = 0; i < 5; i++) {
				group.children.add("Sub Item" + i);
			}
			groups.append(j, group);
		}
	}
}
