package vn.app.phims14.Module;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import vn.app.phims14.R;
/**
 * Created by khuong.man on 5/25/2016.
 */
public class BeginActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.begin_activity);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent next = new Intent(BeginActivity.this,HomeActivity.class);
                startActivity(next);
            }
        }, 2000);
    }
}
