package vn.app.phims14.Module;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import com.rey.material.widget.SnackBar;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

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

import butterknife.ButterKnife;
import uk.co.ribot.easyadapter.EasyAdapter;
import vn.app.phims14.Classes.Advertisement;
import vn.app.phims14.Classes.Category;
import vn.app.phims14.Classes.GlobalVariable;
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
    public static CallbackManager callbackManager;
    ImageView ivSearch,ivMenuLeft, ivlogo;
    TextView tvLogin, tvUsername, tvUserPoint;
    RelativeLayout rlLogin;
    LinearLayout lluserLogin;
    DrawerLayout mDrawerLayout;
    ListView lvCategory;
    EditText et_search;
    ViewPager pager;
    AdView mAdView;
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
        GlobalVariable.APPLICATION_CONTEXT = getApplicationContext();
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.home_activity);
        lvCategory = (ListView) findViewById(R.id.lv_category);
        et_search = (EditText) findViewById(R.id.et_search);
        ivMenuLeft = (ImageView) findViewById(R.id.iv_menu_left);
        pager = (ViewPager) findViewById(R.id.pager);
        rlLogin = (RelativeLayout) findViewById(R.id.rl_login);
        lluserLogin = (LinearLayout) findViewById(R.id.user_info);
        tvLogin = (TextView) findViewById(R.id.tv_login);
        tvUsername = (TextView) findViewById(R.id.tv_username);
        tvUserPoint = (TextView) findViewById(R.id.tv_userpoint);
        ivSearch = (ImageView) findViewById(R.id.iv_search);
        ivlogo = (ImageView) findViewById(R.id.iv_logo_app_1);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        GlobalVariable.PREFERENCES = getApplicationContext().getSharedPreferences(getResources().getString(R.string.app_name), MODE_PRIVATE);
        GlobalVariable.PREF_EDITOR = GlobalVariable.PREFERENCES.edit();



        MobileAds.initialize(this, GlobalVariable.ADMOB_INTERSTITIAL_AD_ID);
        mAdView = (AdView) findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        //Khởi chạy thread lấy dữ liệu Category trên server.
        String tmp = GlobalVariable.PREFERENCES.getString("Categories", "");
        if (tmp.isEmpty()) {
            new getCategory().execute();
        } else {
            categories = ConvertJsonToCategories(tmp);
            setupUI();
        }

        ButterKnife.bind(this);
        mAds = new AdsManager(this, mAdtockDefault);
        mDrawerToggle = new ActionBarDrawerToggle(HomeActivity.this, mDrawerLayout,
                R.drawable.ic_launcher, R.string.app_name, R.string.app_name);
        ivMenuLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        int width = getResources().getDisplayMetrics().widthPixels / 3 * 2;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) lvCategory.getLayoutParams();
        params.width = width;
        lvCategory.setLayoutParams(params);

        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_search.getText().toString().isEmpty()) return;
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                intent.putExtra("searchText", et_search.getText().toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        String info = GlobalVariable.PREFERENCES.getString("InfoFacebook", "");
        if(!info.isEmpty()){
            updateUserInfo(info);
        }
    }

    private void updateUserInfo(String infoFacebook) {
        tvLogin.setVisibility(View.GONE);
        lluserLogin.setVisibility(View.VISIBLE);
        try {
            JSONObject object = new JSONObject(infoFacebook);
            tvUsername.setText(object.getString("name"));
            tvUserPoint.setText("0");
            Picasso.with(this).load(object.getJSONObject("picture").getJSONObject("data").getString("url"))
                    .transform(new CircleTransform()).into(ivlogo);
        } catch (Exception e) {

        }
    }

    public class CircleTransform implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());

            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }

            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap,
                    BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "circle";
        }
    }

    public void setupUI() {
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
                                new Advertisement(HomeActivity.this, GlobalVariable.FACEBOOK_INTERSTITIALS_ADS_ID).LoadFacebookAds();
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
    public void checkLogin() {

    }

    public List<Category> ConvertJsonToCategories(String jsonString) {
        List<Category> list = new ArrayList<>();
        Category tmp = new Category();
        tmp.setCategoryName("Phim Truyền Hình");
        tmp.setSlug("phim-bo");
        list.add(tmp);
        try {
            JSONArray array = new JSONArray(jsonString);
            for (int i = 0; i < array.length(); i++) {
                JSONObject category = array.getJSONObject(i);
                if (category.getInt("ProductParentId") != 1) continue;
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
                GlobalVariable.PREF_EDITOR.putString("Categories", response);
                GlobalVariable.PREF_EDITOR.commit();
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

