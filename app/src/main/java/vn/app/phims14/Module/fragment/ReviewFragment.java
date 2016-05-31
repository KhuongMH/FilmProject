package vn.app.phims14.Module.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linearlistview.LinearListView;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import uk.co.ribot.easyadapter.EasyAdapter;
import vn.app.phims14.Classes.MovieInfo;
import vn.app.phims14.Database.CommentDAO;
import vn.app.phims14.Database.UserDAO;
import vn.app.phims14.Module.DetailVideoActivity;
import vn.app.phims14.Module.LoginActivity;
import vn.app.phims14.R;
import vn.app.phims14.UIComponent.CircleTransform;

/**
 * Created by Minh on 4/12/2016.
 */
@SuppressLint("ValidFragment")
public class ReviewFragment extends Fragment {
    MovieInfo info;
    WebView webView;
    LinearLayout parentLayout;
    Activity MyActivity;

    public ReviewFragment(MovieInfo info) {
        this.info = info;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater
                .inflate(R.layout.review_fragment, container, false);
        MyActivity = getActivity();
        parentLayout = (LinearLayout) view.findViewById(R.id.parent_layout);
        webView = new WebView(MyActivity);
        webView.setLayoutParams(getLayoutParams());

        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= 21) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        }else{
            webView.getSettings().setAppCacheEnabled(true);
            webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            webView.getSettings().setSupportMultipleWindows(true);
            webView.getSettings().setSupportZoom(true);
            webView.getSettings().setBuiltInZoomControls(true);
        }
        parentLayout.addView(webView);
        webView.loadDataWithBaseURL("http://s14.com.vn",
                "<html><head></head><body><div id=\"fb-root\"></div><div id=\"fb-root\"></div><script>" +
                        "(function(d, s, id) {var js, fjs = d.getElementsByTagName(s)[0];if (d.getElementById(id))" +
                        " return;js = d.createElement(s); js.id = id;js.src = \"" +
                        "http://connect.facebook.net/en_US/all.js#xfbml=1&appId=" + getResources().getString(R.string.facebook_app_id)
                        + "\";fjs.parentNode.insertBefore(js, fjs);}(document, 'script', 'facebook-jssdk'));</script><div class=\"fb-comments\" data-href=\""
                        + info.getUrlRefFacebook() + "\" data-width=\"470\"></div> </body></html>", "text/html", null, null);
        return view;
    }

    private LinearLayout.LayoutParams getLayoutParams() {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
    }
}

