package vn.app.phims14.Module;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import vn.app.phims14.Database.UserDAO;
import vn.app.phims14.Module.fragment.ApiFilmListener;
import vn.app.phims14.R;
import vn.app.phims14.UIComponent.CircleTransform;

/**
 * Created by Minh on 4/19/2016.
 */
public class RegisterActivity extends Activity implements ApiFilmListener {
    @Bind(R.id.login_button)
    LoginButton loginButton;
    @Bind(R.id.iv_avatar)
    ImageView ivAvatar;
    @Bind(R.id.et_username)
    EditText etUsername;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.bt_register)
    Button btRegister;

    CallbackManager callbackManager;
    @Bind(R.id.et_fullname)
    EditText etFullname;
    @Bind(R.id.et_email)
    EditText etEmail;

    String typeUser = "S14";
    String imageAvatar = "https://www.stihi.ru/pics/2014/04/28/7822.jpg";
    @Bind(R.id.ic_back)
    ImageView icBack;
    @Bind(R.id.psiv_avatar)
    PorterShapeImageView psivAvatar;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        try {
            Intent intent = getIntent();
            Bundle InfoFacebook = intent.getBundleExtra("InfoFacebook");
            etFullname.setText(InfoFacebook.getString("name"));
            psivAvatar.setPadding(0,10,0,0);
            Picasso.with(this).load(InfoFacebook.getString("profile_pic")).error(R.drawable.ic_add_user).into(psivAvatar);
            etEmail.setText(InfoFacebook.getString("email"));
            typeUser = "Facebook";
        } catch (Exception e) {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        facebookSDKInitialize();
        verifyStoragePermissions(this);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        initImageLoader(this);

        loginButton.setReadPermissions("user_friends");
        loginButton.setReadPermissions("public_profile");
        loginButton.setReadPermissions("email");
        loginButton.setReadPermissions("user_birthday");
        getLoginDetails(loginButton);

        //ivAvatar.startAnimation(AnimationUtils.loadAnimation(this, R.anim.animation_rotate));

        /*Animation rotateAnim = new RotateAnimation(0, 360, canvas.getWidth() / 2, canvas.getHeight() / 2);
        rotateAnim.setRepeatMode(Animation.REVERSE);
        rotateAnim.setRepeatCount(Animation.INFINITE);
        rotateAnim.setDuration(10000L);
        rotateAnim.setInterpolator(new AccelerateDecelerateInterpolator());*/

        ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showChooser();
            }
        });

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        icBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private Boolean checkInfo() {
        Boolean result = true;
        if (etFullname.getText().toString() == null) {

        } else if (etEmail.getText().toString() == null) {
            etEmail.setError("Chưa nhập thông tin!");
            result = false;
        } else if (etUsername.getText().toString() == null) {
            etUsername.setError("Chưa nhập thông tin!");
            result = false;
        } else if (etPassword.getText().toString() == null) {
            etPassword.setError("Chưa nhập thông tin!");
            result = false;
        }

        if (etUsername.getText().toString().length() < 6) {
            etUsername.setError("Thông tin quá ngắn!");
            result = false;
        }

        if (etPassword.getText().toString().length() < 6) {
            etPassword.setError("Thông tin quá ngắn!");
            result = false;
        }

        if (!isValidEmail(etEmail.getText().toString())) {
            etEmail.setError("Thông tin không phù hợp!");
            result = false;
        }

        return result;
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    private String getBase64() {
        ivAvatar.buildDrawingCache();
        Bitmap bitmap = ivAvatar.getDrawingCache();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
        byte[] image = stream.toByteArray();
        System.out.println("byte array:" + image);

        String img_str = Base64.encodeToString(image, 0);
        System.out.println("string:" + img_str);

        return img_str;
    }

    protected void getLoginDetails(LoginButton login_button) {

        // Callback registration
        login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult login_result) {
                System.out.println("onSuccess");

                String accessToken = login_result.getAccessToken().getToken();
                Log.i("accessToken", accessToken);

                GraphRequest request = GraphRequest.newMeRequest(login_result.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.i("LoginActivity", response.toString());
                        // Get facebook data from login
                        Bundle bFacebookData = getFacebookData(object);

                        Profile profile = Profile.getCurrentProfile();
                        try {
                            imageAvatar = profile.getProfilePictureUri(120, 120).toString();
                            Picasso.with(RegisterActivity.this).load(profile.getProfilePictureUri(120, 120)).transform(new CircleTransform()).into(ivAvatar);
                        } catch (Exception e) {

                        }

                        try {
                            etEmail.setText(bFacebookData.getString("email"));
                            etFullname.setText(profile.getName());
                            etUsername.setText(bFacebookData.getString("idFacebook"));
                        } catch (Exception e) {

                        }


                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
                request.setParameters(parameters);
                request.executeAsync();


            }

            @Override
            public void onCancel() {
                // code for cancellation

            }

            @Override
            public void onError(FacebookException exception) {
                //  code to handle error

            }
        });
    }

    private void register() {
        checkExistAccount(etUsername.getText().toString());
    }

    private Bundle getFacebookData(JSONObject object) {
        Bundle bundle = new Bundle();
        try {

            String id = object.getString("id");

            try {
                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));
            if (object.has("location"))
                bundle.putString("location", object.getJSONObject("location").getString("name"));
        } catch (Exception e) {

        }
        return bundle;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUEST_CODE:
                // If the file selection was successful
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        // Get the URI of the selected file
                        final Uri uri = data.getData();
                        try {
                            // Get the file path from the URI
//                            final String path = FileUtils.getPath(this, uri);//"file://"
                            final String path = "";
                           /* ImageLoader imageLoader = ImageLoader.getInstance();
                            Bitmap bmp = imageLoader.loadImageSync("file://" + path);*/

                            DisplayImageOptions displayOptions = DisplayImageOptions.createSimple();
                            // Picasso.with(RegisterActivity.this).load("").transform(new CircleTransform()).into(ivAvatar);
                            //ImageSize minImageSize = new ImageSize(70, 70); // 70 - approximate size of ImageView in widget
                            ImageLoader.getInstance()
                                    .loadImage("file://" + path, null, displayOptions, new SimpleImageLoadingListener() {
                                        @Override
                                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                            ivAvatar.setImageBitmap(new CircleTransform().transform(loadedImage));
                                            //Picasso.with(RegisterActivity.this).load().transform(new CircleTransform()).into(ivAvatar);
                                        }
                                    });


                            //Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.d("REQUEST_CODE", e.toString());
                        }
                    }
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);


    }

    protected void facebookSDKInitialize() {

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    private static final int REQUEST_CODE = 6384;

    @Override
    public void loadContentView(int type, ArrayList data) {
        if (type == 5) {
            ArrayList<UserDAO> userDAOs = (ArrayList<UserDAO>) data;
            if (userDAOs.size() > 0) {
                Toast.makeText(this, "Tên tài khoản đã được đăng ký vui lòng nhập lại!", Toast.LENGTH_SHORT).show();
            } else {
                if (checkInfo()) {
                    Toast.makeText(this, "Chúc mừng bạn đã đăng ký thành công!", Toast.LENGTH_SHORT).show();

                }
            }
        }
    }

    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public Boolean checkExistAccount(String username) {
        Boolean result = false;
        return result;
    }
}
