package messages;

import java.util.ArrayList;

import calendar.Entry;
import de.dailab.jiactng.agentcore.knowledge.IFact;

public class CalendarEntries implements IFact{
	
	ArrayList<Entry> entries;
	
	public CalendarEntries(ArrayList<Entry> entries){
		this.entries = entries;
	}
	
	public void setEntries(ArrayList<Entry> entries){
		this.entries = entries;
	}
	
	public ArrayList<Entry> getEntries(){
		return this.entries;
	}

}
