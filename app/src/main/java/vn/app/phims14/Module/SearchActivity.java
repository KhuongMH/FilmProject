package vn.app.phims14.Module;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import org.askerov.dynamicgrid.DynamicGridView;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import uk.co.ribot.easyadapter.EasyAdapter;
import vn.app.phims14.Classes.GlobalVariable;
import vn.app.phims14.Classes.Movie;
import vn.app.phims14.Module.fragment.MovieAdapter;
import vn.app.phims14.R;

/**
 * Created by Minh on 4/7/2016.
 */
public class SearchActivity extends Activity {
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.dynamic_grid)
    DynamicGridView gridView;

    List<Movie> movieList = new ArrayList<>();
    ProgressDialog progressDialog;
    EasyAdapter<Movie> movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        ButterKnife.bind(this);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        movieAdapter = new EasyAdapter<>(getApplication(), MovieAdapter.class, movieList);
        gridView.setAdapter(movieAdapter);
        new getSearchResult().execute();
    }

    class getSearchResult extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(SearchActivity.this);
            progressDialog.setTitle(R.string.app_name);
            progressDialog.setMessage("Đang tìm kiếm dữ liệu phim ...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String stringURL = "http://s14.com.vn/Mobile/search";
            String searchText = deAccent(getIntent().getStringExtra("searchText"));
            String[] tmp = searchText.split(" ");
            for (int i =0;i<tmp.length; i++){
                if(i == 0) searchText = tmp[i];
                else searchText += "-" + tmp[i];
            }
            try {
                URL url = new URL(stringURL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(100000);
                urlConnection.setConnectTimeout(30000);
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("value", searchText));
                params.add(new BasicNameValuePair("pagesize", "50"));
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
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            movieAdapter.notifyDataSetChanged();
        }
    }

    public String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }
}
