package vn.app.phims14.Module.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import vn.app.phims14.Database.MovieSeriesDAO;
import vn.app.phims14.R;
import vn.app.phims14.Module.VideoActivity;
import uk.co.ribot.easyadapter.ItemViewHolder;
import uk.co.ribot.easyadapter.PositionInfo;
import uk.co.ribot.easyadapter.annotations.LayoutId;
import uk.co.ribot.easyadapter.annotations.ViewId;

/**
 * Created by Minh on 4/5/2016.
 */
@LayoutId(R.layout.item_video_film)
public class VideoFilmAdapter extends ItemViewHolder<MovieSeriesDAO> {


    @ViewId(R.id.iv_thumb)
    ImageView ivThumb;
    @ViewId(R.id.tv_name)
    TextView tvName;

    public VideoFilmAdapter(View view) {
        super(view);
    }

    @Override
    public void onSetValues(MovieSeriesDAO movieSeriesDAO, PositionInfo positionInfo) {
        Picasso.with(getContext()).load(R.drawable.bk1).into(ivThumb);
        tvName.setText(getItem().getIDMovie());


        ivThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), VideoActivity.class);
                intent.putExtra("urlVideo", getItem().getLink());
                intent.putExtra("idVideo", getItem().getIDMovie());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });
    }
}
