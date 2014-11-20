package com.lagvna.helpers;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

public class DataHelper {
	private HttpClient client = new DefaultHttpClient();
	private String session;
	private String crsfToken;
	private String message;
	private String accessToken;
	private String serverUrl;

	private static final DataHelper dh = new DataHelper();

	public static DataHelper getInstance() {
		return dh;
	}

	public HttpClient getClient() {
		return client;
	}

	public void setCookies(String[] tmp)	{
		for(int i = 0; i < tmp.length; i++)	{
			if(tmp[i].startsWith("sessionid"))	{
				setSession(tmp[i]);
			} else if(tmp[i].startsWith("messages"))	{
				setMessage(tmp[i]);
			} else	{
				setCrsfToken(tmp[i]);
			}
		}
	}

	public void setClient(HttpClient client) {
		this.client = client;
	}

	public String getCrsfToken() {
		return crsfToken;
	}

	public void setCrsfToken(String crsfToken) {
		this.crsfToken = crsfToken;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
}
