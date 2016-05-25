package vn.app.phims14.Global;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vn.app.phims14.Classes.Category;
import vn.app.phims14.Database.CategoryDAO;
import vn.app.phims14.Database.UserDAO;
import vn.app.phims14.Helper.ads.AdsManager;

/**
 * Created by Minh on 4/19/2016.
 */
public class Global {
    private static Global ourInstance = new Global();

    public static Global getInstance() {
        return ourInstance;
    }

    private Global() {
    }

    List<Category> categoryDAOArrayList=new ArrayList<>();
    UserDAO userDAO=new UserDAO();

    public List<Category> getCategoryDAOArrayList() {
        return categoryDAOArrayList;
    }

    public void setCategoryDAOArrayList(List<Category> categoryDAOArrayList) {
        this.categoryDAOArrayList = categoryDAOArrayList;
    }

    public UserDAO getUserDAO() {
        return this.userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public String currentIDMovie="";
    public String currentIDUser="";

    public String getCurrentIDMovie() {
        return currentIDMovie;
    }

    public void setCurrentIDMovie(String currentIDMovie) {
        this.currentIDMovie = currentIDMovie;
    }

    public String getCurrentIDUser() {
        return currentIDUser;
    }

    public void setCurrentIDUser(String currentIDUser) {
        this.currentIDUser = currentIDUser;
    }

    public void LoadAds(AdsManager mAds,long mExitAdId) {
        long curTime = Calendar.getInstance().getTimeInMillis() / 1000;
        if (curTime % 2 == 0)
            LoadGoogleAds(mAds,mExitAdId);
        else {
            LoadFacebookAds(mAds,mExitAdId);
        }
    }

    private void LoadFacebookAds(final AdsManager mAds,long mExitAdId) {
        final long finalMExitAdId = mExitAdId;
        mExitAdId = mAds.loadIntelAd(AdsManager.AdStock.Facebook, new AdsManager.AdListener() {
            @Override
            public void onAdError(int errCode, String msgErr) {

            }

            @Override
            public void onAdDismissed() {

            }

            @Override
            public void onAdLoaded() {
                mAds.showInterstitialAd(finalMExitAdId);
            }

            @Override
            public void onRequestReload() {

            }
        });
    }

    private Boolean LoadGoogleAds(final AdsManager mAds,long mExitAdId) {
        final long finalMExitAdId = mExitAdId;
        mExitAdId = mAds.loadIntelAd(AdsManager.AdStock.Admob, new AdsManager.AdListener() {
            @Override
            public void onAdError(int errCode, String msgErr) {
                LoadFacebookAds(mAds, finalMExitAdId);
            }

            @Override
            public void onAdDismissed() {

            }

            @Override
            public void onAdLoaded() {
                mAds.showInterstitialAd(finalMExitAdId);
            }

            @Override
            public void onRequestReload() {

            }
        });
        return true;
    }
}
