package vn.app.phims14.Module.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import io.techery.properratingbar.ProperRatingBar;
import vn.app.phims14.Classes.Movie;
import vn.app.phims14.Module.DetailVideoActivity;
import vn.app.phims14.R;

/**
 * Created by Minh on 3/29/2016.
 */
@SuppressLint("ValidFragment")
public class ImageFragment extends BaseFragment {
    TextView itemTitle;
    ImageView ivLike;
    TextView tvLike;
    ImageView ivPlay;
    TextView tvPlay;
    TextView tvRate;
    ProperRatingBar rbVote;
    ImageView ivCover;

    private Movie movie;
    private String url = "";

    public ImageFragment(Movie movie,String url) {
        this.movie = movie;
        this.url = url;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater
                .inflate(R.layout.image_fragment, container, false);
        itemTitle = (TextView) view.findViewById(R.id.item_title);
        ivLike = (ImageView) view.findViewById(R.id.iv_like);
        tvLike = (TextView) view.findViewById(R.id.tv_like);
        ivPlay = (ImageView) view.findViewById(R.id.iv_play);
        tvPlay = (TextView) view.findViewById(R.id.tv_play);
        tvRate = (TextView) view.findViewById(R.id.tv_rate);
        rbVote = (ProperRatingBar) view.findViewById(R.id.rb_vote);
        ivCover = (ImageView) view.findViewById(R.id.iv_cover);

        try {
            Picasso.with(getContext()).load("http://s14.com.vn" + url).into(ivCover);
            itemTitle.setText(movie.getTitle());
            tvRate.setText(movie.getRate());
            rbVote.setRating((int) Double.parseDouble(movie.getRate()));
        } catch (Exception e) {

        }

//        ivCover.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), DetailVideoActivity.class);
//                intent.putExtra("idMovie", movie.getIdMovie());
//                intent.putExtra("titleMovie", movie.getTitle());
//                intent.putExtra("rateMovie", movie.getRate());
//                intent.putExtra("bannerMovie", movie.getUrl());
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                getContext().startActivity(intent);
//            }
//        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
