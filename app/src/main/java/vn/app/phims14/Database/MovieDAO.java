package vn.app.phims14.Database;

/**
 * Created by Minh on 4/1/2016.
 */
public class MovieDAO {
    String ID="";
    String IDMovie="";
    String Name="";
    String NameViet="";
    String IDState ="";
    String Point="";
    String PointIMDB="";
    String Quality="";
    String NumberRating ="";
    String Image="";
    String IDProducer="";
    String IDCountry="";
    String IDActor="";
    String Year="";
    String Date="";
    String Duration="";
    String IDLanguage ="";
    String Company ="";
    String IDCategory="";
    String Trailer="";
    String NumberLikeFacebook="";
    String NumberShareFacebook="";
    String NumberLikeTweeter="";
    String NumberShareTweeter="";
    String NumberLikeGoogle="";
    String NumberShareGoolge="";
    String Content="";
    String NumberEpisode="";
    String Tag="";
    String ImageBanner="";
    String Banner="";
    String NumberView="";
    String NumberComment="";

    public MovieDAO() {

    }

    public MovieDAO(String[] input) {
            setID(input[0]);
            setIDMovie(input[1]);
            setName(input[2]);
            setNameViet(input[3]);
            setIDState(input[4]);
            setPoint(input[5]);
            setPointIMDB(input[6]);
            setQuality(input[7]);
            setNumberRating(input[8]);
            setImage(input[9]);
            setIDProducer(input[10]);
            setIDCountry(input[11]);
            setIDActor(input[12]);
            setYear(input[13]);
            setDate(input[14]);
            setDuration(input[15]);
            setIDLanguage(input[16]);
            setCompany(input[17]);
            setIDCategory(input[18]);
            setTrailer(input[19]);
            setNumberLikeFacebook(input[20]);
            setNumberShareFacebook(input[21]);
            setNumberLikeTweeter(input[22]);
            setNumberShareTweeter(input[23]);
            setNumberLikeGoogle(input[24]);
            setNumberShareGoolge(input[25]);
            setContent(input[26]);
            setNumberEpisode(input[27]);
            setTag(input[28]);
            setImageBanner(input[29]);
            setBanner(input[30]);
            setNumberView(input[31]);
            setNumberComment(input[32]);
    }

    public String getImageBanner() {
        return ImageBanner;
    }

    public void setImageBanner(String imageBanner) {
        ImageBanner = imageBanner;
    }

    public String getBanner() {
        return Banner;
    }

    public void setBanner(String banner) {
        Banner = banner;
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

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNameViet() {
        return NameViet;
    }

    public void setNameViet(String nameViet) {
        NameViet = nameViet;
    }

    public String getIDState() {
        return IDState;
    }

    public void setIDState(String IDState) {
        this.IDState = IDState;
    }

    public String getPoint() {
        return Point;
    }

    public void setPoint(String point) {
        Point = point;
    }

    public String getPointIMDB() {
        return PointIMDB;
    }

    public void setPointIMDB(String pointIMDB) {
        PointIMDB = pointIMDB;
    }

    public String getQuality() {
        return Quality;
    }

    public void setQuality(String quality) {
        Quality = quality;
    }

    public String getNumberRating() {
        return NumberRating;
    }

    public void setNumberRating(String numberRating) {
        NumberRating = numberRating;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getIDProducer() {
        return IDProducer;
    }

    public void setIDProducer(String IDProducer) {
        this.IDProducer = IDProducer;
    }

    public String getIDCountry() {
        return IDCountry;
    }

    public void setIDCountry(String IDCountry) {
        this.IDCountry = IDCountry;
    }

    public String getIDActor() {
        return IDActor;
    }

    public void setIDActor(String IDActor) {
        this.IDActor = IDActor;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getIDLanguage() {
        return IDLanguage;
    }

    public void setIDLanguage(String IDLanguage) {
        this.IDLanguage = IDLanguage;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        this.Company = company;
    }

    public String getIDCategory() {
        return IDCategory;
    }

    public void setIDCategory(String IDCategory) {
        this.IDCategory = IDCategory;
    }

    public String getTrailer() {
        return Trailer;
    }

    public void setTrailer(String trailer) {
        Trailer = trailer;
    }

    public String getNumberView() {
        return NumberView;
    }

    public void setNumberView(String numberView) {
        NumberView = numberView;
    }

    public String getNumberComment() {
        return NumberComment;
    }

    public void setNumberComment(String numberComment) {
        NumberComment = numberComment;
    }

    public String getNumberLikeFacebook() {
        return NumberLikeFacebook;
    }

    public void setNumberLikeFacebook(String numberLikeFacebook) {
        NumberLikeFacebook = numberLikeFacebook;
    }

    public String getNumberShareFacebook() {
        return NumberShareFacebook;
    }

    public void setNumberShareFacebook(String numberShareFacebook) {
        NumberShareFacebook = numberShareFacebook;
    }

    public String getNumberLikeTweeter() {
        return NumberLikeTweeter;
    }

    public void setNumberLikeTweeter(String numberLikeTweeter) {
        NumberLikeTweeter = numberLikeTweeter;
    }

    public String getNumberShareTweeter() {
        return NumberShareTweeter;
    }

    public void setNumberShareTweeter(String numberShareTweeter) {
        NumberShareTweeter = numberShareTweeter;
    }

    public String getNumberLikeGoogle() {
        return NumberLikeGoogle;
    }

    public void setNumberLikeGoogle(String numberLikeGoogle) {
        NumberLikeGoogle = numberLikeGoogle;
    }

    public String getNumberShareGoolge() {
        return NumberShareGoolge;
    }

    public void setNumberShareGoolge(String numberShareGoolge) {
        NumberShareGoolge = numberShareGoolge;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getNumberEpisode() {
        return NumberEpisode;
    }

    public void setNumberEpisode(String numberEpisode) {
        NumberEpisode = numberEpisode;
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }
}
