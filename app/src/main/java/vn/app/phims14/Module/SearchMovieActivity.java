package vn.app.phims14.Module;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import org.askerov.dynamicgrid.DynamicGridView;
import org.json.JSONArray;
import org.json.JSONObject;

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

import butterknife.ButterKnife;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import uk.co.ribot.easyadapter.EasyAdapter;
import vn.app.phims14.Classes.Constant;
import vn.app.phims14.Classes.Movie;
import vn.app.phims14.Module.fragment.MovieAdapter;
import vn.app.phims14.R;

/**
 * Created by Minh on 4/7/2016.
 */
public class SearchMovieActivity extends Activity {
    DynamicGridView gridView;
    EditText et_search;
    TextView tv_title;
    List<Movie> movieList = new ArrayList<>();
    ProgressDialog progressDialog;
    EasyAdapter<Movie> movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_video_activity);
        et_search = (EditText) findViewById(R.id.et_search);
        gridView = (DynamicGridView) findViewById(R.id.dynamic_grid);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(getIntent().getStringExtra("categoryName"));
        movieAdapter = new EasyAdapter<>(getApplication(), MovieAdapter.class, movieList);
        gridView.setAdapter(movieAdapter);
        ButterKnife.bind(this);
        new getListFilms().execute("2000");
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                updateUI(s.toString());
            }
        });
    }

    public void updateUI(String search) {
        if (search.isEmpty()) {
            movieAdapter.setItems(movieList);
        } else {
            String[] s = search.split(" ");
            String text = "";
            for (int i = 0; i < s.length; i++) {
                if (i == s.length - 1) text += s[i];
                else text += s[i] + "-";
            }
            List<Movie> searchList = new ArrayList<>();
            for (Movie movie : movieList) {
                if (movie.getUrl().contains(text) || movie.getTitle().contains(search)) {
                    searchList.add(movie);
                }
            }
            movieAdapter.setItems(searchList);
        }
        movieAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    class getListFilms extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(SearchMovieActivity.this);
            progressDialog.setTitle(R.string.app_name);
            progressDialog.setMessage("Đang cập nhật dữ liệu phim ...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... quantity) {
            String stringURL = "http://s14.com.vn/Mobile/get100VideoByCategory";
            try {
                URL url = new URL(stringURL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(100000);
                urlConnection.setConnectTimeout(30000);
                urlConnection.setRequestMethod("POST");
                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("slug", getIntent().getStringExtra("categorySlug")));
                params.add(new BasicNameValuePair("take", quantity[0]));

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(Constant.createQueryWithParameters(params));
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
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        movieList.add(new Movie(jsonObject.getString("url"),
                                jsonObject.getString("title"),
                                jsonObject.getString("rate"),
                                jsonObject.getString("id"),
                                jsonObject.getString("idYoutube")));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return quantity[0];
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            movieAdapter.notifyDataSetChanged();
        }
    }
}
