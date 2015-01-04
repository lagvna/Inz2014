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
import com.lagvna.perfectday.GiftDetailsActivity;

public class SignupForGiftTask extends AsyncTask<Void, Void, Void> {
	private GiftDetailsActivity callingActivity;
	private String url;
	private ArrayList<String> result;
	private String message;

	public SignupForGiftTask(GiftDetailsActivity callingActivity, String id,
			String buyer) {
		this.callingActivity = callingActivity;

		try {
			id = URLEncoder.encode(id, "utf-8");
			buyer = URLEncoder.encode(buyer, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		url = DataHelper.getInstance().getServerUrl() + "signupgift?id=" + id
				+ "&buyer=" + buyer;

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
			result = jp.getSignupForGiftTaskResult();
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
		callingActivity.setSignupDetails(result.get(2));
		callingActivity.runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(callingActivity, message, Toast.LENGTH_SHORT)
						.show();
			}
		});
		callingActivity.finish();
	}
}