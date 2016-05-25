package vn.app.phims14.Database;

/**
 * Created by Minh on 4/21/2016.
 */
public class MovieHomeDAO {
    //IDMovie,ImageBanner,NameViet,Quality,Point
    String IDMovie;
    String ImageBanner;
    String Image;
    String NameViet;
    String Quality;
    String Point;
    String ID;
    String PointIMDB;

    public MovieHomeDAO(String[] input, int type) {
        switch (type) {
            case 0: {
                setIDMovie(input[0]);
                setImageBanner(input[1]);
                setNameViet(input[2]);
                setQuality(input[3]);
                setPoint(input[4]);
                setID(input[5]);
                setPointIMDB(input[6]);
            }
            break;
            case 1: {
                setIDMovie(input[0]);
                setImage(input[1]);
                setNameViet(input[2]);
                setQuality(input[3]);
                setPoint(input[4]);
                setID(input[5]);
                setPointIMDB(input[6]);
            }
            break;
        }
    }

    public String getPointIMDB() {
        return PointIMDB;
    }

    public void setPointIMDB(String pointIMDB) {
        PointIMDB = pointIMDB;
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

    public String getImageBanner() {
        return ImageBanner;
    }

    public void setImageBanner(String imageBanner) {
        ImageBanner = imageBanner;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getNameViet() {
        return NameViet;
    }

    public void setNameViet(String nameViet) {
        NameViet = nameViet;
    }

    public String getQuality() {
        return Quality;
    }

    public void setQuality(String quality) {
        Quality = quality;
    }

    public String getPoint() {
        return Point;
    }

    public void setPoint(String point) {
        Point = point;
    }
}
