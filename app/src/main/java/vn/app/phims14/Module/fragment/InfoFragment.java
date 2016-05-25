package vn.app.phims14.Module.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import org.jsoup.Jsoup;

import java.util.ArrayList;

import it.sephiroth.android.library.widget.HListView;
import uk.co.ribot.easyadapter.EasyAdapter;
import vn.app.phims14.Classes.Actor;
import vn.app.phims14.Classes.MovieInfo;
import vn.app.phims14.R;

/**
 * Created by Minh on 4/12/2016.
 */
@SuppressLint("ValidFragment")
public class InfoFragment extends BaseFragment {

    static final String TAG = InfoFragment.class.getName();

    TextView tvFilmTitle;
    TextView tvFilmStatus;
    TextView tvFilmAuthor;
    TextView tvFilmCountry;
    TextView tvFilmYear;
    ImageView ivFacebook;
    ImageView ivGoogle;
    ImageView ivThumb;
    TextView ivName;
    HListView lvActor;
    TextView tvDescription;

    MovieInfo info;
    EasyAdapter<Actor> personDAOEasyAdapter;

    public InfoFragment() {

    }

    public InfoFragment(MovieInfo info) {
        this.info = info;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater
                .inflate(R.layout.info_fragment, container, false);
        lvActor = (HListView) view.findViewById(R.id.lv_actor);
        tvFilmTitle = (TextView) view.findViewById(R.id.tv_film_title);
        tvFilmStatus = (TextView) view.findViewById(R.id.tv_film_status);
        tvFilmAuthor = (TextView) view.findViewById(R.id.tv_film_author);
        tvFilmCountry = (TextView) view.findViewById(R.id.tv_film_country);
        tvFilmYear = (TextView) view.findViewById(R.id.tv_film_year);
        tvDescription = (TextView) view.findViewById(R.id.tv_description);


        tvFilmTitle.setText(info.getTitle());
        tvFilmCountry.setText(String.valueOf(info.getCountry()));
        tvFilmStatus.setText(String.valueOf(info.getDuration()));
        tvFilmAuthor.setText(String.valueOf(info.getCategories()));
        tvFilmYear.setText(String.valueOf(info.getYear()));
        String[] tmp = Jsoup.parse(info.getDescription()).text().split(":");
        String description = "";
        for (int i = 1; i < tmp.length; i++) {
            description += tmp[i];
        }
        tvDescription.setText(description);

        personDAOEasyAdapter = new EasyAdapter<Actor>(getContext(), ActorAdapter.class, info.getActors());
        lvActor.setAdapter(personDAOEasyAdapter);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
