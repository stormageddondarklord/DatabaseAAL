package mails;

import java.util.Comparator;

import objects.Mail;

public class MailComparator implements Comparator<Mail>{
	@Override
	public int compare(Mail obj1, Mail obj2){
		return obj2.getReceived().compareTo(obj1.getReceived());
	}
}
