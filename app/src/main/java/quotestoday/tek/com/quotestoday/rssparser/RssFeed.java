package quotestoday.tek.com.quotestoday.rssparser;

import java.util.ArrayList;
import java.util.List;

public class RssFeed {
	private String title;
	private String link;
	private String description;
	private String lang;
	private String copyright;
	private String pubDate;

	private List<RssItem> items = new ArrayList<>();

	public RssFeed() {
	}

	public RssFeed(String title, String link, String description, String lang, String copyright, String pubDate,
			List<RssItem> items) {
		super();
		this.title = title;
		this.link = link;
		this.description = description;
		this.lang = lang;
		this.copyright = copyright;
		this.pubDate = pubDate;
		this.items = items;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public List<RssItem> getItems() {
		return items;
	}

	public void setItems(List<RssItem> items) {
		this.items = items;
	}
}
