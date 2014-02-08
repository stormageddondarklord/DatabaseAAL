package newsfeeds;

import java.util.List;

import objects.Feed;
import objects.NewsFeedMessage;

public class FetchRSSFeed {
	
	public List<NewsFeedMessage> getRSSFeedWeltDE(){
		RSSFeedParser parser = new RSSFeedParser("http://www.welt.de/wirtschaft/?service=Rss");
    	Feed feed = parser.readFeed();
    	
    	return feed.getMessages();	
	}
	
		
    

}
//http://news.yahoo.com/rss/
//http://www.welt.de/vermischtes/?service=Rss