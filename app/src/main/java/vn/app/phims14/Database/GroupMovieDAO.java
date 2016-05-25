package vn.app.phims14.Database;

import java.util.ArrayList;

/**
 * Created by Minh on 4/8/2016.
 */
public class GroupMovieDAO {
    String title;
    ArrayList<MovieHomeDAO> movieDAOs;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<MovieHomeDAO> getMovieDAOs() {
        return movieDAOs;
    }

    public void setMovieDAOs(ArrayList<MovieHomeDAO> movieDAOs) {
        this.movieDAOs = movieDAOs;
    }
}
