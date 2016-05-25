package vn.app.phims14.Module.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import vn.app.phims14.Classes.Actor;
import vn.app.phims14.R;
import uk.co.ribot.easyadapter.ItemViewHolder;
import uk.co.ribot.easyadapter.PositionInfo;
import uk.co.ribot.easyadapter.annotations.LayoutId;
import uk.co.ribot.easyadapter.annotations.ViewId;
import vn.app.phims14.UIComponent.CircleTransform;

/**
 * Created by Minh on 4/5/2016.
 */
@LayoutId(R.layout.item_actor)
public class ActorAdapter extends ItemViewHolder<Actor> {

    @ViewId(R.id.iv_thumb)
    ImageView ivThumb;
    @ViewId(R.id.iv_name)
    TextView ivName;

    public ActorAdapter(View view) {
        super(view);
    }

    @Override
    public void onSetValues(Actor actor, PositionInfo positionInfo) {
        if (!getItem().getImage().isEmpty()) {
            Picasso.with(getContext()).load(getItem().getImage()).
                    transform(new CircleTransform()).error(R.drawable.bk1).into(ivThumb);
        }
        ivName.setText(getItem().getName());

    }
}
