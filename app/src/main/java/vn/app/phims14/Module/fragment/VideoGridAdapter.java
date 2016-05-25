package vn.app.phims14.Module.fragment;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.askerov.dynamicgrid.BaseDynamicGridAdapter;

import java.util.List;

import io.techery.properratingbar.ProperRatingBar;
import vn.app.phims14.Database.MovieHomeDAO;
import vn.app.phims14.Module.DetailVideoActivity;
import vn.app.phims14.R;

/**
 * Created by Minh on 3/29/2016.
 */
public class VideoGridAdapter<T> extends BaseDynamicGridAdapter {
    final static String TAG = VideoGridAdapter.class.getName();

    public VideoGridAdapter(Context context, List<T> items, int columnCount) {
        super(context, items, columnCount);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        VideoItem holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_grid, null);
            holder = new VideoItem(convertView);
            convertView.setTag(holder);
        } else {
            holder = (VideoItem) convertView.getTag();
        }





        holder.tvRate.setText(((MovieHomeDAO) getItem(position)).getPointIMDB());
        holder.rbVote.setRating((int) Double.parseDouble(((MovieHomeDAO) getItem(position)).getPointIMDB()) / 2);
        holder.titleText.setText(((MovieHomeDAO) getItem(position)).getNameViet());

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailVideoActivity.class);
                intent.putExtra("idMovie", ((MovieHomeDAO) getItem(position)).getID());
                //intent.putExtra("urlVideo", ((MovieSeriesDAO) getItem(position)).getLink());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });
        return convertView;
    }

    private class VideoItem {
        private TextView titleText;
        private ImageView image;
        TextView tvRate;
        ProperRatingBar rbVote;
        private Button btVideo;

        private VideoItem(View view) {

            titleText = (TextView) view.findViewById(R.id.item_title);
            image = (ImageView) view.findViewById(R.id.item_img);
            tvRate = (TextView) view.findViewById(R.id.tv_rate);
            rbVote = (ProperRatingBar) view.findViewById(R.id.rb_vote);
        }

        void build(int position) {
            try {

                Log.d(TAG, ((MovieHomeDAO) getItem(position)).getNameViet());
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
        }
    }
}
