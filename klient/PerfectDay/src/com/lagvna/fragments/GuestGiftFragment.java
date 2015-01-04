package com.lagvna.fragments;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.lagvna.adapters.ListViewAdapter;
import com.lagvna.customtypes.Gift;
import com.lagvna.lists.CustomRow;
import com.lagvna.perfectday.GiftDetailsActivity;
import com.lagvna.perfectday.GuestEventActivity;
import com.lagvna.perfectday.R;

public class GuestGiftFragment extends Fragment {
	private ArrayList<CustomRow> CustomRow_data;
	private ListViewAdapter listAdapter;
	private ListView lv;
	private ArrayList<Gift> giftArr;
	private GuestEventActivity ea;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_guest_gift,
				container, false);

		ea = (GuestEventActivity) getActivity();

		CustomRow_data = new ArrayList<CustomRow>();

		lv = (ListView) rootView.findViewById(R.id.listGifts);

		listAdapter = new ListViewAdapter(getActivity(), R.layout.element,
				CustomRow_data);
		lv.setAdapter(listAdapter);

		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(getActivity(), GiftDetailsActivity.class);
				i.putExtra("name", giftArr.get(position).getName());
				i.putExtra("link", giftArr.get(position).getLink());
				i.putExtra("shop", giftArr.get(position).getShop());
				i.putExtra("id", giftArr.get(position).getId());
				i.putExtra("description", giftArr.get(position)
						.getDescription());
				i.putExtra("currentbuyer", giftArr.get(position).getBuyer());
				i.putExtra("position", position);

				getActivity().startActivityForResult(i, 2);
			}
		});

		createList();

		return rootView;
	}

	public void createList() {
		giftArr = ea.giftArr;
		listAdapter.notifyDataSetChanged();
		CustomRow_data.clear();
		for (int i = 0; i < ea.giftArr.size(); i++) {
			String buyer = ea.giftArr.get(i).getBuyer();
			String name = ea.giftArr.get(i).getName();
			CustomRow_data.add(new CustomRow(R.drawable.lvsel, name, buyer));
		}
	}

	@SuppressWarnings("unused")
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		AdapterView.AdapterContextMenuInfo info;
		try {
			info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		} catch (ClassCastException e) {
			Log.e("perfectday", "bad menuInfo", e);
			return;
		}

		menu.setHeaderTitle("Menu");
		menu.add(0, v.getId(), 0, "Usuń");
	}

	public void postDelete(int position) {
		ea.giftArr.remove(position);
		createList();
	}
}
