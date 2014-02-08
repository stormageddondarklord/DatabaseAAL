package agents.beans;

import java.io.Serializable;
import java.util.ArrayList;

import mails.MailReceiver;
import mails.eMailAcc;
import messages.CalendarEntries;
import messages.Mails;
import messages.UserID;
import objects.Mail;

import org.sercho.masp.space.event.SpaceEvent;
import org.sercho.masp.space.event.SpaceObserver;
import org.sercho.masp.space.event.WriteCallEvent;

import calendar.Entry;
import calendar.GoogleCalendarFetcher;
import de.dailab.jiactng.agentcore.AbstractAgentBean;
import de.dailab.jiactng.agentcore.action.Action;
import de.dailab.jiactng.agentcore.comm.ICommunicationBean;
import de.dailab.jiactng.agentcore.comm.message.IJiacMessage;
import de.dailab.jiactng.agentcore.comm.message.JiacMessage;
import de.dailab.jiactng.agentcore.knowledge.IFact;
import de.dailab.jiactng.agentcore.ontology.IActionDescription;

public class CalendarBean extends AbstractAgentBean{
	
private IActionDescription sendAction = null;
	
	@Override
	public void doStart() throws Exception{
		super.doStart();
		log.info("InformationAgent started.");
		
		IActionDescription template = new Action(ICommunicationBean.ACTION_SEND);
		sendAction = memory.read(template);

		if(sendAction == null){
			sendAction = thisAgent.searchAction(template);
		}
		
		if(sendAction == null){
			throw new RuntimeException("Send action not found.");
		}
		
		memory.attach(new MessageObserver(), new JiacMessage());
	}
	
	@Override
	public void execute(){
		
	}
	
	private class MessageObserver implements SpaceObserver<IFact>{

		/**
		 * 
		 */
		private static final long serialVersionUID = -8182513339144469591L;

		@Override
		public void notify(SpaceEvent<? extends IFact> event) {
			
			ArrayList<Entry> entries = null;
			
			if(event instanceof WriteCallEvent<?>){
				WriteCallEvent<IJiacMessage> wce = (WriteCallEvent<IJiacMessage>) event;
				
				log.info("NewsfeedAgent - message received");
				
				IJiacMessage message = memory.remove(wce.getObject());
				IFact obj = message.getPayload();
				

				// which instance??? todo
				if(obj instanceof){
									
					try {
						
						GoogleCalendarFetcher fetch = new GoogleCalendarFetcher("", "");
						entries = fetch.getEventEntries();

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				if(entries == null){
					throw new RuntimeException("No Mails found.");
				}
				
				JiacMessage sendMessage = new JiacMessage(new CalendarEntries(entries));
				
				invoke(sendAction, new Serializable[]{sendMessage, message.getSender()});
			}
			
		}
		
	}

}
