package vn.app.phims14.Classes;

/**
 * Created by khuong.man on 5/26/2016.
 */
public class UserAccount {
    private String id;
    private String name;
    private String avatarURL;

    public UserAccount() {
    }

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

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }
}
