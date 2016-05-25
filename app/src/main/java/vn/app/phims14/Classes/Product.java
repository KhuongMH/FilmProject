package vn.app.phims14.Classes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khuong.man on 5/13/2016.
 */
public class Product {
    private String title ="";

    private List<Movie> movies = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public Product() {

    }

    public Product(String title) {
        this.title = title;
    }
}
