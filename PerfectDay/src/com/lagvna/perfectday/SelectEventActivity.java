package com.lagvna.perfectday;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.lagvna.adapters.ListViewAdapter;
import com.lagvna.helpers.DataHelper;
import com.lagvna.lists.CustomRow;

public class SelectEventActivity extends ListActivity {
	private Button addEvent;
	private AlertDialog.Builder eventTypeDialog;
	private int eventType;
	private ArrayList<CustomRow> CustomRow_data;
	private ListViewAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_select_event);

		CustomRow_data = new ArrayList<CustomRow>();

		createList();

		adapter = new ListViewAdapter(this, R.layout.element, CustomRow_data);

		setListAdapter(adapter);
		ListView lv = getListView();
		lv.setTextFilterEnabled(true);
		registerForContextMenu(getListView());

		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(SelectEventActivity.this,
						EventActivity.class);
				i.putExtra("title", CustomRow_data.get(position).title);
				SelectEventActivity.this.startActivity(i);
			}
		});

		addEvent = (Button) findViewById(R.id.addNewEvent);

		addEvent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				eventTypeDialog.show();
			}
		});

		eventTypeDialog = new AlertDialog.Builder(this);
		eventTypeDialog.setIcon(R.drawable.ic_launcher);
		eventTypeDialog.setTitle("Typ imprezy");
		eventTypeDialog.setMessage("Jakie wydarzenie organizujesz?");
		eventTypeDialog.setPositiveButton("Formalne",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						setEventType(0);

						Intent intent = new Intent(SelectEventActivity.this,
								UpdateEventActivity.class);
						intent.putExtra("eventType", eventType);
						SelectEventActivity.this.startActivity(intent);

						// String url =
						// "http://192.168.1.101:8000/pdapp/checkuser";
						// new CheckUserTask().execute(new String[] { url });

					}
				});
		eventTypeDialog.setNegativeButton("Nieformalne",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						setEventType(1);
						Intent intent = new Intent(SelectEventActivity.this,
								EventActivity.class);
						intent.putExtra("eventType", eventType);
						SelectEventActivity.this.startActivity(intent);
					}
				});

	}

	private void createList() {
		CustomRow_data.add(new CustomRow(R.drawable.lvsel, "Impreza 1",
				"18.11.2014"));

		/*
		 * File f = new
		 * File(Environment.getExternalStoragePublicDirectory(Environment
		 * .DIRECTORY_DCIM)+"/"); String[] names = f.list(); SimpleDateFormat
		 * sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		 * 
		 * CustomRow_data.clear();
		 * 
		 * for(int i = 0; i < names.length; i++) {
		 * if(names[i].startsWith("cbb")) { File tmp = new
		 * File(Environment.getExternalStoragePublicDirectory
		 * (Environment.DIRECTORY_DCIM)+"/"+names[i]); String date =
		 * sdf.format(tmp.lastModified()); String title = names[i];
		 * CustomRow_data.add(new CustomRow(R.drawable.lvsel, title, date)); } }
		 */
	}

	public int getEventType() {
		return eventType;
	}

	public void setEventType(int eventType) {
		this.eventType = eventType;
	}

	public void showToast(String result) {
		Toast.makeText(this, Html.fromHtml(result), Toast.LENGTH_SHORT).show();
	}

	private class CheckUserTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			String result = "";
			// String accessToken = params[1];
			String url = params[0];

			try {

				String session = DataHelper.getInstance().getSession();
				HttpClient httpClient = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(url);
				httpGet.addHeader("Cookie", session);
				System.err.println("gowno: " + session);

				HttpResponse httpResponse = httpClient
						.execute((HttpUriRequest) httpGet);
				try {
					HttpEntity entity = httpResponse.getEntity();
					if (entity != null) {
						result = EntityUtils.toString(entity);
					} else {
						result = "sth gone wrong";
					}
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				// getCookies(httpResponse.getAllHeaders());
			} catch (Exception e) {
			}

			System.err.println(result);
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			showToast(result);
		}
	}
}
