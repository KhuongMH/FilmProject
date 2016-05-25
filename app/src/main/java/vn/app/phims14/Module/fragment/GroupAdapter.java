package vn.app.phims14.Module.fragment;

import android.view.View;
import android.widget.TextView;

import it.sephiroth.android.library.widget.HListView;
import uk.co.ribot.easyadapter.EasyAdapter;
import uk.co.ribot.easyadapter.ItemViewHolder;
import uk.co.ribot.easyadapter.PositionInfo;
import uk.co.ribot.easyadapter.annotations.LayoutId;
import uk.co.ribot.easyadapter.annotations.ViewId;
import vn.app.phims14.Classes.Movie;
import vn.app.phims14.Classes.Product;
import vn.app.phims14.R;

/**
 * Created by Minh on 4/8/2016.
 */
@LayoutId(R.layout.item_group_video)
public class GroupAdapter extends ItemViewHolder<Product> {
    @ViewId(R.id.llv_video)
    HListView llvVideo;
    @ViewId(R.id.tv_title)
    TextView tvTitle;

    public GroupAdapter(View view) {
        super(view);
    }

    @Override
    public void onSetValues(Product product, PositionInfo positionInfo) {
        tvTitle.setText(product.getTitle());
        EasyAdapter<Movie> movieAdapterEasyAdapter = new EasyAdapter<Movie>(getContext(), MovieAdapter.class, product.getMovies());
        llvVideo.setAdapter(movieAdapterEasyAdapter);
    }
}
