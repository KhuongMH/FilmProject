package vn.app.phims14.Database;

import java.util.ArrayList;
import java.util.List;

import vn.app.phims14.Classes.MovieEpisode;

/**
 * Created by Minh on 4/12/2016.
 */
public class ItemPartVideo {
    String title;
    List<MovieEpisode> linkEpisodes;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<MovieEpisode> getLinkEpisodes() {
        return linkEpisodes;
    }

    public void setLinkEpisodes(List<MovieEpisode> linkEpisodes) {
        this.linkEpisodes = linkEpisodes;
    }

    public ItemPartVideo() {

    }
}
