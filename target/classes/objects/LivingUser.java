package objects;
import java.sql.Timestamp;
import java.util.Date;


public class LivingUser {
	
	String name;
	String lastname;
	String gender;
	String email;
	String webpage;
	Timestamp timestamp;
	
	public LivingUser(){
		name = "";
		lastname = "";
		gender = "";
		email = "";
		webpage = "";
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setLastname(String lastname){
		this.lastname = lastname;
	}
	
	public void setGender(String gender){
		this.gender = gender;
	}
	
	public void setTimestamp(Timestamp timestamp){
		this.timestamp = timestamp;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public void setWebsite(String webpage){
		this.webpage = webpage;
	}
	
	public String getName(){
		return name;
	}
	
	public String getLastame(){
		return lastname;
	}
	
	public String getGender(){
		return gender;
	}
	
	public String getEmail(){
		return email;
	}
	
	public String getWebpage(){
		return webpage;
	}
	
	public Date getTimestamp(){
		return timestamp;
	}

}
