package vn.app.phims14.Database;

/**
 * Created by Minh on 4/1/2016.
 */
public class CategoryDAO {
    String ID = "";
    String Name = "";
    String Icon = "";

    public CategoryDAO(String[] input) {
        setIcon(input[0]);
        setName(input[1]);
        setID(input[2]);
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String icon) {
        Icon = icon;
    }
}
