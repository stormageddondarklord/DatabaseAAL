	package objects;

import java.net.URL;
import java.util.jar.Attributes;

import de.dailab.jiactng.agentcore.knowledge.IFact;

public class NewsFeedMessage{
	String title;
	String description;
	String link;
	String author;
	String guid;
	String enclosure;
    
	
	
	public String getTitle() {
	    return title;
	  }

	  public void setTitle(String title) {
	    this.title = title;
	  }

	  public String getDescription() {
	    return description;
	  }

	  public void setDescription(String description) {
	    this.description = description;
	  }

	  public String getLink() {
	    return link;
	  }

	  public void setLink(String link) {
	    this.link = link;
	  }
	  
//	  public String getAuthor() {
//		    return author;
//		  }
//
//	  public void setAuthor(String author) {
//		    this.author = author;
//		  }
//
//	  public String getGuid() {
//		    return guid;
//        }
//
//	  public void setGuid(String guid) {
//		    this.guid = guid;
//		  }
//		  
	  public String getEnclosure() {
	        return enclosure;
	    }

	    public void setEnclosure(String enclosure) {
	        this.enclosure = enclosure;
	    }

	  
	  
	  			



		  @Override
		  public String toString() {
		    return "FeedMessage [TITLE=" + title + ", DESCRIPTION=" + description
		        + ", LINK=" + link + ", ENCLOSURE="+ enclosure + "]";
		  }


}
