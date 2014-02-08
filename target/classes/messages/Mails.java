package messages;

import java.util.ArrayList;

import objects.Mail;
import de.dailab.jiactng.agentcore.knowledge.IFact;

public class Mails implements IFact{
	
	private ArrayList<Mail> mails;
	
	public Mails(ArrayList<Mail> mails){
		this.mails = mails;
	}
	
	public ArrayList<Mail> getMails(){
		return mails;
	}
	
	public void setMails(ArrayList<Mail> mails){
		this.mails = mails;
	}

}
