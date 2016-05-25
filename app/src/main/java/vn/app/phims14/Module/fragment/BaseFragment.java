package vn.app.phims14.Module.fragment;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by lqvu on 1/19/2016.
 */
public class BaseFragment<T> extends Fragment {

//    public boolean mIsSetup = false;

    /**
     * Setup fragment first time
     */

    protected void onSetup(View parentView) {
//        mIsSetup = true;
    }

    /**
     * Refresh this fragment after activate
     */
    protected void onRefresh() {}

}
