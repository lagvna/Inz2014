package com.lagvna.tasks;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import android.os.AsyncTask;
import android.widget.Toast;

import com.lagvna.helpers.DataHelper;
import com.lagvna.helpers.JSONParser;
import com.lagvna.perfectday.AddGiftActivity;

public class AddGiftTask extends AsyncTask<Void, Void, Void> {
	private AddGiftActivity callingActivity;
	private String url;
	private ArrayList<String> result;
	private String message;

	public AddGiftTask(AddGiftActivity callingActivity, String name,
			String shop, String link, String desc, String eventId) {
		this.callingActivity = callingActivity;

		try {
			name = URLEncoder.encode(name, "utf-8");
			shop = URLEncoder.encode(shop, "utf-8");
			link = URLEncoder.encode(link, "utf-8");
			desc = URLEncoder.encode(desc, "utf-8");
			eventId = URLEncoder.encode(eventId, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		url = DataHelper.getInstance().getServerUrl() + "addgift?name=" + name
				+ "&shop=" + shop + "&link=" + link + "&description=" + desc
				+ "&buyer=brak" + "&eventid=" + eventId;

	}

	@Override
	protected void onPreExecute() {
		callingActivity.showProgressDial();
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(Void... params) {
		try {

			String session = DataHelper.getInstance().getSession();
			HttpClient httpClient = DataHelper.getInstance().getClient();
			HttpGet httpGet = new HttpGet(url);
			httpGet.addHeader("Cookie", session);
			HttpResponse httpResponse;

			httpResponse = httpClient.execute((HttpUriRequest) httpGet);

			HttpEntity entity = httpResponse.getEntity();
			String res = EntityUtils.toString(entity);
			System.out.println(res);
			JSONParser jp = new JSONParser(res);
			result = jp.getAddGiftTaskResult();
			if (entity != null) {
				if (result.get(0).equals("success")) {
					message = result.get(1);
				} else {
					message = "Coś poszło nie tak";
				}
			} else {
				message = "Błąd połączenia z serwerem";
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected void onPostExecute(Void arg) {
		callingActivity.hideProgressDial();
		callingActivity.setGiftDetails(result.get(2), result.get(3),
				result.get(4), result.get(5), result.get(6), result.get(7));
		callingActivity.runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(callingActivity, message, Toast.LENGTH_SHORT)
						.show();
			}
		});
		callingActivity.finish();
	}
}