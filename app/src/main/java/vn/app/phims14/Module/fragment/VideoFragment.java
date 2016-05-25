package vn.app.phims14.Module.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.linearlistview.LinearListView;

import java.util.ArrayList;

import it.sephiroth.android.library.widget.AbsHListView;
import it.sephiroth.android.library.widget.HListView;
import uk.co.ribot.easyadapter.EasyAdapter;
import vn.app.phims14.Classes.Movie;
import vn.app.phims14.Classes.MovieInfo;
import vn.app.phims14.Classes.MovieServer;
import vn.app.phims14.Database.MovieDAO;
import vn.app.phims14.Database.MovieHomeDAO;
import vn.app.phims14.R;

/**
 * Created by Minh on 4/12/2016.
 */
@SuppressLint("ValidFragment")
public class VideoFragment extends BaseFragment {
    ImageView ivThumb;
    TextView ivName;
    LinearListView lvServer;
    HListView lvRelatedFilm;
    ScrollView sc_main;
    EasyAdapter<MovieServer> movieSeriesAdapter;
    EasyAdapter<Movie> movieAdapter;

    MovieInfo info;

    public VideoFragment(MovieInfo info) {
        this.info = info;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater
                .inflate(R.layout.video_fragment, container, false);
        ivThumb = (ImageView) view.findViewById(R.id.iv_thumb);
        ivName = (TextView) view.findViewById(R.id.iv_name);
        lvServer = (LinearListView) view.findViewById(R.id.lv_server);
        lvRelatedFilm = (HListView) view.findViewById(R.id.lv_related_film);
        sc_main = (ScrollView) view.findViewById(R.id.sc_main);

        movieAdapter = new EasyAdapter<>(getActivity(), MovieAdapter.class, info.getRelateMovies());
        lvRelatedFilm.setAdapter(movieAdapter);

        movieSeriesAdapter = new EasyAdapter<>(getActivity(), MovieServerAdapder.class, info.getMovieServers());
        lvServer.setAdapter(movieSeriesAdapter);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
