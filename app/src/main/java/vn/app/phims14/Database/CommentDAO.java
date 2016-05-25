package vn.app.phims14.Database;

/**
 * Created by Minh on 4/7/2016.
 */
public class CommentDAO {
    String ID;
    String Date;
    String IDUser;
    String Content;
    String IDMovie;
    String IDParent;

    public CommentDAO() {

    }

    public CommentDAO(String[] cols) {
        this.ID = cols[0];
        this.Date = cols[1];
        this.IDUser = cols[2];
        this.Content = cols[3];
        this.IDMovie = cols[4];
        this.IDParent = cols[5];
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getIDUser() {
        return IDUser;
    }

    public void setIDUser(String IDUser) {
        this.IDUser = IDUser;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getIDMovie() {
        return IDMovie;
    }

    public void setIDMovie(String IDMovie) {
        this.IDMovie = IDMovie;
    }

    public String getIDParent() {
        return IDParent;
    }

    public void setIDParent(String IDParent) {
        this.IDParent = IDParent;
    }
}
