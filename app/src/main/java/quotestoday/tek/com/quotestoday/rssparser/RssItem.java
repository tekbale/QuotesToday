package quotestoday.tek.com.quotestoday.rssparser;

public class RssItem {
	private String title;
	private String link;
	private String desc;
	private String pubDate;
	private String category; /* for future expansion */

	public RssItem() {
	}

	public RssItem(String title, String link, String desc, String pubDate, String category) {
		super();
		this.title = title;
		this.link = link;
		this.desc = desc;
		this.pubDate = pubDate;
		this.category = category;
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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "RssItem [title=" + title + ",\n link=" + link + ",\n desc=" + desc + ",\n pubDate=" + pubDate + ",\n category="
				+ category + "]\n\n";
	}
}
