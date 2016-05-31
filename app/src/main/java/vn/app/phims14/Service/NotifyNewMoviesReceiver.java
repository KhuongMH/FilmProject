package vn.app.phims14.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import vn.app.phims14.Classes.GlobalVariable;

/**
 * Created by khuong.man on 5/31/2016.
 */
public class NotifyNewMoviesReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(!GlobalVariable.newMovies.isEmpty()){
            Intent noob = new Intent(context, NotifyNewMovies.class);
            context.startActivity(noob);
        }
    }
}
