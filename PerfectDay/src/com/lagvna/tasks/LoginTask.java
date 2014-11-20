package com.lagvna.tasks;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import android.os.AsyncTask;
import android.widget.Toast;

import com.lagvna.helpers.DataHelper;
import com.lagvna.perfectday.LoginActivity;

public class LoginTask extends AsyncTask<Void, Void, Void> {
	private LoginActivity callingActivity;
	private String url;
	private String result;

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
			if (entity != null) {
				result = "Zalogowano pomyślnie do serwera";
			} else {
				result = "Błąd połączenia z serwerem";
			}

			getCookies(httpResponse.getAllHeaders());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	private void getCookies(Header[] headers) {
		String[] tmp = new String[3];
		int i = 0;

		for (Header h : headers) {
			if (h.getName().trim().equals("Set-Cookie")) {
				tmp[i] = h.getValue().split(";")[0].trim();
				i++;
			}
		}
		DataHelper.getInstance().setCookies(tmp);
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
