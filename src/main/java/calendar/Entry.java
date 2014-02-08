package calendar;

import java.util.Date;

import de.dailab.jiactng.agentcore.knowledge.IFact;

public class Entry{

	private String discription;
	private String name;
	private Date startTime;
	private Date endTime;
	private String location;
	
	public Entry(String discription, String name, Date startTime, Date endTime, String location){
		this.setDiscription(discription);
		this.setName(name);
		this.setStartTime(startTime);
		this.setEndTime(endTime);
		this.setLocation(location);
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	@Override
	public String toString(){
		String returnString = "";
		returnString+= "Title: "+name+", Discription: "+discription+"\n"
				+"Start: ";
		if (startTime != null){
			returnString+= startTime.toString()+"; End: "+endTime.toString()+" \n";
		}
		returnString+= "Location: "+location+"\n";
		return returnString;
	}
}
