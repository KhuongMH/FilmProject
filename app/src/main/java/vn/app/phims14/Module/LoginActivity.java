package vn.app.phims14.Module;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import vn.app.phims14.Classes.GlobalVariable;
import vn.app.phims14.R;

/**
 * Created by Minh on 4/19/2016.
 */
public class LoginActivity extends Activity {
    EditText etUsername, etPassword;
    Button btLogin, btRegister;
    ImageView icBack;
    LoginButton loginButton;
    TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        checkLogin();

//        btLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (checkValidate()) {
//
//                }
//
//            }
//        });
//
//        tvRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//            }
//        });
        loginButton = (LoginButton) findViewById(R.id.login_button);
        tvRegister = (TextView) findViewById(R.id.tv_register);
        icBack = (ImageView) findViewById(R.id.ic_back);
        btRegister = (Button) findViewById(R.id.bt_register);
        btLogin = (Button) findViewById(R.id.bt_login);
        etPassword = (EditText) findViewById(R.id.et_password);
        etUsername = (EditText) findViewById(R.id.et_username);
        loginButton.setReadPermissions("user_friends");
        loginButton.setReadPermissions("public_profile");
        loginButton.setReadPermissions("email");
        loginButton.setReadPermissions("user_birthday");
        getLoginDetails(loginButton);

        icBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void checkLogin() {

    }

    private String md5(String in) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.reset();
            digest.update(in.getBytes());
            byte[] a = digest.digest();
            int len = a.length;
            StringBuilder sb = new StringBuilder(len << 1);
            for (int i = 0; i < len; i++) {
                sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));
                sb.append(Character.forDigit(a[i] & 0x0f, 16));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getMd5Key(String password) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());

            byte byteData[] = md.digest();

            //convert the byte to hex format method 1
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }

            System.out.println("Digest(in hex format):: " + sb.toString());

            //convert the byte to hex format method 2
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                String hex = Integer.toHexString(0xff & byteData[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            System.out.println("Digest(in hex format):: " + hexString.toString());

            return hexString.toString();

        } catch (Exception e) {
            // TODO: handle exception
        }

        return "";
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);

        try {
            if (AccessToken.getCurrentAccessToken() != null) {
                LoginManager.getInstance().logOut();
            } else {

            }
        } catch (Exception e) {

        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        HomeActivity.callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    Bundle bFacebookData = null;

    protected Bundle getLoginDetails(LoginButton login_button) {

        // Callback registration
        login_button.registerCallback(HomeActivity.callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult login_result) {
                System.out.println("onSuccess");
                String accessToken = login_result.getAccessToken().getToken();
                Log.i("accessToken", accessToken);

                GraphRequest request = GraphRequest.newMeRequest(login_result.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.i("LoginActivity", response.toString());
                        if (object.has("id")) new checkExistUser().execute(object);
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name, first_name, last_name, email,gender, birthday, location,picture");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException exception) {
            }
        });
        return bFacebookData;
    }

    private Boolean checkValidate() {
        Boolean result = true;
        if (etUsername.getText().toString() == null) {
            etUsername.setError("Chưa nhập thông tin!");
            result = false;
        } else if (etPassword.getText().toString() == null) {
            etPassword.setError("Chưa nhập thông tin!");
            result = false;
        }

        if (etUsername.getText().toString().length() < 6) {
            etUsername.setError("Thông tin quá ngắn!");
            result = false;
        } else if (etPassword.getText().toString().length() < 6) {
            etPassword.setError("Thông tin quá ngắn!");
            result = false;
        }
        return result;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public class checkExistUser extends AsyncTask<JSONObject, Void, String>{
        JSONObject tmp;
        @Override
        protected String doInBackground(JSONObject... params) {
            tmp = params[0];
            try {
                String stringURL = "" + tmp.getString("id");
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
                JSONObject object = new JSONObject(response);
                if(object.has("response") && object.getInt("response") == 200){
                    //Existed
                    tmp = new JSONObject(response);
                    return "1";
                }
            } catch (Exception e) {
                return "1";
            }

            //Not existed
            return "0";
        }

        @Override
        protected void onPostExecute(String result) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            GlobalVariable.PREF_EDITOR.putString("InfoFacebook", tmp.toString());
            GlobalVariable.PREF_EDITOR.commit();
            startActivity(intent);
        }
    }
}
