package vn.app.phims14.Classes;

import android.content.SharedPreferences;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import cz.msebera.android.httpclient.NameValuePair;

/**
 * Created by khuong.man on 5/17/2016.
 */
public class Constant {

    public static SharedPreferences PREFERENCES = null;
    public static SharedPreferences.Editor PREF_EDITOR = null;

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
