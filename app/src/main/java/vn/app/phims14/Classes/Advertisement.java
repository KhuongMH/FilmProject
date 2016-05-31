package vn.app.phims14.Classes;

import android.app.Activity;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;

/**
 * Created by khuong.man on 5/30/2016.
 */
public class Advertisement {

    private InterstitialAd interstitialAd;

    public Advertisement(Activity activity, String Facebook_Ad_ID) {
        interstitialAd = new InterstitialAd(activity, Facebook_Ad_ID);
    }

    public void LoadFacebookAds() {

        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {

            }

            @Override
            public void onInterstitialDismissed(Ad ad) {

            }

            @Override
            public void onError(Ad ad, AdError adError) {

            }

            @Override
            public void onAdLoaded(Ad ad) {
                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {

            }
        });

        interstitialAd.loadAd();
    }

}
