package vn.app.phims14.Module;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.widget.LikeView;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import io.techery.properratingbar.ProperRatingBar;
import io.techery.properratingbar.RatingListener;
import vn.app.phims14.Classes.Actor;
import vn.app.phims14.Classes.Category;
import vn.app.phims14.Classes.GlobalVariable;
import vn.app.phims14.Classes.Movie;
import vn.app.phims14.Classes.MovieEpisode;
import vn.app.phims14.Classes.MovieInfo;
import vn.app.phims14.Classes.MovieServer;
import vn.app.phims14.Classes.Product;
import vn.app.phims14.Module.fragment.InfoFragment;
import vn.app.phims14.Module.fragment.ReviewFragment;
import vn.app.phims14.Module.fragment.VideoFragment;
import vn.app.phims14.R;

/**
 * Created by Minh on 4/12/2016.
 */
public class DetailVideoActivity extends FragmentActivity {
    TextView tvViewFilm, tvView, tvYear, tvCountry, tvType, tvRate, itemTitle, tvTitle, tvThanks;
    ImageView ivCover, ivBack;
    ProperRatingBar rbVote;
    SmartTabLayout indicator;
    ViewPager pager;
    LikeView likeView;
    MovieInfo info;
    ProgressDialog progressDialog;
    List<Fragment> mScreenList;
    boolean isRate = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_movie_activity);

        tvViewFilm = (TextView) findViewById(R.id.tv_view_film);
        ivCover = (ImageView) findViewById(R.id.iv_cover);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        pager = (ViewPager) findViewById(R.id.pager);
        indicator = (SmartTabLayout) findViewById(R.id.indicator);
        tvView = (TextView) findViewById(R.id.tv_view);
        tvYear = (TextView) findViewById(R.id.tv_year);
        tvCountry = (TextView) findViewById(R.id.tv_country);
        tvType = (TextView) findViewById(R.id.tv_type);
        tvRate = (TextView) findViewById(R.id.tv_rate);
        tvThanks = (TextView) findViewById(R.id.tv_thanks);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        rbVote = (ProperRatingBar) findViewById(R.id.rb_vote);
        likeView = (LikeView) findViewById(R.id.likeView);
        new getInfoMovie().execute();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void updateUI() {
        setupScreenTabs();
        setupSmartLayout();

        likeView.setLikeViewStyle(LikeView.Style.STANDARD);
        likeView.setAuxiliaryViewPosition(LikeView.AuxiliaryViewPosition.INLINE);
        likeView.setObjectIdAndType(
                info.getUrlRefFacebook(),
                LikeView.ObjectType.OPEN_GRAPH);
        tvType.setText(info.getCategories());
        tvCountry.setText(info.getCountry());
        tvYear.setText(info.getYear());
        tvView.setText(info.getView());
        tvTitle.setText(info.getTitle());
        tvRate.setText(info.getRate());
        Picasso.with(getParent()).load(info.getBanner()).into(ivCover);

        String view = info.getView();
        StringBuilder builder = new StringBuilder(view);
        view = builder.reverse().toString();
        String[] tmp = new String[view.length()];
        for (int i = 0; i < view.length(); i++) {
            tmp[i] = view.charAt(i) + "";
        }
        view = "";
        for (int i = 0; i < tmp.length; i++) {
            view += tmp[i];
            if ((i + 1) % 3 == 0 && (i + 1) != tmp.length) view += ".";
        }
        builder = new StringBuilder(view);
        tvViewFilm.setText(builder.reverse());


        rbVote.setRating(0);
        String status = GlobalVariable.PREFERENCES.getString(info.getId(), "");
        if(!status.isEmpty()){
            rbVote.setVisibility(View.GONE);
            tvThanks.setVisibility(View.VISIBLE);
        } else {
            rbVote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            rbVote.setListener(new RatingListener() {
                @Override
                public void onRatePicked(ProperRatingBar properRatingBar) {
                    new SubmitRating().execute(properRatingBar.getRating());
                    rbVote.setVisibility(View.GONE);
                    tvThanks.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    private void setupScreenTabs() {
        mScreenList = new ArrayList<>();
        try {
            mScreenList.add(new VideoFragment(info));
            mScreenList.add(new InfoFragment(info));
            mScreenList.add(new ReviewFragment(info));

        } catch (Exception e) {
            Log.d("setupScreenList", e.toString());
        }
    }

    private Fragment getScreen(int position) {
        Fragment screen = null;
        do {
            if (position > 3 || position < 0)
                break;
            screen = mScreenList.get(position);
        } while (false);
        return screen;
    }

    public void setup(SmartTabLayout layout) {

        final LayoutInflater inflater = LayoutInflater.from(layout.getContext());
        final Resources res = layout.getContext().getResources();
        layout.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                TextView textView = (TextView) inflater.inflate(R.layout.custom_tab_icon1, container, false);
                switch (position) {
                    case 0: {
                        textView.setText("Video");
                    }
                    break;
                    case 1: {
                        textView.setText("Info");
                    }
                    break;
                    case 2: {
                        textView.setText("Review");
                    }
                    break;
                }

                return textView;
            }
        });
    }

    public void setupSmartLayout() {
        FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return getScreen(position);
            }

            @Override
            public int getCount() {
                return 3;
            }
        };

        pager.setAdapter(adapter);
        setup(indicator);
        indicator.setViewPager(pager);
    }

    class getInfoMovie extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(DetailVideoActivity.this);
            progressDialog.setTitle(R.string.app_name);
            progressDialog.setMessage("Đang cập nhật dữ liệu ...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String stringURL = "http://s14.com.vn/Mobile/getitem";
            try {
                URL url = new URL(stringURL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(100000);
                urlConnection.setConnectTimeout(30000);
                urlConnection.setRequestMethod("POST");
                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("id", getIntent().getStringExtra("idMovie")));

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(GlobalVariable.createQueryWithParameters(params));
                writer.flush();
                writer.close();
                os.close();

                urlConnection.connect();
                InputStream inStream = urlConnection.getInputStream();
                BufferedReader bReader = new BufferedReader(new InputStreamReader(inStream));
                String temp, response = "";
                while ((temp = bReader.readLine()) != null) {
                    response += temp;
                }

                try {
                    JSONObject obj = new JSONObject(response);
                    info = new MovieInfo();
                    info.setId(getIntent().getStringExtra("idMovie"));
                    info.setTitle(getIntent().getStringExtra("titleMovie"));
                    info.setRate(getIntent().getStringExtra("rateMovie"));
                    info.setBanner(getIntent().getStringExtra("bannerMovie"));

                    String category = "";
                    JSONArray categories = obj.getJSONArray("Category");
                    for (int i = 0; i < categories.length(); i++) {
                        if (i == 0) category += categories.getString(i);
                        else category += ", " + categories.getString(i);
                    }
                    info.setCategories(category);

                    boolean existedServer;
                    JSONArray episodes = obj.getJSONArray("UrlPhim2");

                    //Create List Of Servers
                    for (int i = 0; i < episodes.length(); i++) {
                        existedServer = false;
                        JSONObject tmp = episodes.getJSONObject(i);
                        for (MovieServer server : info.getMovieServers()) {
                            if (server.getTitle().equalsIgnoreCase(tmp.getString("server"))) {
                                existedServer = true;
                                break;
                            }
                        }
                        if (!existedServer) {
                            info.getMovieServers().add(new MovieServer(tmp.getString("server")));
                        }
                    }

                    //Add each episode for each server.
                    for (int i = 0; i < episodes.length(); i++) {
                        JSONObject tmp = episodes.getJSONObject(i);
                        for (MovieServer server : info.getMovieServers()) {
                            if (server.getTitle().equalsIgnoreCase(tmp.getString("server"))) {
                                server.getMovieEpisodes().add(new MovieEpisode(tmp.getString("Eps"),
                                        tmp.getString("url"),""));
                            }
                        }
                    }

                    JSONArray actors = obj.getJSONArray("Actor");
                    for (int i = 0; i < actors.length(); i++) {
                        Actor actor = new Actor();
                        actor.setName(actors.getString(i));
                        info.getActors().add(actor);
                    }

                    info.setCountry(obj.getString("Country"));
                    info.setView(obj.getString("View"));
                    info.setYear(obj.getString("year"));
                    info.setProductCategory(obj.getString("Productcategory"));
                    info.setDuration(obj.getString("Duration"));
                    info.setUrlRefFacebook("http://s14.com.vn/" + obj.getString("UrlFacebook").split("/")[2]);
                    Document t = Jsoup.parse(obj.getString("Description"));
                    String t1 = t.text();
                    info.setDescription(t1);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new getRelateMovies().execute();
        }
    }

    class getRelateMovies extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            String stringURL = "http://s14.com.vn/Mobile/GetLinkrelate";
            try {
                URL url = new URL(stringURL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(100000);
                urlConnection.setConnectTimeout(30000);
                urlConnection.setRequestMethod("POST");


                int idCategory = 0;
                for (Category category : HomeActivity.categories) {
                    if (info.getCategories().contains(category.getCategoryName())) {
                        idCategory = category.getId();
                    }
                }
                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("categoryId", idCategory + ""));

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(GlobalVariable.createQueryWithParameters(params));
                writer.flush();
                writer.close();
                os.close();

                urlConnection.connect();
                InputStream inStream = urlConnection.getInputStream();
                BufferedReader bReader = new BufferedReader(new InputStreamReader(inStream));
                String temp, response = "";
                while ((temp = bReader.readLine()) != null) {
                    response += temp;
                }

                try {
                    JSONArray obj = new JSONArray(response);
                    for (int i = 0; i < obj.length(); i++) {
                        JSONObject tmp = obj.getJSONObject(i);
                        if (info.getId().equalsIgnoreCase(tmp.getString("id"))) continue;
                        info.getRelateMovies().add(new Movie(tmp.getString("url"),
                                tmp.getString("title"),
                                tmp.getString("rate"),
                                tmp.getString("id"),
                                tmp.getString("idYoutube")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            updateUI();
            progressDialog.dismiss();
        }
    }

    public class SubmitRating extends AsyncTask<Integer, Void, String> {

        @Override
        protected String doInBackground(Integer... params) {
            String stringURL = "http://s14.com.vn/Mobile/Rating";
            try {
                URL url = new URL(stringURL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(100000);
                urlConnection.setConnectTimeout(30000);
                urlConnection.setRequestMethod("POST");
                List<NameValuePair> param = new ArrayList<>();
                param.add(new BasicNameValuePair("videoid", info.getId()));
                param.add(new BasicNameValuePair("rate", params[0] + ""));

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(GlobalVariable.createQueryWithParameters(param));
                writer.flush();
                writer.close();
                os.close();

                urlConnection.connect();
                InputStream inStream = urlConnection.getInputStream();
                BufferedReader bReader = new BufferedReader(new InputStreamReader(inStream));
                String temp, response = "";
                while ((temp = bReader.readLine()) != null) {
                    response += temp;
                }

                //Update Local Rating.
                for(Movie movie : GlobalVariable.searchMovies){
                    if(movie.getIdMovie().equalsIgnoreCase(info.getId())){
                        movie.setRate(response);
                    }
                }
                for(Product product : GlobalVariable.mainPageMovies){
                    for(Movie movie : product.getMovies()){
                        if(movie.getIdMovie().equalsIgnoreCase(info.getId())){
                            movie.setRate(response);
                        }
                    }
                }
                return response;
            } catch (Exception e) {
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if(s != null){
                GlobalVariable.PREF_EDITOR.putString(info.getId(), "OK");
                GlobalVariable.PREF_EDITOR.commit();
                tvRate.setText(s);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        HomeActivity.callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
