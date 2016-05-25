package vn.app.phims14.Classes;

/**
 * Created by khuong.man on 5/13/2016.
 */
public class Movie {
    private String url;
    private String title;
    private String rate;
    private String idMovie;
    private String idYoutube;

    public Movie() {
    }

    public Movie(String url, String title, String rate, String idMovie, String idYoutube) {
        this.url = url;
        this.title = title;
        this.rate = rate;
        this.idMovie = idMovie;
        this.idYoutube = idYoutube;
    }

    public String getIdYoutube() {
        return idYoutube;
    }

    public void setIdYoutube(String idYoutube) {
        this.idYoutube = idYoutube;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(String idMovie) {
        this.idMovie = idMovie;
    }
}
