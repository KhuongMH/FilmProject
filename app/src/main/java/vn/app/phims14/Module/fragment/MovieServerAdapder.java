package vn.app.phims14.Module.fragment;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.askerov.dynamicgrid.DynamicGridView;

import it.sephiroth.android.library.widget.HListView;
import vn.app.phims14.Classes.MovieServer;
import vn.app.phims14.Database.ItemPartVideo;
import vn.app.phims14.R;
import uk.co.ribot.easyadapter.ItemViewHolder;
import uk.co.ribot.easyadapter.PositionInfo;
import uk.co.ribot.easyadapter.annotations.LayoutId;
import uk.co.ribot.easyadapter.annotations.ViewId;

/**
 * Created by Minh on 4/12/2016.
 */
@LayoutId(R.layout.item_server_video)
public class MovieServerAdapder extends ItemViewHolder<MovieServer> {
    @ViewId(R.id.tv_server_name)
    TextView tvServerName;
    @ViewId(R.id.hl_video)
    HListView dgVideo;

    public MovieServerAdapder(View view) {
        super(view);
    }

    @Override
    public void onSetValues(MovieServer movieServer, PositionInfo positionInfo) {
        tvServerName.setText("Server " + getItem().getTitle() + " : " + getItem().getMovieEpisodes().size() + " tập");
        HomeAdapter adapter = new HomeAdapter(getContext(), getItem().getMovieEpisodes(),4);
        dgVideo.setAdapter(adapter);
    }

}
