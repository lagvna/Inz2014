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
import com.lagvna.perfectday.LoginActivity;

public class GuestLoginTask extends AsyncTask<Void, Void, Void> {
	private LoginActivity callingActivity;
	private String url;
	private ArrayList<String> result;
	private String message = "failure";

	public GuestLoginTask(LoginActivity callingActivity, String code) {
		this.callingActivity = callingActivity;

		try {
			code = URLEncoder.encode(code, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		url = DataHelper.getInstance().getServerUrl() + "guestlogin?code="
				+ code;
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
			try {
				JSONParser jp = new JSONParser(res);
				result = jp.getGuestLoginTaskResult();
				if (entity != null) {
					if (result.get(0).equals("success")) {
						message = result.get(1);
					} else {
						message = "Coś poszło nie tak";
					}
				} else {
					message = "Błąd połączenia z serwerem lub zły kod";
				}
			} catch (JSONException e) {
				message = "failure";
				e.printStackTrace();
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
		if (result.get(0).equals("success")) {
			callingActivity.setEventDetails(result.get(2), result.get(3),
					result.get(4), result.get(5), result.get(6), result.get(7),
					result.get(8));
		}
		callingActivity.runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(callingActivity, message, Toast.LENGTH_SHORT)
						.show();
			}
		});
	}
}