package vn.app.phims14.Database;

/**
 * Created by Minh on 4/1/2016.
 */
public class MenuDAO {
    String ID;
    String IDCompany;
    String IDWebsite;
    String Name;
    String Icon;
    String mClass;
    String Urlfile;
    String TT;
    String IDSup;

    public MenuDAO(String[] input){
        setID(input[0]);
        setIDCompany(input[1]);
        setIDWebsite(input[2]);
        setName(input[3]);
        setIcon(input[4]);
        setmClass(input[5]);
        setUrlfile(input[6]);
        setTT(input[7]);
        setIDSup(input[8]);
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getIDCompany() {
        return IDCompany;
    }

    public void setIDCompany(String IDCompany) {
        this.IDCompany = IDCompany;
    }

    public String getIDWebsite() {
        return IDWebsite;
    }

    public void setIDWebsite(String IDWebsite) {
        this.IDWebsite = IDWebsite;
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

    public String getmClass() {
        return mClass;
    }

    public void setmClass(String aClass) {
        mClass = aClass;
    }

    public String getUrlfile() {
        return Urlfile;
    }

    public void setUrlfile(String urlfile) {
        Urlfile = urlfile;
    }

    public String getTT() {
        return TT;
    }

    public void setTT(String TT) {
        this.TT = TT;
    }

    public String getIDSup() {
        return IDSup;
    }

    public void setIDSup(String IDSup) {
        this.IDSup = IDSup;
    }
}
