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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.lagvna.adapters.ListViewAdapter;
import com.lagvna.customtypes.Gift;
import com.lagvna.lists.CustomRow;
import com.lagvna.perfectday.AddGiftActivity;
import com.lagvna.perfectday.EventActivity;
import com.lagvna.perfectday.GiftDetailsActivity;
import com.lagvna.perfectday.R;

public class GiftFragment extends Fragment {
	private ArrayList<CustomRow> CustomRow_data;
	private ListViewAdapter listAdapter;
	private ListView lv;
	private Button addGift;
	private ArrayList<Gift> giftArr;
	private EventActivity ea;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_gift, container,
				false);

		ea = (EventActivity) getActivity();

		CustomRow_data = new ArrayList<CustomRow>();

		lv = (ListView) rootView.findViewById(R.id.listGifts);
		addGift = (Button) rootView.findViewById(R.id.addGift);

		listAdapter = new ListViewAdapter(getActivity(), R.layout.element,
				CustomRow_data);
		lv.setAdapter(listAdapter);
		registerForContextMenu(rootView.findViewById(R.id.listGifts));

		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(getActivity(), GiftDetailsActivity.class);
				i.putExtra("name", giftArr.get(position).getName());
				i.putExtra("link", giftArr.get(position).getLink());
				i.putExtra("shop", giftArr.get(position).getShop());
				i.putExtra("description", giftArr.get(position)
						.getDescription());

				getActivity().startActivity(i);
			}
		});

		addGift.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), AddGiftActivity.class);
				startActivityForResult(i, 1);
			}
		});

		createList();

		return rootView;
	}

	public void createList() {
		giftArr = ea.giftArr;
		listAdapter.notifyDataSetChanged();
		CustomRow_data.clear();
		for (int i = 0; i < giftArr.size(); i++) {
			String buyer = giftArr.get(i).getBuyer();
			String name = giftArr.get(i).getName();
			CustomRow_data.add(new CustomRow(R.drawable.lvsel, name, buyer));
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		getActivity();
		if (requestCode == 1 && resultCode == Activity.RESULT_OK) {

			Bundle extras = data.getExtras();
			String name = (String) extras.getString("name");
			String link = (String) extras.getString("link");
			String shop = (String) extras.getString("shop");
			String description = (String) extras.getString("description");
			String buyer = (String) extras.getString("buyer");
			String id = (String) extras.getString("id");

			ea.giftArr.add(new Gift(name, link, shop, description, buyer, id));
			createList();
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
