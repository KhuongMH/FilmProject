package vn.app.phims14.Module.fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import uk.co.ribot.easyadapter.EasyAdapter;
import vn.app.phims14.Classes.GlobalVariable;
import vn.app.phims14.Classes.MainPageBanner;
import vn.app.phims14.Classes.Movie;
import vn.app.phims14.Classes.Product;
import vn.app.phims14.R;


/**
 * Created by Minh on 3/29/2016.
 */
//public class HomeFragment<T> extends BaseFragment implements ApiFilmListener {
public class HomeFragment extends Fragment {

    SmartTabLayout smartTabLayout;
    ViewPager viewPager;
    FrameLayout frameLayout;
    private int mActiveScreenId = 1;
    ListView lvVideo;
    EasyAdapter<Product> movieDAOEasyAdapter;
    public static List<MainPageBanner> mainPageBanners = new ArrayList<>();
    public static int BANNER_NUMBER = 0;
    ProgressDialog progressDialog;
    private List<Fragment> mScreenList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater
                .inflate(R.layout.home_fragment, container, false);
        smartTabLayout = (SmartTabLayout) view.findViewById(R.id.banner_indicator);
        viewPager = (ViewPager) view.findViewById(R.id.banner_pager);
        frameLayout = (FrameLayout) view.findViewById(R.id.fl_slider);
        lvVideo = (ListView) view.findViewById(R.id.lv_video);
        movieDAOEasyAdapter = new EasyAdapter<>(getContext(), GroupAdapter.class, GlobalVariable.mainPageMovies);
        lvVideo.setAdapter(movieDAOEasyAdapter);
        //Check existed on sharedReferrence
//        if (HomeActivity.PREFERENCES == null) {
//            HomeActivity.PREFERENCES = getContext().getSharedPreferences(getResources().getString(R.string.app_name), getContext().MODE_PRIVATE);
//            HomeActivity.PREF_EDITOR = HomeActivity.PREFERENCES.edit();
//        }
//        String tmp = "";
//        if (tmp.isEmpty()) {
//            new getMoviesShowMainPage().execute();
//        } else {
//            products = ConvertJsonToProducts(tmp);
//            mainPageBanners = ConvertJsonToBanncers(tmp);
//            movieDAOEasyAdapter = new EasyAdapter<>(getContext(), GroupAdapter.class, products);
//            lvVideo.setAdapter(movieDAOEasyAdapter);
//            initSlider();
//        }

        //Not check existed on sharedReferrence
        new getMoviesShowMainPage().execute();
        return view;
    }

    private Fragment getScreen(int position) {
        Fragment screen = null;
        do {
            if (position > 3 || position < 0)
                break;

            try {
                screen = mScreenList.get(position);
            } catch (Exception e) {
                Log.d("getScreen", e.toString());
            }
        } while (false);

        return screen;
    }

    public void setup(SmartTabLayout layout) {

        final LayoutInflater inflater = LayoutInflater.from(layout.getContext());

        layout.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                View view = inflater.inflate(R.layout.custom_tab_circle, container, false);
                return view;
            }
        });
    }

    private void initSlider() {
        setup(smartTabLayout);
        setupScreenList();
        final FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                mActiveScreenId = position;
                return getScreen(position);
            }

            @Override
            public int getCount() {
                return 3;
            }
        };
        setupAutoChangeBanner();
        viewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(viewPager);
    }

    private void setupAutoChangeBanner() {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (BANNER_NUMBER > 2) {
                    BANNER_NUMBER = 0;
                }
                viewPager.setCurrentItem(BANNER_NUMBER, true);
                BANNER_NUMBER++;
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(runnable);
            }
        }, 100, 5000);
    }

    private void setupScreenList() {
        mScreenList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            mScreenList.add(new ImageFragment(GlobalVariable.mainPageMovies.get(i).getMovies().get(i + 1),
                    mainPageBanners.get(i).getUrl()));
        }
    }

    public void ConvertJsonToProducts(String jsonString) {
        try {
            GlobalVariable.mainPageMovies.clear();
            JSONObject tmp = new JSONObject(jsonString);
            JSONArray listProduct = tmp.getJSONArray("listproduct");
            Product product = null;
            for (int i = 0; i < listProduct.length(); i++) {
                JSONObject obj = listProduct.getJSONObject(i);
                if (i == 0) {
                    product = new Product("PHIM S14 ĐỀ XUẤT");
                }
                if (i == 8) {
                    GlobalVariable.mainPageMovies.add(product);
                    product = new Product("PHIM MỚI");
                }
                if (i == 16) {
                    GlobalVariable.mainPageMovies.add(product);
                    product = new Product("PHIM HOT");
                }
                product.getMovies().add(new Movie(obj.getString("url"),
                        obj.getString("title"),
                        obj.getString("rate"),
                        obj.getString("id"),
                        obj.getString("idYoutube")));
            }
            GlobalVariable.mainPageMovies.add(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<MainPageBanner> ConvertJsonToBanners(String jsonString) {
        List<MainPageBanner> list = new ArrayList<>();
        try {
            JSONObject tmp = new JSONObject(jsonString);
            JSONArray listBanner = tmp.getJSONArray("listbanner");
            for (int i = 0; i < listBanner.length(); i++) {
                JSONObject obj = listBanner.getJSONObject(i);
                list.add(new MainPageBanner(obj.getString("url")));
            }
        } catch (Exception e) {

        }
        return list;
    }

    class getMoviesShowMainPage extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle(R.string.app_name);
            progressDialog.setMessage("Đang tải dữ liệu phim ...");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String stringURL = "http://s14.com.vn/Mobile/index";
            try {
                URL url = new URL(stringURL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(100000);
                urlConnection.setConnectTimeout(30000);
                urlConnection.setRequestMethod("POST");
                InputStream inStream = urlConnection.getInputStream();
                BufferedReader bReader = new BufferedReader(new InputStreamReader(inStream));
                String temp, response = "";
                while ((temp = bReader.readLine()) != null) {
                    response += temp;
                }
                ConvertJsonToProducts(response);
                mainPageBanners = ConvertJsonToBanners(response);

//                //Save data to sharedReference
//                HomeActivity.PREF_EDITOR.putString("products_and_banners", response);
//                HomeActivity.PREF_EDITOR.commit();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();

            if (!mainPageBanners.isEmpty() && !GlobalVariable.mainPageMovies.isEmpty()) {
                movieDAOEasyAdapter.setItems(GlobalVariable.mainPageMovies);
                movieDAOEasyAdapter.notifyDataSetChanged();
                initSlider();
            }
        }
    }
}
