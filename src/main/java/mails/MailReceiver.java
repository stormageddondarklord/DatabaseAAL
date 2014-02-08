package mails;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;

import objects.Mail;

public class MailReceiver {
	
//	public static void main (String args[]){
//		ArrayList<eMailAcc> accs = new ArrayList<eMailAcc>();
//		eMailAcc acc = new eMailAcc("email@beispiel.de", "password");
//		accs.add(acc);
//		MailReceiver receiver = new MailReceiver(accs);
//		ArrayList<Mail> mails = receiver.receiveMails();
//		Collections.sort(mails, new MailComparator());
//		for (int i=0; i<mails.size();i++){
//			System.out.println(mails.get(i).toString());
//		}
//		System.out.println(mails.get(1).toString());
//	}
	
	private ArrayList<eMailAcc> emailAccs;
	private ArrayList<Mail> emails;
	
	public MailReceiver (ArrayList<eMailAcc> emailAccs){
		this.emailAccs = emailAccs;
	}
	
	public ArrayList<Mail> receiveMails(){
		ArrayList<Mail> received = new ArrayList<Mail>();
		for (int i=0; i<emailAccs.size(); i++){
			eMailAcc current = emailAccs.get(i);
			String provider = getProvider(current.getEmailAdd());
			Properties props = new Properties();
			switch(provider){
				case "aol.com":
					props.put("mail.imaps.host", "imap.de.aol.com");
					received.addAll(getMails(current, props));break;
				case "aim.com": case "aol.de":
					props.put("mail.imaps.host", "imap.aim.com");
					received.addAll(getMails(current, props));break;
				case "gmail.com": case "googlemail.com":
					props.put("mail.imaps.host", "imap.gmail.com");
					props.put("mail.imaps.port", "993");
					received.addAll(getMails(current, props));break;
				case "gmx.net": case "gmx.de":
					props.put("mail.imaps.host", "imap.gmx.net");
					props.put("mail.imaps.port", "993");
					received.addAll(getMails(current, props));break;
				case "campus.tu-berlin.de": case "tu-berlin.de":
					props.put("mail.imaps.port", "993");
					props.put("mail.imaps.host", "mail.tu-berlin.de");
					received.addAll(getMails(current , props));break;
				case "web.de":
					props.put("mail.pop3s.host", "pop3.web.de");
					received.addAll(getMails(current, props));break;
				case "hotmail.de":
					props.put("mail.pop3s.host", "pop3.live.com");
					props.put("mail.pop3s.port", "995");
					received.addAll(getMails(current, props));break;
				default:
					System.err.println("Provider is not supported!");;
			}
		}
		Collections.sort(received, new MailComparator());

		return received;
		
	}
	
	private String getProvider(String addr){
		if (addr.contains("@")){
			return addr.substring(addr.indexOf("@")+1);
		} else {
			System.err.println("This is no E-Mail address!!");
			return addr;
		}
	}
	
	private ArrayList<Mail> getMails(eMailAcc acc,Properties properties){
		ArrayList<Mail> mails = new ArrayList<Mail>();
		try {  
			   Session session = Session.getInstance(properties, null);
			   Store store = null;
			   if (properties.getProperty("mail.imaps.host") != null){
					   store = session.getStore("imaps");
					   if (properties.getProperty("mail.imaps.port") == null){
						   store.connect(properties.getProperty("mail.imaps.host"), acc.getUserName(), acc.getPassword());
					   }else{
						   store.connect(properties.getProperty("mail.imaps.host"),
								   Integer.parseInt(properties.getProperty("mail.imaps.port")),acc.getUserName(), acc.getPassword());
					   }
				} else {
					store = session.getStore("pop3s");
					if (properties.getProperty("mail.pop3s.port") == null){
						   store.connect(properties.getProperty("mail.pop3s.host"), acc.getUserName(), acc.getPassword());
					   }else{
						   store.connect(properties.getProperty("mail.pop3s.host"),
								   Integer.parseInt(properties.getProperty("mail.pop3s.port")),acc.getUserName(), acc.getPassword());
					   }
				}
			   
			  
			   //3) create the folder object and open it  
//			   Folder emailFolder = emailStore.getFolder("INBOX");  
//			   Folder[] f = store.getDefaultFolder().list();
//			   for(Folder fd:f)
//				    System.out.println(">> "+fd.getName());
			   Folder emailFolder = store.getFolder("INBOX"); 
			   System.out.println(emailFolder.getUnreadMessageCount());
			   emailFolder.open(Folder.READ_ONLY);  
			  
			   //4) retrieve the messages from the folder in an array and print it  
			   Message[] messages = emailFolder.getMessages();  
//			   System.out.println(messages.length);
			   for (int i = 0; i < messages.length; i++) {  
			    Message message = messages[i];  
			    String msg = getText(message);
			    String type = message.getContentType();
			    mails.add(new Mail(message.getSubject(), msg, type,
			    		message.getFrom()[0].toString(),new Date(message.getSentDate().getTime())));
			   }  
			  
			   //5) close the store and folder objects  
			   emailFolder.close(false);  
			   store.close(); 
			  
			  } catch (NoSuchProviderException e) {e.printStackTrace();}   
			  catch (MessagingException e) {e.printStackTrace();}  	   
		return mails;
	}

	// Getter and setter
	public ArrayList<Mail> getEmails() {
		return emails;
	}

	public void setEmails(ArrayList<Mail> emails) {
		this.emails = emails;
	}
	
	private String getText(Part p){
		try {
			if (p.isMimeType("text/plain")) {
				return (String)p.getContent();
			}
			else if (p.isMimeType("text/html")) {
			     return (String)p.getContent();
			 } else if (p.isMimeType("multipart/alternative")) {
				 Multipart mp = (Multipart)p.getContent();
				 String text = null;
				 for (int i = 0; i < mp.getCount(); i++) {
		             Part bp = mp.getBodyPart(i);
		             if (bp.isMimeType("text/html")) {
		                 if (text == null)
		                     text = getText(bp);
		                 continue;
		             } else if (bp.isMimeType("text/plain")) {
		                 String s = getText(bp);
		                 if (s != null)
		                     return s;
		             } else {
		                 return getText(bp);
		             }
		         }
			 }
		} catch (MessagingException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
