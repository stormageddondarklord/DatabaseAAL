package objects;

import java.sql.Timestamp;
import java.util.Date;

import de.dailab.jiactng.agentcore.knowledge.IFact;

public class FacebookUser implements IFact{
	
	private long fbid;
	private String username;
	private String name;
	private String middleName;
	private String lastName;
	private String birthday;
	private String email;
	private String gender;
	private String picture;
	private Timestamp timestamp;
	
	public FacebookUser(){
		name = "";
		middleName = "";
		lastName = "";
		gender = "";
	}
	
	public void setFbid(long fbid){
		this.fbid = fbid;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setMiddleName(String middleName){
		this.middleName = middleName;
	}
	
	public void setLastName(String lastName){
		this.lastName = lastName;
	}
	
	public void setGender(String gender){
		this.gender = gender;
	}
	
	public void setPicture(String url){
		this.picture = url;
	}
	
	public void setTimestamp(Timestamp timestamp){
		this.timestamp = timestamp;
	}
	
	public long getFbid(){
		return fbid;
	}
	
	public String getName(){
		return name;
	}
	
	public String getMiddleName(){
		return middleName;
	}
	
	public String getLastName(){
		return lastName;
	}
	
	public String getGender(){
		return gender;
	}
	
	public String getPicture(){
		return picture;
	}
	
	public Date getTimestamp(){
		return timestamp;
	}

}
