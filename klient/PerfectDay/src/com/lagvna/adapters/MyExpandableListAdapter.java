package com.lagvna.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.lagvna.lists.Response;
import com.lagvna.lists.Wall;
import com.lagvna.perfectday.R;

public class MyExpandableListAdapter extends BaseExpandableListAdapter {
	private List<Wall> catList;
	private Context ctx;

	public MyExpandableListAdapter(List<Wall> catList, Context ctx) {
		this.catList = catList;
		this.ctx = ctx;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return catList.get(groupPosition).getResponses().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return catList.get(groupPosition).getResponses().get(childPosition)
				.hashCode();
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) ctx
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.listrow_details, parent, false);
		}
		TextView itemNote = (TextView) v.findViewById(R.id.detailNote);
		TextView itemAuthor = (TextView) v.findViewById(R.id.detailAuthor);
		TextView itemDate = (TextView) v.findViewById(R.id.detailDate);
		Response det = catList.get(groupPosition).getResponses()
				.get(childPosition);
		itemNote.setText(det.getNote());
		itemAuthor.setText(det.getAuthor());
		itemDate.setText(det.getDate());

		v.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO ostatni dodaje
				System.out.println("KLIKŁEM!");
			}
		});
		return v;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		int size = catList.get(groupPosition).getResponses().size();
		System.out.println("Child for group [" + groupPosition + "] is ["
				+ size + "]");
		return size;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return catList.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return catList.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return catList.get(groupPosition).hashCode();
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) ctx
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.listrow_group, parent, false);
		}
		TextView groupNote = (TextView) v.findViewById(R.id.groupNote);
		TextView groupAuthor = (TextView) v.findViewById(R.id.groupAuthor);
		TextView groupDate = (TextView) v.findViewById(R.id.groupDate);
		Wall cat = catList.get(groupPosition);
		groupNote.setText(cat.getNote());
		groupAuthor.setText(cat.getAuthor());
		groupDate.setText(cat.getDate());

		/*
		 * v.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) {
		 * System.out.println("KLIKLEM DUZEGO"); } });
		 */
		return v;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}