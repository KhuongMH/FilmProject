package vn.app.phims14.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.TelephonyManager;

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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import vn.app.phims14.Classes.GlobalVariable;
import vn.app.phims14.Classes.Movie;

/**
 * Created by khuong.man on 5/31/2016.
 */
public class BroadcastService extends Service {

    private final Handler handlerThread = new Handler();
    @Override
    public void onStart(Intent intent, int startId) {
        handlerThread.removeCallbacks(sendRequestCheckNewMovies);
        handlerThread.postDelayed(sendRequestCheckNewMovies, 10);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Runnable sendRequestCheckNewMovies = new Runnable() {
        @Override
        public void run() {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Calendar c = Calendar.getInstance();
                    Calendar c1 = Calendar.getInstance();
                    c1.set(Calendar.HOUR_OF_DAY, 23);
                    c1.set(Calendar.MINUTE, 59);
                    if (c.getTime().equals(c1.getTime())) {
                        if (GlobalVariable.APPLICATION_CONTEXT != null) {
                            new checkAndUpdateNewFilm().execute();
                        }
                    }
                }
            }
            ).start();
            handlerThread.postDelayed(this, 60 * 1000);
        }
    };


    public class checkAndUpdateNewFilm extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            GlobalVariable.newMovies.clear();
            String urlString = "?";
            TelephonyManager telephonyManager = (TelephonyManager) GlobalVariable.APPLICATION_CONTEXT.getSystemService(Context.TELEPHONY_SERVICE);

            try {
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(30000);
                urlConnection.setRequestMethod("POST");
                List<NameValuePair> param = new ArrayList<>();
                param.add(new BasicNameValuePair("phoneId", telephonyManager.getDeviceId()));

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
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    GlobalVariable.newMovies.add(new Movie(object.getString("url"),
                            object.getString("title"),
                            object.getString("rate"),
                            object.getString("id"),
                            object.getString("idYoutube")));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
