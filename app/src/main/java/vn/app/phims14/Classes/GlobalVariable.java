package vn.app.phims14.Classes;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.NameValuePair;

/**
 * Created by khuong.man on 5/17/2016.
 */
public class GlobalVariable {

    //Ads ID
    public final static String FACEBOOK_BANNER_AD_ID = "885273401598948_888687784590843";
    public final static String FACEBOOK_INLIST_ADS_ID = "885273401598948_888687784590843";
    public final static String FACEBOOK_INTERSTITIALS_ADS_ID = "885273401598948_892598760866412";
    public final static String ADMOB_BANNER_AD_ID = "ca-app-pub-1616957174178533/3968690600";
    public final static String ADMOB_INTERSTITIAL_AD_ID = "ca-app-pub-1616957174178533~3968690600";

    public static SharedPreferences PREFERENCES = null;
    public static SharedPreferences.Editor PREF_EDITOR = null;
    public static UserAccount CURRENT_USER = new UserAccount();
    public static Context APPLICATION_CONTEXT = null;
    public static List<Movie> newMovies = new ArrayList<>();
    public static boolean hasRunBroadcastService = false;

    public static String createQueryWithParameters(List<NameValuePair> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }
        return result.toString();
    }
}
