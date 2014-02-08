package messages;

import java.util.List;

import objects.NewsFeedMessage;
import de.dailab.jiactng.agentcore.knowledge.IFact;

public class NewsFeed implements IFact{
	
	List<NewsFeedMessage> news;
	
	public NewsFeed(List<NewsFeedMessage> news){
		this.news = news;
	}
	
	public List<NewsFeedMessage> getNewsFeed(){
		return news;
	}
	
	public void setNewsFeed(List<NewsFeedMessage> news){
		this.news = news;
	}

}
