package com.lagvna.fragments;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

import com.lagvna.adapters.MyExpandableListAdapter;
import com.lagvna.lists.Response;
import com.lagvna.lists.Wall;
import com.lagvna.perfectday.AddResponseActivity;
import com.lagvna.perfectday.GuestEventActivity;
import com.lagvna.perfectday.R;

public class GuestWallFragment extends Fragment {
	private List<Wall> wallArr;
	private Button addResponse;
	public SwipeRefreshLayout mSwipeRefreshLayout;
	public MyExpandableListAdapter exAdpt;
	public ExpandableListView exList;
	private GuestEventActivity ea;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_guest_wall, container,
				false);

		ea = (GuestEventActivity) getActivity();
		wallArr = ea.wallArr;
		mSwipeRefreshLayout = (SwipeRefreshLayout) rootView
				.findViewById(R.id.activity_main_swipe_refresh_layout);
		exList = (ExpandableListView) rootView
				.findViewById(R.id.expandableListView1);

		addResponse = (Button) rootView.findViewById(R.id.addResponseD);
		exList.setIndicatorBounds(5, 5);
		exAdpt = new MyExpandableListAdapter(wallArr, getActivity());

		exList.setIndicatorBounds(0, 20);
		exList.setAdapter(exAdpt);

		exList.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {

				TextView tv = (TextView) v.findViewById(R.id.detailNote);

				String data = tv.getText().toString();
				if (data.equals("---Odpowiedz---")) {
					Intent i = new Intent(getActivity(),
							AddResponseActivity.class);
					i.putExtra("id", wallArr.get(groupPosition).getId());
					startActivityForResult(i, 1);
				}

				return true;
			}
		});

		mSwipeRefreshLayout
				.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
					@Override
					public void onRefresh() {

						ea.getWall();
					}
				});

		addResponse.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), AddResponseActivity.class);
				i.putExtra("id", "new");
				startActivityForResult(i, 1);
			}
		});

		createList();

		return rootView;
	}

	public void createList() {
		wallArr = ea.wallArr;
		exAdpt = new MyExpandableListAdapter(wallArr, getActivity());
		exList.setAdapter(exAdpt);
		mSwipeRefreshLayout.setRefreshing(false);
		exAdpt.notifyDataSetChanged();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		getActivity();
		if (requestCode == 1 && resultCode == Activity.RESULT_OK) {

			Bundle extras = data.getExtras();
			String note = (String) extras.getString("note");
			String author = (String) extras.getString("author");
			String date = (String) extras.getString("date");
			String wallId = (String) extras.getString("wallid");
			String id = (String) extras.getString("id");

			if (wallId.equals("null")) {
				date = date.substring(0, 19);
				Wall w = new Wall(id, note, author, date);
				w.getResponses().add(
						new Response("-1", "---Odpowiedz---", "", "", id));
				ea.wallArr.add(w);
				createList();
			} else {
				for (int i = 0; i < wallArr.size(); i++) {
					if (wallArr.get(i).getId().equals(wallId)) {
						int toAdd = i;
						date = date.substring(0, 19);
						Response r = new Response(id, note, author, date,
								wallId);
						int size = ea.wallArr.get(toAdd).getResponses().size();
						ea.wallArr.get(toAdd).getResponses().remove(size - 1);
						ea.wallArr.get(toAdd).getResponses().add(r);
						ea.wallArr
								.get(toAdd)
								.getResponses()
								.add(new Response("-1", "---Odpowiedz---", "",
										"", id));
						break;
					}
				}
				createList();
			}
		}
	}
}
