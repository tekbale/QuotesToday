package quotestoday.tek.com.quotestoday.rssparser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class RssParser {
	private URL url;

	public RssParser(String url) {
		try {
			this.url = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public RssFeed parseRss() {
		RssFeed rssFeed = null;
		RssItem item = null;
		try {
			XmlPullParserFactory inputFactory = XmlPullParserFactory.newInstance();
			InputStream inputStream = this.url.openStream();
			XmlPullParser eventReader = inputFactory.newPullParser();
			eventReader.setInput(url.openStream(),"UTF-8");
			boolean newItem=false;
			int event = eventReader.getEventType();
			while(event!=XmlPullParser.END_DOCUMENT) {

				if(event==XmlPullParser.START_TAG) {
					String localPart = eventReader.getName();
					//System.out.println("<"+localPart+">");
					switch(localPart) {
						case "channel":
							rssFeed = new RssFeed();
							break;
						
						case "item":
							item = new RssItem();
							newItem=true;
							break;
							
						case "title":
							if(newItem) {
								eventReader.next();
								item.setTitle(eventReader.getText());
								//System.out.println("\t\t"+item.getTitle());
							}
							break;
							
						case "link":
							if(newItem) {
								eventReader.next();
								item.setLink(eventReader.getText());
							}
							break;
							
						case "description":
							if(newItem) {
								eventReader.next();
								item.setDesc(eventReader.getText());
							}
							break;
							
						case "pubDate":
							if(newItem) {
								eventReader.next();
								item.setPubDate(eventReader.getText());
							}
							break;
							
						case "category":
							if(newItem) {
								eventReader.next();
								item.setCategory(eventReader.getText());
							}
							break;
							
						default:
							break;
					}
				} else if(event==XmlPullParser.END_TAG) {
					String localPart = eventReader.getName();
					//System.out.println("</"+localPart+">");
					
					switch(localPart) {
						case "item":
							rssFeed.getItems().add(item);
							newItem=false;
							break;
						
						default:
							break;
					}
				}

				event=eventReader.next();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}

		return rssFeed;
	}


	/*private String getNodeData(XMLEvent event, XMLEventReader eventReader) throws XMLStreamException {
		String res="";
		event = eventReader.nextEvent();
		if(event instanceof Characters)
			res = event.asCharacters().getData();
		return res;
	}*/
}
