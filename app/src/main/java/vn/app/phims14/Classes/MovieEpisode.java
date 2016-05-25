package vn.app.phims14.Classes;

/**
 * Created by khuong.man on 5/16/2016.
 */
public class MovieEpisode {
    private String name;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public MovieEpisode() {
    }

    public MovieEpisode(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
