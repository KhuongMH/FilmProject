package vn.app.phims14.Module.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import org.askerov.dynamicgrid.BaseDynamicGridAdapter;
import java.util.List;
import vn.app.phims14.Classes.MovieEpisode;
import vn.app.phims14.Module.VideoActivity;
import vn.app.phims14.R;

/**
 * Created by Minh on 3/29/2016.
 */
public class HomeAdapter<T> extends BaseDynamicGridAdapter {

    public HomeAdapter(Context context, List<T> items, int columnCount) {
        super(context, items, columnCount);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        VideoItem holder;
        final MovieEpisode movieEpisode = (MovieEpisode) getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_part_video, null);
            holder = new VideoItem(convertView);
            convertView.setTag(holder);
        } else {
            holder = (VideoItem) convertView.getTag();
        }
        holder.btVideo.setText(movieEpisode.getName());
        holder.btVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), VideoActivity.class);
                intent.putExtra("urlVideo", movieEpisode.getUrl());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });
        return convertView;
    }

    private class VideoItem {
        private Button btVideo;

        private VideoItem(View view) {
            btVideo = (Button) view.findViewById(R.id.bt_video);
        }

    }
}
