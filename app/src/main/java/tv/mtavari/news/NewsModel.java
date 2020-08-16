package tv.mtavari.news;

public class NewsModel {
    private String thumburl;
    private String url;
    private String title;
    private String description;
    private String category;
    private String time;

    public NewsModel(String thumburl, String url, String title, String description, String category, String time) {
        this.thumburl = thumburl;
        this.url = url;
        this.title = title;
        this.description = description;
        this.category = category;
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public String getTime() {
        return time;
    }

    public String getThumburl() {
        return thumburl;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
