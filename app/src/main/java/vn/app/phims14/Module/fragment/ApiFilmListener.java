package vn.app.phims14.Module.fragment;

import java.util.ArrayList;

/**
 * Created by Minh on 4/4/2016.
 */
public interface ApiFilmListener<T> {
    public void loadContentView(int type, ArrayList<T> data);
}
