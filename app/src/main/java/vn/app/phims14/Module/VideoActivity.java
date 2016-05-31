package vn.app.phims14.Module;

import android.app.Activity;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.MediaController;
import android.widget.VideoView;

import vn.app.phims14.Classes.Advertisement;
import vn.app.phims14.Classes.GlobalVariable;
import vn.app.phims14.Helper.ads.AdsManager;
import vn.app.phims14.R;

/**
 * Created by Minh on 4/4/2016.
 */
public class VideoActivity extends Activity {
    ProgressDialog pDialog;
    VideoView videoview;

    String VideoURL = "";
    private AdsManager mAds;
    private long mExitAdId;
    private final AdsManager.AdStock mAdtockDefault = AdsManager.AdStock.Admob;

    public VideoActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_activity);

        mAds = new AdsManager(this, mAdtockDefault);
        videoview = (VideoView) findViewById(R.id.vv_video);
        pDialog = new ProgressDialog(VideoActivity.this);
        pDialog.setTitle("Loading Video");
        pDialog.setMessage("Buffering...");
        pDialog.setIndeterminate(false);
        pDialog.show();

        VideoURL = getIntent().getStringExtra("urlVideo");
        playVideo(VideoURL);
    }

    private void playVideo(String url) {
        MediaController mediacontroller = new MediaController(
                VideoActivity.this);
        mediacontroller.setAnchorView(videoview);
        Uri video = Uri.parse(url);
        videoview.setMediaController(mediacontroller);
        videoview.setVideoURI(video);
        videoview.requestFocus();
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                pDialog.dismiss();
                videoview.start();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new Advertisement(this, GlobalVariable.FACEBOOK_INTERSTITIALS_ADS_ID).LoadFacebookAds();
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

//    private Boolean LoadGoogleAds() {
//        try {
//            mExitAdId = mAds.loadIntelAd(AdsManager.AdStock.Admob, new AdsManager.AdListener() {
//                @Override
//                public void onAdError(int errCode, String msgErr) {
//                    LoadFacebookAds();
//                }
//
//                @Override
//                public void onAdDismissed() {
//
//                }
//
//                @Override
//                public void onAdLoaded() {
//                    mAds.showInterstitialAd(mExitAdId);
//                }
//
//                @Override
//                public void onRequestReload() {
//
//                }
//            });
//        } catch (Exception e) {
//
//        }
//        return true;
//    }
}
