package vn.app.phims14.Database;

/**
 * Created by Minh on 4/1/2016.
 */
public class StateDAO {
    String ID;
    String Name;

    public StateDAO(String[] input){
        setID(input[0]);
        setName(input[1]);
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
}
