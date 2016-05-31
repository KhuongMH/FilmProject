package vn.app.phims14.Module;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import org.askerov.dynamicgrid.DynamicGridView;

import java.util.ArrayList;
import java.util.List;

import uk.co.ribot.easyadapter.EasyAdapter;
import vn.app.phims14.Classes.GlobalVariable;
import vn.app.phims14.Classes.Movie;
import vn.app.phims14.Module.fragment.MovieAdapter;
import vn.app.phims14.R;

/**
 * Created by khuong.man on 5/31/2016.
 */
public class NewMoviesActivity extends Activity {
    DynamicGridView gridView;
    EditText et_search;
    TextView tv_title;
    EasyAdapter<Movie> movieAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_video_activity);
        et_search = (EditText) findViewById(R.id.et_search);
        gridView = (DynamicGridView) findViewById(R.id.dynamic_grid);
        tv_title = (TextView) findViewById(R.id.tv_title);
        movieAdapter = new EasyAdapter<>(getApplication(), MovieAdapter.class, GlobalVariable.newMovies);
        gridView.setAdapter(movieAdapter);
        GlobalVariable.newMovies.clear();
    }
}
