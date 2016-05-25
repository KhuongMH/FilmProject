package vn.app.phims14.Module.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import uk.co.ribot.easyadapter.ItemViewHolder;
import uk.co.ribot.easyadapter.PositionInfo;
import uk.co.ribot.easyadapter.annotations.LayoutId;
import uk.co.ribot.easyadapter.annotations.ViewId;
import vn.app.phims14.Database.CommentDAO;
import vn.app.phims14.R;
import vn.app.phims14.UIComponent.CircleTransform;

/**
 * Created by Minh on 4/7/2016.
 */
@LayoutId(R.layout.item_comment)
public class CommentAdapter extends ItemViewHolder<CommentDAO> {

    @ViewId(R.id.iv_avatar)
    ImageView ivAvatar;
    @ViewId(R.id.tv_user_name)
    TextView tvUserName;
    @ViewId(R.id.ll_wrapper)
    LinearLayout llWrapper;
    @ViewId(R.id.tv_user_date)
    TextView tvUserDate;
    @ViewId(R.id.tv_user_content)
    TextView tvUserContent;

    public CommentAdapter(View view) {
        super(view);
    }

    @Override
    public void onSetValues(CommentDAO commentDAO, PositionInfo positionInfo) {

        tvUserDate.setText(getItem().getDate());
        tvUserContent.setText(getItem().getContent());
        String[] str=getItem().getIDUser().split(",");
        tvUserName.setText(str[0]);
        try {
            Picasso.with(getContext()).load(str[2]).transform(new CircleTransform()).error(R.drawable.ic_logo_s14).into(ivAvatar);
        }catch (Exception e){
            Picasso.with(getContext()).load(R.drawable.ic_logo_s14).transform(new CircleTransform()).into(ivAvatar);
        }
    }
}
