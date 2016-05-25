package vn.app.phims14.Module.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import org.askerov.dynamicgrid.DynamicGridView;

import java.util.ArrayList;
import java.util.List;

import vn.app.phims14.Database.MovieDAO;
import vn.app.phims14.R;


/**
 * Created by Minh on 3/29/2016.
 */
public class HomeFragment1<T> extends BaseFragment implements ApiFilmListener {

    final static String TAG = HomeFragment1.class.getName();
    private static final int NUMBERS_OF_PAGE = 4;

    private static final int FRAGMENT_A = 0;
    private static final int FRAGMENT_B = 1;
    private static final int FRAGMENT_C = 2;
    private static final int FRAGMENT_D = 3;
    SmartTabLayout smartTabLayout;
    ViewPager viewPager;
    FrameLayout frameLayout;
    private DynamicGridView gridView;

    float xCoOrdinate, yCoOrdinate;

    private List<BaseFragment> mScreenList;
    private int mActiveScreenId = FRAGMENT_A;
    private ArrayList<MovieDAO> movieDAOs;
    private HomeAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater
                .inflate(R.layout.home_fragment, container, false);

        smartTabLayout = (SmartTabLayout) view.findViewById(R.id.indicator);
        viewPager = (ViewPager) view.findViewById(R.id.pager);
        gridView = (DynamicGridView) view.findViewById(R.id.dynamic_grid);
        frameLayout = (FrameLayout) view.findViewById(R.id.fl_slider);

        movieDAOs=new ArrayList<>();
        adapter = new HomeAdapter(getActivity(),
                movieDAOs,
                2);
        gridView.setAdapter(adapter);

        initSlider();

        gridView.setOnDragListener(new DynamicGridView.OnDragListener() {
            @Override
            public void onDragStarted(int position) {

            }

            @Override
            public void onDragPositionsChanged(int oldPosition, int newPosition) {

            }
        });
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                gridView.startEditMode(position);
                return true;
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int mLastFirstVisibleItem;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                if (mLastFirstVisibleItem < firstVisibleItem) {
                    //frameLayout.setVisibility(View.GONE);
                    Log.i("yyy", "down");
                }
                if (mLastFirstVisibleItem > firstVisibleItem) {
                    //frameLayout.setVisibility(View.VISIBLE);
                    Log.i("yyy", "up");
                }
                mLastFirstVisibleItem = firstVisibleItem;

            }
        });

        return view;
    }

    private void initSlider() {
        setup(smartTabLayout);

        FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                mActiveScreenId = position;
                return getScreen(position);
            }

            @Override
            public int getCount() {
                return NUMBERS_OF_PAGE;
            }
        };

        viewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(viewPager);
        smartTabLayout.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

            }

        });

        setupScreenList();
    }

    private void setupScreenList() {
        mScreenList = new ArrayList<>();
    /*    mScreenList.add(new ImageFragment());
        mScreenList.add(new ImageFragment());
        mScreenList.add(new ImageFragment());
        mScreenList.add(new ImageFragment());*/
    }

    private BaseFragment getScreen(int position) {
        BaseFragment screen = null;
        do {
            if (position > NUMBERS_OF_PAGE || position < 0)
                break;

            screen = mScreenList.get(position);
        } while (false);

        return screen;
    }


    public void setup(SmartTabLayout layout) {

        final LayoutInflater inflater = LayoutInflater.from(layout.getContext());
        final Resources res = layout.getContext().getResources();

        layout.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                View view = (View) inflater.inflate(R.layout.custom_tab_circle, container, false);
               /* TextView textView = (TextView) inflater.inflate(R.layout.custom_tab_icon1, container, false);
                switch (position) {
                    case 0:
                        textView.setText("PHIM LẺ");
                        break;
                    case 1:
                        textView.setText("PHIM BỘ");
                        break;
                    case 2:
                        textView.setText("PHIM CHIẾU RẠP");
                        break;
                    case 3:
                        textView.setText("PHIM IMDB");
                        break;
                    default:
                        throw new IllegalStateException("Invalid position: " + position);
                }*/
               /* TintableImageView icon = (TintableImageView) inflater.inflate(R.layout.custom_tab_icon2, container, false);
                switch (position) {
                    case 0:
                        icon.setImageResource(R.drawable.ic_launcher);
                        //icon.setImageDrawable(res.getDrawable(R.drawable.ic_launcher));
                        break;
                    case 1:
                        icon.setImageResource(R.drawable.ic_launcher);
                        break;
                    case 2:
                        icon.setImageResource(R.drawable.ic_launcher);
                        break;
                    case 3:
                        icon.setImageResource(R.drawable.ic_launcher);
                        break;
                    default:
                        throw new IllegalStateException("Invalid position: " + position);
                }*/
                return view;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        /*ButterKnife.unbind(this);*/
    }

    @Override
    public void loadContentView(int type, ArrayList data) {
        if (type == 0) {

            movieDAOs.clear();
            movieDAOs.addAll(data);
            adapter.add(movieDAOs);
        }
    }
}
