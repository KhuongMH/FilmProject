package vn.app.phims14.Module.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import io.techery.properratingbar.ProperRatingBar;
import uk.co.ribot.easyadapter.ItemViewHolder;
import uk.co.ribot.easyadapter.PositionInfo;
import uk.co.ribot.easyadapter.annotations.LayoutId;
import uk.co.ribot.easyadapter.annotations.ViewId;
import vn.app.phims14.Classes.Movie;
import vn.app.phims14.Module.DetailVideoActivity;
import vn.app.phims14.R;

/**
 * Created by Minh on 4/5/2016.
 */
@LayoutId(R.layout.item_grid)
public class MovieAdapter extends ItemViewHolder<Movie> {
    @ViewId(R.id.item_img)
    ImageView itemImg;
    @ViewId(R.id.tv_rate)
    TextView tvRate;
    @ViewId(R.id.rb_vote)
    ProperRatingBar rbVote;
    @ViewId(R.id.item_title)
    TextView itemTitle;
    String image;

    public MovieAdapter(View view) {
        super(view);
    }

    @Override
    public void onSetValues(final Movie movie, PositionInfo positionInfo) {
        image = "";
        if (!movie.getIdYoutube().equalsIgnoreCase("null")) {
            image = "http://img.youtube.com/vi/" + movie.getIdYoutube() + "/hqdefault.jpg";
        } else {
            image = movie.getUrl();
        }
        Picasso.with(getContext()).load(image).into(itemImg);
        itemTitle.setText(movie.getTitle());
        tvRate.setText(movie.getRate());
        rbVote.setRating((int) Double.parseDouble(movie.getRate()));

        itemImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailVideoActivity.class);
                intent.putExtra("idMovie", movie.getIdMovie());
                intent.putExtra("titleMovie", movie.getTitle());
                intent.putExtra("rateMovie", movie.getRate());
                intent.putExtra("bannerMovie", image);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });
    }
}
