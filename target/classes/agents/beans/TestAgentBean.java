package agents.beans;

import java.io.Serializable;
import java.util.List;

import messages.UserID;
import access.MySQLAccess;
import de.dailab.jiactng.agentcore.AbstractAgentBean;
import de.dailab.jiactng.agentcore.action.Action;
import de.dailab.jiactng.agentcore.comm.ICommunicationBean;
import de.dailab.jiactng.agentcore.comm.IMessageBoxAddress;
import de.dailab.jiactng.agentcore.comm.message.JiacMessage;
import de.dailab.jiactng.agentcore.ontology.AgentDescription;
import de.dailab.jiactng.agentcore.ontology.IActionDescription;
import de.dailab.jiactng.agentcore.ontology.IAgentDescription;

public class TestAgentBean extends AbstractAgentBean{
	
	private IActionDescription sendAction = null;
	private long id = 30;
	private String accessToken = "CAADAoXWfzBQBAHmn8ZBUd1do1zCI7eRA2UrN2dg5OLJ4yVHjT…UnALSXZCpEGwEsmOtnG5qxYPyXh2wv4DOfcYxx6ewDjZCr3oX";
	
	@Override
	public void doStart() throws Exception{
		super.doStart();
		log.info("TestAgent started.");
		
		IActionDescription template = new Action(ICommunicationBean.ACTION_SEND);
		sendAction = memory.read(template);

		if(sendAction == null){
			sendAction = thisAgent.searchAction(template);
		}
		
		if(sendAction == null){
			throw new RuntimeException("Send action not found.");
		}
	}
	
	@Override
	public void execute(){

		List<IAgentDescription> agentDescriptions = thisAgent.searchAllAgents(new AgentDescription());

		for(IAgentDescription agent : agentDescriptions){
			if(agent.getName().equals("SocialMediaAgent")){

				JiacMessage message =  new JiacMessage(new UserID(id, accessToken));
				IMessageBoxAddress receiver = agent.getMessageBoxAddress();

				
				log.info("TestAgent - sending UserID to: " + receiver);
				invoke(sendAction, new Serializable[] {message, receiver});
			}
		}
	}

}
