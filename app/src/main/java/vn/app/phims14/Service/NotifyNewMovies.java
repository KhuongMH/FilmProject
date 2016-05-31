package vn.app.phims14.Service;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import vn.app.phims14.Module.BeginActivity;

/**
 * Created by khuong.man on 5/31/2016.
 */
public class NotifyNewMovies extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        displayAlert();
    }

    private void displayAlert()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("PhimS14 có phim mới ? Bạn có muốn xem ngay không ?").setCancelable(
                false).setPositiveButton("Có",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(NotifyNewMovies.this, BeginActivity.class);
                        intent.putExtra("newMovie", true);
                        startActivity(intent);
                    }
                }).setNegativeButton("Không",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
