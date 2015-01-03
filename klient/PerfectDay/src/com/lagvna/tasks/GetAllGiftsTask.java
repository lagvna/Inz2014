package com.lagvna.tasks;

import java.io.IOException;
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

import com.lagvna.customtypes.Gift;
import com.lagvna.helpers.DataHelper;
import com.lagvna.helpers.JSONParser;
import com.lagvna.perfectday.EventActivity;

public class GetAllGiftsTask extends AsyncTask<Void, Void, Void> {
	private EventActivity callingActivity;
	private String url;
	private ArrayList<String> result;
	private String message;
	private ArrayList<Gift> giftsArr;

	public GetAllGiftsTask(EventActivity callingActivity, String eventId) {
		this.callingActivity = callingActivity;
		url = DataHelper.getInstance().getServerUrl() + "getallgifts?eventid="
				+ eventId;
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
			result = jp.getGetAllGiftsTaskResult();
			if (entity != null) {
				if (result.get(0).equals("success")) {
					message = result.get(1);
					String gifts = result.get(2);
					giftsArr = jp.getGifts(gifts);
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
		callingActivity.createGiftList(giftsArr);
		callingActivity.runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(callingActivity, message, Toast.LENGTH_SHORT)
						.show();
			}
		});
	}
}