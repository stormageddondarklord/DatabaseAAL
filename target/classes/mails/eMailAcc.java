package mails;

public class eMailAcc {

	private String emailAdd;
	private String userName;
	private String password;
	
	public eMailAcc(String email, String userName, String pword){
		this.setEmailAdd(email);
		this.setPassword(pword);
		this.setUserName(userName);
	}

	public eMailAcc(String email, String pword){
		this.setEmailAdd(email);
		this.setPassword(pword);
		this.setUserName(null);
		
	}
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUserName() {
		if (userName == null) return emailAdd;
		else return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmailAdd() {
		return emailAdd;
	}

	public void setEmailAdd(String emailAdd) {
		this.emailAdd = emailAdd;
	}
}
