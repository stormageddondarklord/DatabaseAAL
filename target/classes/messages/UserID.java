package messages;

import de.dailab.jiactng.agentcore.knowledge.IFact;

public class UserID implements IFact{
	
	private long id;
	private String accessToken;
	
	public UserID(long id, String access){
		this.id = id;
		this.accessToken = access;
	}
	
	public UserID() {
		// TODO Auto-generated constructor stub
	}

	public void setID(long id){
		this.id = id;
	}
	
	public long getID(){
		return this.id;
	}
	
	public void setAccessToken(String access){
		this.accessToken = access;
	}
	
	public String getAccessToken(){
		return this.accessToken;
	}

}
