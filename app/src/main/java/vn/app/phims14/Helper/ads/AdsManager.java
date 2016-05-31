package vn.app.phims14.Helper.ads;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.facebook.ads.AbstractAdListener;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.NativeAd;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import vn.app.phims14.Classes.GlobalVariable;


/**
 * Created by lqvu on 8/21/2015.
 */
public class AdsManager {

    private static final String TAG = AdsManager.class.getSimpleName();
    private final String KEY_LAST_SHOW_INL_AD = "last_show_inl_ad";
    private final String KEY_COUNT_SHOW_INL_AD = "count_show_inl_ad";
    private final String KEY_LAST_CHECK_AD_STOCK = "last_check_ad_stock";
    private final String KEY_CURRENT_AD_STOCK = "current_ad_stock";

    // Def Error Code
    public static int ERROR_CODE_LACK_FB = 1203;
    public static String ERROR_MSG_LACK_FB = "We are not able to serve ads to this person";

    // Maximum counter show interstitial Ad
    private final int MAX_SHOW_INL_AD = 3;

    // Max time for reset counter show Interstitial Ad
    private final long WAIT_TIME_FOR_SHOW_AD = 15 * 60 * 1000; // 15 min
    private final long WAIT_TIME_FOR_CHECK_AD_STOCK = 14 * 24 * 60 * 60 * 1000; // 2 weeks
    private final float LARGE_INCH_SIZE = 6.5f;

    private Activity mActivity;
    public AdStock mLastStockContent;
    public AdStock mLastStockHistory;
    public AdStock mLastStockExit;

    public enum AdStock {
        None,

        Facebook,
        Admob;

        public static AdStock getValue(int id) {
            AdStock[] ad = AdStock.values();
            AdStock ret = AdStock.None;
            if (id > 0 && ad.length > id)
                ret = ad[id];

            return ret;
        }
    }

    public enum AdPosition {
        Content,
        History
    }

    // Content Ads object cached
    private Map<Long, Object> mAdObjects = new HashMap<Long, Object>();

    // Interface request when banner FB Ad error
    private List<ReloadAdListener> mRequestsReload = new ArrayList<ReloadAdListener>();
    private LayoutInflater mInflater;
//    private View.OnClickListener mLocalAdListener;


    public AdsManager(Activity activity, AdStock adStock) {
        do {
            mActivity = activity;
            mInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } while (false);
    }

    /**
     * Check internet problem by AdStock
     *
     * @param errCode
     * @return
     */
    public boolean isNetworkProblem(int errCode, AdStock adStock) {
        boolean ret = false;
        switch (adStock) {
            case Facebook: {
                if (errCode == AdError.NETWORK_ERROR_CODE)
                    ret = true;
                break;
            }
            case Admob: {
                if (errCode == AdRequest.ERROR_CODE_NETWORK_ERROR)
                    ret = true;
                break;
            }

            default:
                break;
        }

        return ret;
    }


    /**
     * Load interstitial Ad
     *
     * @param adStock
     * @param listener
     * @return
     */
    public long loadIntelAd(AdStock adStock, AdListener listener) {

        mLastStockExit = adStock;
        long regID = 0;
        switch (adStock) {
            case Facebook: {
                regID = loadIntelFbk(GlobalVariable.FACEBOOK_INTERSTITIALS_ADS_ID, listener);
                break;
            }

//            case Admob: {
//                regID = loadIntelAdmob(Constant.ADMOB_INTERSTITIAL_AD_ID, listener);
//                break;
//            }

            default:
                break;
        }

        return regID;
    }

    /**
     * Load interstitial Ad for Admob
     *
     * @param admobIntelID
     * @param listener
     * @return
     */
    private long loadIntelAdmob(String admobIntelID, final AdListener listener) {
        com.google.android.gms.ads.InterstitialAd ad = new com.google.android.gms.ads.InterstitialAd(mActivity);
        ad.setAdUnitId(admobIntelID);
        ad.setAdListener(new com.google.android.gms.ads.AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                listener.onAdDismissed();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);

                String msgErr = String.format(Locale.US,
                        "Load interstitial Admob fail [%s]", errorCode);
                if (errorCode != AdRequest.ERROR_CODE_NETWORK_ERROR)
                    Log.e(TAG, msgErr);

                listener.onAdError(errorCode, msgErr);
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                listener.onAdLoaded();
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        ad.loadAd(adRequest);

        long regID = System.currentTimeMillis();
        mAdObjects.put(regID, ad);
        return regID;
    }

