package vn.app.phims14.Classes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khuong.man on 5/16/2016.
 */
public class MovieServer {
    private String title;
    private List<MovieEpisode> movieEpisodes = new ArrayList<>();

    public List<MovieEpisode> getMovieEpisodes() {
        return movieEpisodes;
    }

    public void setMovieEpisodes(List<MovieEpisode> movieEpisodes) {
        this.movieEpisodes = movieEpisodes;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MovieServer(String title) {

        this.title = title;
    }

    public MovieServer() {
    }
}
