package vn.app.phims14.Database;

/**
 * Created by Minh on 4/1/2016.
 */
public class UserReponseDAO {

    private String ID;
    private String Email;
    private String Username;
    private String Password;
    private String Point;
    private String Image;

    public UserReponseDAO() {

    }

    public UserReponseDAO(String[] cols) {
        this.ID = cols[0];
        this.Email = cols[1];
        this.Username = cols[2];
        this.Password = cols[3];
        this.Point = cols[4];
        this.Image = cols[5];
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPoint() {
        return Point;
    }

    public void setPoint(String point) {
        Point = point;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
