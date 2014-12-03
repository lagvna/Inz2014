package com.lagvna.tasks;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.Header;
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
import com.lagvna.perfectday.LoginActivity;

public class LoginTask extends AsyncTask<Void, Void, Void> {
	private LoginActivity callingActivity;
	private String url;
	private Header[] headers;
	private ArrayList<String> result;
	private String message;

	public LoginTask(String url, LoginActivity callingActivity) {
		this.callingActivity = callingActivity;
		this.url = url;
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
			JSONParser jp = new JSONParser(EntityUtils.toString(entity));
			result = jp.getLoginTaskResult();
			if (entity != null) {
				// String success = result.get(0);
				message = result.get(1);
				DataHelper.getInstance().setLogin(result.get(2));
				// System.out.println(login);
			} else {
				message = "Błąd połączenia z serwerem";
			}
			System.err.println(entity.toString());
			headers = httpResponse.getAllHeaders();
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

		callingActivity.getCookies(headers);

		callingActivity.runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(callingActivity, message, Toast.LENGTH_SHORT)
						.show();
			}
		});
	}
}
