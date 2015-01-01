package com.lagvna.helpers;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lagvna.customtypes.Contact;
import com.lagvna.customtypes.Event;
import com.lagvna.customtypes.Guest;

public class JSONParser {
	private String inputStream;
	private ArrayList<String> resultArray = new ArrayList<String>();
	private ArrayList<Event> eventArray = new ArrayList<Event>();
	private ArrayList<Guest> guestArray = new ArrayList<Guest>();
	private ArrayList<Contact> contactArray = new ArrayList<Contact>();

	public JSONParser(String inputStream) {

		this.inputStream = inputStream;
	}

	public ArrayList<String> getLoginTaskResult() throws JSONException {
		JSONObject jo = new JSONObject(inputStream);

		resultArray.add(jo.getString("result"));
		resultArray.add(jo.getString("message"));
		resultArray.add(jo.getString("login"));

		System.out.println(resultArray.get(0));

		return resultArray;
	}

	public ArrayList<String> getAddEventTaskResult() throws JSONException {
		JSONObject jo = new JSONObject(inputStream);

		resultArray.add(jo.getString("result"));
		resultArray.add(jo.getString("message"));
		resultArray.add(jo.getString("name"));
		resultArray.add(jo.getString("place"));
		resultArray.add(jo.getString("date"));
		resultArray.add(jo.getString("description"));
		resultArray.add(jo.getString("code"));
		resultArray.add(jo.getString("id"));

		return resultArray;
	}

	public ArrayList<String> getGetAllEventsTaskResult() throws JSONException {
		JSONObject jo = new JSONObject(inputStream);

		resultArray.add(jo.getString("result"));
		resultArray.add(jo.getString("message"));
		resultArray.add(jo.get("events").toString());

		return resultArray;
	}

	public ArrayList<Event> getEvents(String events) throws JSONException {
		JSONArray ja = new JSONArray(events);

		for (int i = 0; i < ja.length(); i++) {
			JSONObject jo = new JSONObject();
			jo = ja.getJSONObject(i);
			eventArray.add(new Event(jo.getString("id"), jo.getString("date"),
					jo.getString("name"), jo.getString("place"), jo
							.getString("note"), jo.getString("code")));
		}

		return eventArray;
	}

	public ArrayList<String> getGetAllGuestsTaskResult() throws JSONException {
		JSONObject jo = new JSONObject(inputStream);

		resultArray.add(jo.getString("result"));
		resultArray.add(jo.getString("message"));
		resultArray.add(jo.get("guests").toString());

		return resultArray;
	}

	public ArrayList<Guest> getGuests(String guests) throws JSONException {
		JSONArray ja = new JSONArray(guests);

		for (int i = 0; i < ja.length(); i++) {
			JSONObject jo = new JSONObject();
			jo = ja.getJSONObject(i);
			guestArray
					.add(new Guest(jo.getString("name"), jo
							.getString("surname"), jo.getString("email")
							.replace(".at.", "@"), jo.getString("telephone"),
							jo.getString("id")));
		}

		return guestArray;
	}

	public ArrayList<String> getGetAllContactsTaskResult() throws JSONException {
		JSONObject jo = new JSONObject(inputStream);

		resultArray.add(jo.getString("result"));
		resultArray.add(jo.getString("message"));
		resultArray.add(jo.get("contacts").toString());

		return resultArray;
	}

	public ArrayList<Contact> getContacts(String contacts) throws JSONException {
		JSONArray ja = new JSONArray(contacts);

		for (int i = 0; i < ja.length(); i++) {
			JSONObject jo = new JSONObject();
			jo = ja.getJSONObject(i);
			contactArray.add(new Contact(jo.getString("id"), jo
					.getString("name"), jo.getString("sector"), jo.getString(
					"email").replace(".at.", "@"), jo.getString("telephone"),
					jo.getString("note")));
		}

		return contactArray;
	}

	public ArrayList<String> getAddGuestTaskResult() throws JSONException {
		JSONObject jo = new JSONObject(inputStream);

		resultArray.add(jo.getString("result"));
		resultArray.add(jo.getString("message"));
		resultArray.add(jo.getString("name"));
		resultArray.add(jo.getString("surname"));
		resultArray.add(jo.getString("email"));
		resultArray.add(jo.getString("phone"));
		resultArray.add(jo.getString("event"));
		resultArray.add(jo.getString("id"));

		return resultArray;
	}

	public ArrayList<String> getAddContactTaskResult() throws JSONException {
		JSONObject jo = new JSONObject(inputStream);

		resultArray.add(jo.getString("result"));
		resultArray.add(jo.getString("message"));
		resultArray.add(jo.getString("name"));
		resultArray.add(jo.getString("sector"));
		resultArray.add(jo.getString("email"));
		resultArray.add(jo.getString("telephone"));
		resultArray.add(jo.getString("note"));
		resultArray.add(jo.getString("id"));

		return resultArray;
	}

	public ArrayList<String> getAddNoteTaskResult() throws JSONException {
		JSONObject jo = new JSONObject(inputStream);

		resultArray.add(jo.getString("result"));
		resultArray.add(jo.getString("message"));
		resultArray.add(jo.getString("description"));
		resultArray.add(jo.getString("isdone"));
		resultArray.add(jo.getString("id"));

		return resultArray;
	}

	public ArrayList<String> getRemoveSthTaskResult() throws JSONException {
		JSONObject jo = new JSONObject(inputStream);

		resultArray.add(jo.getString("result"));
		resultArray.add(jo.getString("message"));

		return resultArray;
	}
}