    /**
     * Load full the Facebook Ad when app was exited
     *
     * @param facebookID
     * @return
     */
    private long loadIntelFbk(String facebookID, final AdListener listener) {

        InterstitialAd ad = null;
        do {

            if (facebookID == null)
                break;

//            if (Def.DEBUG)
//                AdSettings.addTestDevice(BuildConfig.FbkDebuggerID);

            try {
                ad = new InterstitialAd(mActivity, facebookID);
                ad.setAdListener(new InterstitialAdListener() {
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
//                        ad.show();
                    }

                    @Override
                    public void onAdClicked(Ad ad) {

                    }
                });
                AdSettings.addTestDevice("5aba0a3b323aabac517ae6575d35c588");
                ad.loadAd();
            } catch (Exception ex) {
                Log.e(TAG, ex.getMessage());
            } catch (Error e) {
                Log.e(TAG, e.getMessage());
            }


        } while (false);

        long regID = System.currentTimeMillis();
        mAdObjects.put(regID, ad);
        return regID;
    }


    /**
     * Show interstitial Ad
     *
     * @param regID
     * @return
     */
    public boolean showInterstitialAd(long regID) {
        boolean ret = false;
        do {
            if (mAdObjects.containsKey(regID) == false)
                break;

            Object adObj = mAdObjects.get(regID);
            if (adObj == null)
                break;

            // Facebook interstitial Ad
            if (adObj instanceof InterstitialAd) {
                InterstitialAd ad = (InterstitialAd) adObj;
                if (ad.isAdLoaded()) {
                    ad.show();
                    ret = true;
                }
                break;
            }

            // Admob interstitial Ad
            if (adObj instanceof com.google.android.gms.ads.InterstitialAd) {
                com.google.android.gms.ads.InterstitialAd ad = (com.google.android.gms.ads.InterstitialAd) adObj;
                if (ad.isLoaded()) {
                    ad.show();
                    ret = true;
                }
                break;
            }

        } while (false);
        return ret;
    }

    /**
     * Destroy Ad by register ID of Ad
     *
     * @param regID
     */
    public void destroyAd(long regID) {
        do {
            if (regID == 0 || mAdObjects.containsKey(regID) == false)
                break;

            Object adObj = mAdObjects.get(regID);
            if (adObj == null)
                break;

            // Banner Ad of Admob
            if (adObj instanceof com.google.android.gms.ads.AdView) {
                ((com.google.android.gms.ads.AdView) adObj).destroy();
                break;
            }

            // Banner Facebook Ad
            if (adObj instanceof AdView) {
                ((AdView) adObj).destroy();
                break;
            }

            // Native Ad of Facebook
            if (adObj instanceof NativeAd) {
                ((NativeAd) adObj).destroy();
                break;
            }

            // Interstitial Ad of Facebook
            if (adObj instanceof InterstitialAd) {
                ((InterstitialAd) adObj).destroy();
                break;
            }

//            // Banner amazon Ad
//            if (adObj instanceof AdLayout) {
//                ((AdLayout) adObj).destroy();
//                break;
//            }

            // Skip interstitial Ad of Admob
            // Skip Amazon interstitial Ad
        } while (false);

        mAdObjects.remove(regID);
    }

    public void destroy() {
        // java.util.ConcurrentModificationException
        synchronized (mAdObjects) {
            List<Long> adsID = new ArrayList<Long>();
            for (long adID : mAdObjects.keySet())
                adsID.add(adID);

            for (long adID : adsID)
                destroyAd(adID);
        }

        if (mRequestsReload != null)
            mRequestsReload.clear();

        if (mAdObjects != null)
            mAdObjects.clear();
    }

    /**
     * Load banner Ad
     *
     * @param adStock
     * @param rootView
     * @param listener
     * @return
     */
    @SuppressLint("NewApi")
    public long loadBannerAd(AdStock adStock, LinearLayout rootView, AdListener listener) {

        mLastStockContent = adStock;
        long regID = 0;
        do {
            if (rootView == null)
                break;

            switch (adStock) {
                case Facebook: {
                    regID = loadFbkBanner(GlobalVariable.FACEBOOK_BANNER_AD_ID, rootView, listener);
                    break;
                }

                case Admob: {
                    regID = loadAdmobBanner(GlobalVariable.ADMOB_BANNER_AD_ID, rootView, listener);
                    break;
                }

                default:
                    break;
            }
        } while (false);

        return regID;
    }

    /**
     * Load Facebook banner Ads
     *
     * @param facebookID
     * @param rootView
     * @return
     */
    private long loadFbkBanner(String facebookID, final LinearLayout rootView, final AdListener listener) {
//        if (Def.DEBUG)
//        AdSettings.addTestDevice("68a622a3e84898f31b582f4cfedda49f");

        final AdView view = new com.facebook.ads.AdView(mActivity, facebookID, com.facebook.ads.AdSize.BANNER_HEIGHT_50);
        view.setAdListener(new AbstractAdListener() {
            @Override
            public void onError(com.facebook.ads.Ad ad, com.facebook.ads.AdError adError) {
                super.onError(ad, adError);

                int errCode = adError.getErrorCode();
                String errMsg = adError.getErrorMessage();
                String msgErr = String.format(Locale.US, "Load banner FB fail [%s] - %s ", errCode, errMsg);
                Log.e(TAG, msgErr);
                listener.onAdError(errCode, msgErr);
            }

            @Override
            public void onAdLoaded(com.facebook.ads.Ad ad) {
                do {
                    super.onAdLoaded(ad);
                    listener.onAdLoaded();

                    // Trying reload after 3s
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // java.util.ConcurrentModificationException
                            List<ReloadAdListener> cloneReqs = new ArrayList<ReloadAdListener>();
                            for (ReloadAdListener req : mRequestsReload)
                                cloneReqs.add(req);

                            for (ReloadAdListener req : cloneReqs)
                                req.onRequestReload();
                        }
                    }, 3000);
                } while (false);
            }
        });

        // Add Ad view to view layout
        rootView.addView(view);
        view.loadAd();
        long regId = System.currentTimeMillis();
        mAdObjects.put(regId, view);
        return regId;
    }

    /**
     * Load Admob banner Ads
     *
     * @param admobID
     * @param rootView
     * @return
     */
    private long loadAdmobBanner(String admobID, final LinearLayout rootView, final AdListener listener) {
        final com.google.android.gms.ads.AdView view = new com.google.android.gms.ads.AdView(mActivity);
        view.setAdUnitId(admobID);
        view.setAdSize(AdSize.SMART_BANNER);
        view.setAdListener(new com.google.android.gms.ads.AdListener() {
            @Override
            public void onAdClosed() {
                listener.onAdDismissed();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                String msgErr = String.format(Locale.US, "Load banner Admob fail [%s]", errorCode);
                if (errorCode != AdRequest.ERROR_CODE_NETWORK_ERROR)
                    Log.e(TAG, msgErr);

                listener.onAdError(errorCode, msgErr);
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                listener.onAdLoaded();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }
        });

        // Add view to ad layout
        rootView.addView(view);
        AdRequest adRequest = new AdRequest.Builder().build();
        view.loadAd(adRequest);

        long regID = System.currentTimeMillis();
        mAdObjects.put(regID, view);
        return regID;
    }

    /**
     * Load native Ad
     *
     * @param adStock
     * @param listener
     * @return
     */
    public long loadNavAd(AdStock adStock, NavAdListener listener) {

        mLastStockHistory = adStock;
        long regID = 0;
        switch (adStock) {
            case Facebook: {
                regID = loadNavFbk(GlobalVariable.FACEBOOK_INLIST_ADS_ID, listener);
                break;
            }
            default:
                break;
        }

        return regID;
    }


    /**
     * Setup Native Facebook Ads in list view
     *
     * @param facebookID
     * @return
     */
    public long loadNavFbk(String facebookID, final NavAdListener listener) {
        long regID = 0;
        do {
            if (facebookID == null)
                break;

//            if (Def.DEBUG)
//                AdSettings.addTestDevice(BuildConfig.FbkDebuggerID);

            final NativeAd nativeAd = new NativeAd(mActivity, facebookID);
            nativeAd.setAdListener(new com.facebook.ads.AdListener() {
                @Override
                public void onError(Ad ad, AdError adError) {

                    int codeErr = adError.getErrorCode();
                    String msgErr = String.format(Locale.US,
                            "Load Nav FB fail [%s] - %s", codeErr, adError.getErrorMessage());
                    Log.e(TAG, msgErr);

                    // Register reload
                    mRequestsReload.add(listener);
                    listener.onAdError(codeErr, msgErr);
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    do {
                        // Unregister reload
                        if (mRequestsReload.contains(listener))
                            mRequestsReload.remove(listener);

                        if (nativeAd != ad) {
                            listener.onAdError(110, "Wrong Ad instance");
                            break;
                        }

                        /*View adView = inflateAd(nativeAd);
                        listener.onAdLoaded(adView);*/
                    } while (false);
                }

                @Override
                public void onAdClicked(Ad ad) {
                }
            });

            nativeAd.setMediaViewAutoplay(false);
            nativeAd.loadAd();

            regID = System.currentTimeMillis();
            mAdObjects.put(regID, nativeAd);

        } while (false);

        return regID;
    }

    /**
     * Reload Ad interface
     */
    private interface ReloadAdListener {
        /**
         * Support reload Ad for Facebook Ad trigger
         */
        void onRequestReload();
    }

    /**
     * Native Facebook Ads listener
     */
    public interface NavAdListener extends ReloadAdListener {

        /**
         * Call when Ad load success
         */
        void onAdLoaded(View adView);

        /**
         * Call when Ad load error
         */
        void onAdError(int errCode, String msgErr);
    }

    /**
     * Listener for the interstitial Ads
     */
    public interface AdListener extends ReloadAdListener {

        /**
         * Call when Ad error
         */
        void onAdError(int errCode, String msgErr);

        /**
         * Call when the interstitial Ad dismiss
         */
        void onAdDismissed();

        /**
         * Call when the interstitial Ad loaded
         */
        void onAdLoaded();
    }
}