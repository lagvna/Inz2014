package com.lagvna.helpers;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lagvna.customtypes.Contact;
import com.lagvna.customtypes.Event;
import com.lagvna.customtypes.Gift;
import com.lagvna.customtypes.Guest;
import com.lagvna.customtypes.Note;
import com.lagvna.lists.Response;
import com.lagvna.lists.Wall;

public class JSONParser {
	private String inputStream;
	private ArrayList<String> resultArray = new ArrayList<String>();
	private ArrayList<Event> eventArray = new ArrayList<Event>();
	private ArrayList<Guest> guestArray = new ArrayList<Guest>();
	private ArrayList<Contact> contactArray = new ArrayList<Contact>();
	private ArrayList<Note> notesArray = new ArrayList<Note>();
	private ArrayList<Gift> giftsArray = new ArrayList<Gift>();
	private ArrayList<Wall> wallArray = new ArrayList<Wall>();

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

	public ArrayList<String> getGetAllNotesTaskResult() throws JSONException {
		JSONObject jo = new JSONObject(inputStream);

		resultArray.add(jo.getString("result"));
		resultArray.add(jo.getString("message"));
		resultArray.add(jo.get("notes").toString());

		return resultArray;
	}

	public ArrayList<Note> getNotes(String notes) throws JSONException {
		JSONArray ja = new JSONArray(notes);

		for (int i = 0; i < ja.length(); i++) {
			JSONObject jo = new JSONObject();
			jo = ja.getJSONObject(i);
			notesArray.add(new Note(jo.getString("id"), jo.getString("isdone"),
					jo.getString("description")));
		}

		return notesArray;
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

	public ArrayList<String> getAddGiftTaskResult() throws JSONException {
		JSONObject jo = new JSONObject(inputStream);

		resultArray.add(jo.getString("result"));
		resultArray.add(jo.getString("message"));
		resultArray.add(jo.getString("name"));
		resultArray.add(jo.getString("shop"));
		resultArray.add(jo.getString("link"));
		resultArray.add(jo.getString("description"));
		resultArray.add(jo.getString("buyer"));
		resultArray.add(jo.getString("id"));

		return resultArray;
	}

	public ArrayList<String> getGetAllGiftsTaskResult() throws JSONException {
		JSONObject jo = new JSONObject(inputStream);

		resultArray.add(jo.getString("result"));
		resultArray.add(jo.getString("message"));
		resultArray.add(jo.get("gifts").toString());

		return resultArray;
	}

	public ArrayList<Gift> getGifts(String gifts) throws JSONException {
		JSONArray ja = new JSONArray(gifts);

		for (int i = 0; i < ja.length(); i++) {
			JSONObject jo = new JSONObject();
			jo = ja.getJSONObject(i);
			giftsArray.add(new Gift(jo.getString("name"), jo.getString("link"),
					jo.getString("shop"), jo.getString("description"), jo
							.getString("buyer"), jo.getString("id")));
		}

		return giftsArray;
	}

	public ArrayList<String> getAddWallTaskResult() throws JSONException {
		JSONObject jo = new JSONObject(inputStream);

		resultArray.add(jo.getString("result"));
		resultArray.add(jo.getString("message"));
		resultArray.add(jo.getString("note"));
		resultArray.add(jo.getString("author"));
		resultArray.add(jo.getString("date"));
		resultArray.add(jo.getString("id"));

		return resultArray;
	}

	public ArrayList<String> getAddResponseTaskResult() throws JSONException {
		JSONObject jo = new JSONObject(inputStream);

		resultArray.add(jo.getString("result"));
		resultArray.add(jo.getString("message"));
		resultArray.add(jo.getString("note"));
		resultArray.add(jo.getString("author"));
		resultArray.add(jo.getString("date"));
		resultArray.add(jo.getString("wallid"));
		resultArray.add(jo.getString("id"));

		return resultArray;
	}

	public ArrayList<String> getGetWallTaskResult() throws JSONException {
		JSONObject jo = new JSONObject(inputStream);

		resultArray.add(jo.getString("result"));
		resultArray.add(jo.getString("message"));
		resultArray.add(jo.get("wall").toString());

		return resultArray;
	}

	public ArrayList<Wall> getWall(String wall) throws JSONException {
		JSONArray ja = new JSONArray(wall);

		for (int i = 0; i < ja.length(); i++) {
			JSONObject jo = new JSONObject();
			jo = ja.getJSONObject(i);
			Wall w = new Wall(jo.getString("id"), jo.getString("note"),
					jo.getString("author"), jo.getString("date").substring(0,
							19));
			JSONArray ja2 = new JSONArray(jo.get("responses").toString());
			for (int j = 0; j < ja2.length(); j++) {
				JSONObject jo2 = new JSONObject();
				jo2 = ja2.getJSONObject(j);
				Response r = new Response(jo2.getString("id"),
						jo2.getString("note"), jo2.getString("author"), jo2
								.getString("date").substring(0, 19),
						jo2.getString("wallid"));
				w.getResponses().add(r);
			}
			w.getResponses().add(
					new Response("-1", "---Odpowiedz---", "", "", jo
							.getString("id")));

			wallArray.add(w);
		}

		return wallArray;
	}

	public ArrayList<String> getGuestLoginTaskResult() throws JSONException {
		JSONObject jo = new JSONObject(inputStream);

		resultArray.add(jo.getString("result"));
		resultArray.add(jo.getString("message"));
		resultArray.add(jo.getString("name"));
		resultArray.add(jo.getString("place"));
		resultArray.add(jo.getString("date"));
		resultArray.add(jo.getString("note"));
		resultArray.add(jo.getString("code"));
		resultArray.add(jo.getString("organizer"));
		resultArray.add(jo.getString("id"));

		return resultArray;
	}

	public ArrayList<String> getSignupForGiftTaskResult() throws JSONException {
		JSONObject jo = new JSONObject(inputStream);

		resultArray.add(jo.getString("result"));
		resultArray.add(jo.getString("message"));
		resultArray.add(jo.getString("buyer"));

		return resultArray;
	}

	public ArrayList<String> getRemoveSthTaskResult() throws JSONException {
		JSONObject jo = new JSONObject(inputStream);

		resultArray.add(jo.getString("result"));
		resultArray.add(jo.getString("message"));

		return resultArray;
	}
}
