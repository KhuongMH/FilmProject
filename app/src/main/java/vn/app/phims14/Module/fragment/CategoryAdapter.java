package vn.app.phims14.Module.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import vn.app.phims14.Classes.Category;
import vn.app.phims14.Module.SearchMovieActivity;
import vn.app.phims14.R;
import uk.co.ribot.easyadapter.ItemViewHolder;
import uk.co.ribot.easyadapter.PositionInfo;
import uk.co.ribot.easyadapter.annotations.LayoutId;
import uk.co.ribot.easyadapter.annotations.ViewId;

/**
 * Created by Minh on 4/5/2016.
 */
@LayoutId(R.layout.item_category)
public class CategoryAdapter extends ItemViewHolder<Category> {
    @ViewId(R.id.tv_title_category)
    TextView tvTitleCategory;
    @ViewId(R.id.iv_icon)
    ImageView iv_Icon;

    public CategoryAdapter(View view) {
        super(view);
    }

    @Override
    public void onSetValues(final Category category, PositionInfo positionInfo) {
        tvTitleCategory.setText(category.getCategoryName());
        int productImageId = getContext().getResources().getIdentifier(
                category.getSlug().replace("-", ""), "drawable", getContext().getPackageName());
        try {
            Picasso.with(getContext()).load(productImageId).error(R.drawable.ic_movie).into(iv_Icon);
        }catch (Exception e){
            Picasso.with(getContext()).load(R.drawable.ic_movie).into(iv_Icon);
        }
        tvTitleCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchMovieActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("categorySlug", category.getSlug());
                intent.putExtra("categoryName", category.getCategoryName());
                getContext().startActivity(intent);
            }
        });
    }
}
