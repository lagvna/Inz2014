package com.lagvna.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.lagvna.adapters.MyExpandableListAdapter;
import com.lagvna.lists.Wall;
import com.lagvna.lists.Response;
import com.lagvna.perfectday.R;

public class WallFragment extends Fragment {
	private List<Wall> catList;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_wall, container,
				false);
		initData();
		ExpandableListView exList = (ExpandableListView) rootView
				.findViewById(R.id.expandableListView1);
		exList.setIndicatorBounds(5, 5);
		MyExpandableListAdapter exAdpt = new MyExpandableListAdapter(catList,
				getActivity());
		exList.setIndicatorBounds(0, 20);
		exList.setAdapter(exAdpt);

		return rootView;
	}

	private void initData() {
		catList = new ArrayList<Wall>();
		Wall cat1 = createCategory(1, "Game for console", "jarek", "gowno");
		cat1.setResponses(createItems("Game Item", "This is the game n.", 5));
		catList.add(cat1);
	}

	private Wall createCategory(long id, String note, String author, String date) {
		return new Wall(id, note, author, date);
	}

	private List<Response> createItems(String name, String descr, int num) {
		List<Response> result = new ArrayList<Response>();
		for (int i = 0; i < num; i++) {
			Response item = new Response(i, "gowno", "mirek", "wczoraj");
			result.add(item);
		}
		return result;
	}
}
