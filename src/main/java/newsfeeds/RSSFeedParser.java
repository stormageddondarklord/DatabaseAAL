package newsfeeds;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;




import objects.Feed;
import objects.NewsFeedMessage;

import org.xml.sax.helpers.AttributesImpl;

public class RSSFeedParser {
	static final String TITLE = "title";
	  static final String DESCRIPTION = "description";
	  static final String CHANNEL = "channel";
	  static final String LANGUAGE = "language";
	  static final String COPYRIGHT = "copyright";
	  static final String LINK = "link";
	  //static final String AUTHOR = "author";
	  static final String ITEM = "item";
	  static final String PUB_DATE = "pubDate";
	  //static final String GUID = "guid";
	  static final String ENCLOSURE = "enclosure";

	  final URL url;

	  public RSSFeedParser(String feedUrl) {
	    try {
	      this.url = new URL(feedUrl);
	    } catch (MalformedURLException e) {
	      throw new RuntimeException(e);
	    }
	  }

	  
	public Feed readFeed() {
	    Feed feed = null;
	    try {
	      boolean isFeedHeader = true;
	      // Set header values intial to the empty string
	      String description = "";
	      String title = "";
	      String link = "";
	      String language = "";
	      String copyright = "";
//	      String author = "";
	      String pubdate = "";
//	      String guid = "";
	      String enclosure = "";
//	      URL url;
	      
	      

	      // First create a new XMLInputFactory
	      XMLInputFactory inputFactory = XMLInputFactory.newInstance();
	      // Setup a new eventReader
	      InputStream in = read();
	      XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
	      // read the XML document
	      while (eventReader.hasNext()) {
	        XMLEvent event = eventReader.nextEvent();
	        if (event.isStartElement()) {
	          String localPart = event.asStartElement().getName()
	              .getLocalPart();
	          switch (localPart) {
	          case ITEM:
	            if (isFeedHeader) {
	              isFeedHeader = false;
	              feed = new Feed(title, link, description, language,
	                  copyright, pubdate);
	            }
	            event = eventReader.nextEvent();
	            break;
	          case TITLE:
	            title = getCharacterData(event, eventReader);
	            break;
	          case DESCRIPTION:
	            description = getCharacterData(event, eventReader);
	            break;
	          case LINK:
	            link = getCharacterData(event, eventReader);
	            break;
//	          case GUID:
//	            guid = getCharacterData(event, eventReader);
//	            break;
	          case LANGUAGE:
	            language = getCharacterData(event, eventReader);
	            break;
//	          case AUTHOR:
//	            author = getCharacterData(event, eventReader);
//	            break;
	          case PUB_DATE:
	            pubdate = getCharacterData(event, eventReader);
	            break;
	          case COPYRIGHT:
	            copyright = getCharacterData(event, eventReader);
	            break;
	          case ENCLOSURE:
	        	enclosure = event.toString();
	        	enclosure = enclosure.substring(enclosure.indexOf("http"), enclosure.length()-2);
	        	event = eventReader.nextEvent();
	        	break;
	        	
	          
	          }
	        } else if (event.isEndElement()) {
	          if (event.asEndElement().getName().getLocalPart() == (ITEM)) {
	            NewsFeedMessage message = new NewsFeedMessage();
	            message.setDescription(description);
//	            message.setGuid(guid);
	            message.setLink(link);
	            message.setTitle(title);
	            message.setEnclosure(enclosure);
	            feed.getMessages().add(message);
	            event = eventReader.nextEvent();
	            continue;
	            
	          }
	        }
	      }
	    } catch (XMLStreamException e) {
	      throw new RuntimeException(e);
	    }
	    return feed;
	  }

	  private String getCharacterData(XMLEvent event, XMLEventReader eventReader)
	      throws XMLStreamException {
	    String result = "";
	    event = eventReader.nextEvent();
	    
	    if (event instanceof Characters) {
	      result = event.asCharacters().getData();
	    }
	    return result;
	    
	  }
	  
	  
	  
	  
	  
	  	

	  private InputStream read() {
	    try {
	      return url.openStream();
	    } catch (IOException e) {
	      throw new RuntimeException(e);
	    }
	  }
}
	
		  
