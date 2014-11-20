package com.lagvna.tasks;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import android.os.AsyncTask;
import android.widget.Toast;

import com.lagvna.helpers.DataHelper;
import com.lagvna.perfectday.UpdateEventActivity;

public class UpdateEventTask extends AsyncTask<Void, Void, Void> {
	private UpdateEventActivity callingActivity;
	private String url;
	private String result;

	public UpdateEventTask(UpdateEventActivity callingActivity,
			String eventName, String eventDate, String eventPlace,
			String eventDescription, int isFormal) {
		this.callingActivity = callingActivity;
		url = DataHelper.getInstance().getServerUrl()
				+ "updateevent?eventname=" + eventName + "&eventdate="
				+ eventDate + "&eventplace=" + eventPlace + "&eventdesc="
				+ eventDescription + "&isformal=" + isFormal;
	}

	@Override
	protected void onPreExecute() {
		callingActivity.showProgressDial();
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(Void... params) {
		try {
			HttpClient httpClient = DataHelper.getInstance().getClient();
			HttpGet httpGet = new HttpGet(url);
			HttpResponse httpResponse;

			httpResponse = httpClient.execute((HttpUriRequest) httpGet);

			HttpEntity entity = httpResponse.getEntity();
			if (entity != null) {
				result = "Dodano wydarzenie pomyślnie";
			} else {
				result = "Błąd połączenia z serwerem";
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected void onPostExecute(Void arg) {
		callingActivity.hideProgressDial();

		callingActivity.runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(callingActivity, result, Toast.LENGTH_SHORT)
						.show();
			}
		});
	}
}
