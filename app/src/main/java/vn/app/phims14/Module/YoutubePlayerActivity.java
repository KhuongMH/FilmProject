package vn.app.phims14.Module;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.Calendar;

import vn.app.phims14.Helper.ads.AdsManager;
import vn.app.phims14.R;


/**
 * Created by Minh on 8/25/2015.
 */


public class YoutubePlayerActivity extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_DIALOG_REQUEST = 1;

    // YouTube player view


    public static final String API_KEY = "AIzaSyBQEUxJUCf3fZRQz65X3vSy9tMGFPxR8HM";

    // YouTube video id
    public String VIDEO_ID = "wwZpEo8DpcI";

    private AdsManager mAds;
    private long mExitAdId;
    private final AdsManager.AdStock mAdtockDefault = AdsManager.AdStock.Admob;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** attaching layout xml **/
        setContentView(R.layout.activity_player);
        mAds = new AdsManager(this, mAdtockDefault);
        try {
            Intent intent = getIntent();
            VIDEO_ID = intent.getStringExtra("VideoId");
        } catch (Exception e) {

        }

        /** Initializing YouTube player view **/
        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubePlayerView.initialize(API_KEY, this);

        //youTubePlayerView.performClick();
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {
        Toast.makeText(this, "Failured to Initialize!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        /** add listeners to YouTubePlayer instance **/
        player.setPlayerStateChangeListener(playerStateChangeListener);
        player.setPlaybackEventListener(playbackEventListener);
        player.setFullscreen(true);
        //player.play();

        /** Start buffering **/
        if (!wasRestored) {
            player.loadVideo(VIDEO_ID);
        }
    }

    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {

        @Override
        public void onBuffering(boolean arg0) {
        }

        @Override
        public void onPaused() {
        }

        @Override
        public void onPlaying() {
        }

        @Override
        public void onSeekTo(int arg0) {
        }

        @Override
        public void onStopped() {
        }

    };

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {

        @Override
        public void onAdStarted() {
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason arg0) {
        }

        @Override
        public void onLoaded(String arg0) {
        }

        @Override
        public void onLoading() {
        }

        @Override
        public void onVideoEnded() {
        }

        @Override
        public void onVideoStarted() {
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        LoadAds();
        finish();
    }

    private void LoadAds() {
//        long curTime = Calendar.getInstance().getTimeInMillis() / 1000;
//        if (curTime % 2 == 0)
            LoadGoogleAds();
//        else {
//            LoadFacebookAds();
//        }
    }

    private void LoadFacebookAds() {
        mExitAdId = mAds.loadIntelAd(AdsManager.AdStock.Facebook, new AdsManager.AdListener() {
            @Override
            public void onAdError(int errCode, String msgErr) {

            }

            @Override
            public void onAdDismissed() {

            }

            @Override
            public void onAdLoaded() {
                mAds.showInterstitialAd(mExitAdId);
            }

            @Override
            public void onRequestReload() {

            }
        });
    }

    private Boolean LoadGoogleAds() {
        mExitAdId = mAds.loadIntelAd(AdsManager.AdStock.Admob, new AdsManager.AdListener() {
            @Override
            public void onAdError(int errCode, String msgErr) {
                LoadFacebookAds();
            }

            @Override
            public void onAdDismissed() {

            }

            @Override
            public void onAdLoaded() {
                mAds.showInterstitialAd(mExitAdId);
            }

            @Override
            public void onRequestReload() {

            }
        });
        return true;
    }

}
