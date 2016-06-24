package vn.app.phims14.Classes;

/**
 * Created by khuong.man on 5/16/2016.
 */
public class MovieEpisode {
    private String movieName;
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

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public MovieEpisode(String name, String url, String movieName) {
        this.movieName = movieName;
        this.name = name;
        this.url = url;
    }
}
