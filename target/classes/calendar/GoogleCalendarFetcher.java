package calendar;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.CalendarEventFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

public class GoogleCalendarFetcher {
	
	private String emailAddress;
	private String pword;

	public GoogleCalendarFetcher(String emailAddress, String pword){
		this.emailAddress = emailAddress;
		this.pword = pword;
	}
	
	public ArrayList<Entry> getEventEntries(){
		// Create a Service to access the calendar
		CalendarService myService = new CalendarService("LivingWall GoogleCalendarAccess");
		try {
			myService.setUserCredentials(this.emailAddress, this.pword);
		} catch (AuthenticationException e) {
			System.err.println("Authentification failed!");
		}

		// Send the request and print the response
		URL feedUrl = null;
		try {
			feedUrl = new URL("https://www.google.com/calendar/feeds/default/private/full");
		} catch (MalformedURLException e) {
			System.err.println("Calendar page could not be retrieved");
		}
		CalendarEventFeed feed = null;
		try {
			feed = myService.getFeed(feedUrl, CalendarEventFeed.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LinkedList<CalendarEventEntry> events = (LinkedList<CalendarEventEntry>) feed.getEntries();
		
		ArrayList<Entry> entries = new ArrayList<Entry>();
		for (CalendarEventEntry e : events){
//			e.getParticipants()
			String content = e.getPlainTextContent();
			String name = e.getTitle().getPlainText();
			Date stime = null;
			Date etime = null;
			if (!e.getTimes().isEmpty()){
				stime = new Date(e.getTimes().get(0).getStartTime().getValue());
				etime = new Date(e.getTimes().get(0).getEndTime().getValue());
			}
			String location = e.getLocations().get(0).getValueString();
			Entry currentEntry = new Entry(content, name, stime, etime, location);
			entries.add(currentEntry);
		}
		return entries;
	}
}
