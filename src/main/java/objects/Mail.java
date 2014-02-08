package objects;

import java.util.Date;

import de.dailab.jiactng.agentcore.knowledge.IFact;

public class Mail implements IFact{

	private String subject;
	private String content;
	private String type;
	private String from;
	private Date received;
	
	public Mail(String subject, String content,String type, String from, Date received){
		setSubject(subject);
		setContent(content);
		setType(type);
		setFrom(from);
		setReceived(received);
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public Date getReceived() {
		return received;
	}

	public void setReceived(Date received) {
		this.received = received;
	}
	
	@Override
	public String toString(){
		return   "Subject: "+subject+" \n"
				+"---------------------------------\n"
				+"MsgType: "+type+"\n"
				+"Content: "+content+"\n"
				+"---------------------------------\n"
				+"Msg from: "+from+"\n"
				+"Received: "+received.toString()+"\n";
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
