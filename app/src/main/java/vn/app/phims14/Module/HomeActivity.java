package vn.app.phims14.Module;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.rey.material.widget.SnackBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import uk.co.ribot.easyadapter.EasyAdapter;
import vn.app.phims14.Classes.Category;
import vn.app.phims14.Classes.Constant;
import vn.app.phims14.Global.Global;
import vn.app.phims14.Helper.ads.AdsManager;
import vn.app.phims14.Module.fragment.CategoryAdapter;
import vn.app.phims14.Module.fragment.HomeFragment;
import vn.app.phims14.R;

/**
 * Created by Minh on 3/29/2016.
 * <p/>
 * Man Huỳnh Khương đã mò vào project này ^_^ (11/05/2016)
 * <p/>
 * Ai sẽ là nạn nhân tiếp theo :3
 */
public class HomeActivity extends FragmentActivity {

    private static final String TAG = HomeActivity.class.getName();
    ImageView ivSearch, ivEdit, ivBack, ivMenuLeft;
    TextView tvUserName,tvLogin;
    RelativeLayout rlLogin, rlLogon;
    DrawerLayout mDrawerLayout;
    ListView lvCategory;
    EditText et_search;
    ViewPager pager;
    static SnackBar sbToast;

    private ActionBarDrawerToggle mDrawerToggle;

    private List<HomeFragment> mScreenList = new ArrayList<>();
    public static List<Category> categories = new ArrayList<>();
    private AdsManager mAds;
    private long mExitAdId;
    private final AdsManager.AdStock mAdtockDefault = AdsManager.AdStock.Admob;

    public HomeActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.home_activity);
        lvCategory = (ListView) findViewById(R.id.lv_category);
        et_search = (EditText) findViewById(R.id.et_search);
        ivMenuLeft = (ImageView) findViewById(R.id.iv_menu_left);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        pager = (ViewPager) findViewById(R.id.pager);
        rlLogon = (RelativeLayout) findViewById(R.id.rl_logon);
        rlLogin = (RelativeLayout) findViewById(R.id.rl_login);
//        tvLogin = (TextView) findViewById(R.id.tv_login);
        tvUserName = (TextView) findViewById(R.id.tv_user_name);
        ivEdit = (ImageView) findViewById(R.id.iv_edit);
        ivSearch = (ImageView) findViewById(R.id.iv_search);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        Constant.PREFERENCES = getApplicationContext().getSharedPreferences(getResources().getString(R.string.app_name), MODE_PRIVATE);
        Constant.PREF_EDITOR = Constant.PREFERENCES.edit();

        //Khởi chạy thread lấy dữ liệu Category trên server.
        String tmp = Constant.PREFERENCES.getString("Categories", "");
        if (tmp.isEmpty()) {
            new getCategory().execute();
        } else {
            categories = ConvertJsonToCategories(tmp);
            setupUI();
        }

        ButterKnife.bind(this);
        mAds = new AdsManager(this, mAdtockDefault);
        sbToast = (SnackBar) findViewById(R.id.sb_toast);
        mDrawerToggle = new ActionBarDrawerToggle(HomeActivity.this, mDrawerLayout,
                R.drawable.ic_launcher, R.string.app_name, R.string.app_name);
        ivMenuLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        int width = getResources().getDisplayMetrics().widthPixels/3*2;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) lvCategory.getLayoutParams();
        params.width = width;
        lvCategory.setLayoutParams(params);

        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_search.getText().toString().isEmpty()) return;
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                intent.putExtra("searchText", et_search.getText().toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        tvUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(HomeActivity.this)
                        .setTitle("Đăng xuất")
                        .setMessage("Bạn muốn Đăng xuất tài khoản không?")
                        .setPositiveButton(android.R.string.yes,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        //LoadAds();
                                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                        .setNegativeButton(android.R.string.no,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        return;
                                    }
                                }).setIcon(android.R.drawable.ic_dialog_alert).show();
            }
        });

//        tvLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//            }
//        });
    }

    public void setupUI() {
        Global.getInstance().setCategoryDAOArrayList(categories);
        mScreenList.clear();
        mScreenList.add(new HomeFragment());
        FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mScreenList.get(position);
            }

            @Override
            public int getCount() {
                return 1;
            }
        };
        pager.setAdapter(adapter);
        categoryDAOEasyAdapter = new EasyAdapter<>(HomeActivity.this, CategoryAdapter.class, categories);
        categoryDAOEasyAdapter.notifyDataSetChanged();
        lvCategory.setAdapter(categoryDAOEasyAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkLogin();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Bạn muốn thoát ứng dụng hay không?")
                .setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                LoadAds();
                                finish();
                            }
                        })
                .setNegativeButton(android.R.string.no,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                return;
                            }
                        }).setIcon(android.R.drawable.ic_dialog_alert).show();
    }

    EasyAdapter<Category> categoryDAOEasyAdapter;

    public void LoadAds() {
        long curTime = Calendar.getInstance().getTimeInMillis() / 1000;
        if (curTime % 2 == 0)
            LoadGoogleAds();
        else {
            LoadFacebookAds();
        }
    }

    public void LoadFacebookAds() {
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

    public Boolean LoadGoogleAds() {
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

    public void checkLogin() {

    }

    public void showLogin() {
        rlLogon.setVisibility(View.VISIBLE);
        rlLogin.setVisibility(View.GONE);


    }

    public void showLogon() {
        rlLogon.setVisibility(View.GONE);
        rlLogin.setVisibility(View.VISIBLE);
    }

    public List<Category> ConvertJsonToCategories(String jsonString) {
        List<Category> list = new ArrayList<>();
        Category tmp = new Category();
        tmp.setCategoryName("Phim Bộ");
        tmp.setSlug("phim-bo");
        list.add(tmp);
        tmp = new Category();
        tmp.setCategoryName("Phim Lẻ");
        tmp.setSlug("phim-le");
        list.add(tmp);
        try {
            JSONArray array = new JSONArray(jsonString);
            for (int i = 0; i < array.length(); i++) {
                JSONObject category = array.getJSONObject(i);
                if(category.getInt("ProductParentId") != 1) continue;
                list.add(new Category(category.getString("Name"),
                        category.getString("Description"),
                        category.getString("Slug"),
                        category.getBoolean("Deleted"),
                        category.getInt("ProductParentId"),
                        null, // ProductCategoryMappings - Chưa xài chỗ này
                        category.getInt("Id")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Class lấy dữ liệu bảng Category từ server và setup lên giao diện.
    class getCategory extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            String stringURL = "http://s14.com.vn/Mobile/getCategory";
            try {
                URL url = new URL(stringURL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(30000);
                urlConnection.setRequestMethod("POST");
                InputStream inStream = urlConnection.getInputStream();
                BufferedReader bReader = new BufferedReader(new InputStreamReader(inStream));
                String temp, response = "";
                while ((temp = bReader.readLine()) != null) {
                    response += temp;
                }
                Constant.PREF_EDITOR.putString("Categories", response);
                Constant.PREF_EDITOR.commit();
                categories = ConvertJsonToCategories(response);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        //Sau khi chạy xong thì cập nhật category menu trên giao diện
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setupUI();
        }
    }
}

