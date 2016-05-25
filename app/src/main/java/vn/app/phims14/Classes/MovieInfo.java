package vn.app.phims14.Classes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khuong.man on 5/16/2016.
 */
public class MovieInfo {
    private String id;
    private String categories;
    private String title;
    private String view;
    private String year;
    private String productCategory;
    private String duration;
    private String description;
    private String rate;
    private String country;
    private String banner;
    private String urlRefFacebook;
    private int like = 0;
    private List<String> comments = new ArrayList<>();
    private List<MovieServer> movieServers = new ArrayList<>();
    private List<Actor> actors = new ArrayList<>();
    private List<Movie> relateMovies = new ArrayList<>();

    public String getUrlRefFacebook() {
        return urlRefFacebook;
    }

    public void setUrlRefFacebook(String urlRefFacebook) {
        this.urlRefFacebook = urlRefFacebook;
    }

    public List<Movie> getRelateMovies() {
        return relateMovies;
    }

    public void setRelateMovies(List<Movie> relateMovies) {
        this.relateMovies = relateMovies;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<MovieServer> getMovieServers() {
        return movieServers;
    }

    public void setMovieServers(List<MovieServer> movieServers) {
        this.movieServers = movieServers;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public MovieInfo() {

    }
}
