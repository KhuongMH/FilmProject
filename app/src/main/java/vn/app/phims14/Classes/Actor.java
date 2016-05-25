package vn.app.phims14.Classes;

/**
 * Created by khuong.man on 5/16/2016.
 */
public class Actor {
    private String id;
    private String name;
    private String birthday;
    private String image = "";
    private String height;
    private String slug;
    private String weight;
    private String jobID;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public Actor(String id, String name, String birthday, String image, String height, String slug, String weight, String jobID) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.image = image;
        this.height = height;
        this.slug = slug;
        this.weight = weight;
        this.jobID = jobID;
    }

    public Actor() {

    }
}
