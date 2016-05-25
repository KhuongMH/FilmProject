package vn.app.phims14.Module.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import uk.co.ribot.easyadapter.ItemViewHolder;
import uk.co.ribot.easyadapter.PositionInfo;
import uk.co.ribot.easyadapter.annotations.LayoutId;
import uk.co.ribot.easyadapter.annotations.ViewId;
import vn.app.phims14.Database.MovieDAO;
import vn.app.phims14.Module.YoutubePlayerActivity;
import vn.app.phims14.R;

/**
 * Created by Minh on 4/12/2016.
 */
@LayoutId(R.layout.item_part_video)
public class ItemVideoButtonAdapter extends ItemViewHolder<MovieDAO> {
    @ViewId(R.id.bt_video)
    Button btVideo;

    public ItemVideoButtonAdapter(View view) {
        super(view);
    }

    @Override
    public void onSetValues(MovieDAO o, PositionInfo positionInfo) {
        btVideo.setText("Trailer "+String.valueOf(positionInfo.getPosition()));
        btVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(),getItem().getTrailer(),Toast.LENGTH_SHORT).show();
                try {
                    Intent intent = new Intent(getContext(), YoutubePlayerActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    String trailer = getItem().getTrailer().replace("https://www.youtube.com/watch?v=", "");
                    intent.putExtra("VideoId", trailer);
                    getContext().startActivity(intent);
                }catch (Exception e){
                    Log.d("ItemVideoButtonAdapter",e.toString());
                }
            }
        });
    }
}
