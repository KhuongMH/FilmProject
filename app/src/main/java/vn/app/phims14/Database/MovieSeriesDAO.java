package vn.app.phims14.Database;

/**
 * Created by Minh on 4/1/2016.
 */
public class MovieSeriesDAO {
    String ID;
    String IDMovie;
    String Link;
    String Format;
    String Episode;

    public MovieSeriesDAO() {

    }

    public MovieSeriesDAO(String[] input) {
        setID(input[0]);
        setIDMovie(input[1]);
        setLink(input[2]);
        setFormat(input[3]);
        setEpisode(input[4]);
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getIDMovie() {
        return IDMovie;
    }

    public void setIDMovie(String IDMovie) {
        this.IDMovie = IDMovie;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getFormat() {
        return Format;
    }

    public void setFormat(String format) {
        Format = format;
    }

    public String getEpisode() {
        return Episode;
    }

    public void setEpisode(String episode) {
        Episode = episode;
    }
}
