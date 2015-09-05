package ryanyou.ryanmaterialdesigndemo.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.blurry.Blurry;
import ryanyou.ryanmaterialdesigndemo.R;
import ryanyou.ryanmaterialdesigndemo.adapter.MovieDetailAdapter;
import ryanyou.ryanmaterialdesigndemo.bean.TestBean;
import ryanyou.ryanmaterialdesigndemo.utils.CommonUtils;

/**
 * 影片详情Activity
 * Created by RyanYou on 15/8/12.
 */
public class MovieDetailActivity extends BaseActivity {

    public static final String TAG = "MovieDetailActivity";
    private RecyclerView main_rv;
    private ImageView pic_iv;
    private CollapsingToolbarLayout collapsing_toolbar;
    private MovieDetailAdapter mAdapter;
    private Handler mHandler = new Handler();
    private int currentItemPosition = 0;

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_movie_detial);
        main_rv = (RecyclerView) findViewById(R.id.activity_movie_detail_rv);
        pic_iv = (ImageView) findViewById(R.id.activity_movie_detail_pic_iv);
        collapsing_toolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        initToolbar();
    }

    @Override
    protected void initData() {
        mAdapter = new MovieDetailAdapter(MovieDetailActivity.this, addData(currentItemPosition, 20));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        main_rv.setLayoutManager(linearLayoutManager);
        main_rv.setAdapter(mAdapter);

        Glide.with(this).load(getIntent().getStringExtra("movie_pic")).into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                pic_iv.setImageDrawable(resource);
                //对图片进行高斯模糊
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Blurry.with(MovieDetailActivity.this)
                                .radius(8)
                                .sampling(8)
                                .async()
                                .animate(200)
                                .capture(pic_iv)
                                .into((ImageView) MovieDetailActivity.this.findViewById(R.id.activity_movie_detail_blurry_iv));
                    }
                }, 500);
                //改变status bar的颜色
                changeStatusBarColor(CommonUtils.drawableToBitmap(resource));
            }
        });
    }

    @Override
    protected void initEvents() {
        mAdapter.setOnPullUpRefreshListener(new MovieDetailAdapter.OnPullUpRefreshListener() {
            @Override
            public void onPullUpRefresh() {
                simulateFetchData();
            }
        });
    }

    @Override
    public void onClick(View v) {
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
    }

    private List<TestBean> addData(int start, int count) {
        currentItemPosition = start + count;
        List<TestBean> data = new ArrayList<TestBean>();
        for (int i = start; i < currentItemPosition; i++) {
            TestBean bean = new TestBean();
            bean.content = String.valueOf(i);
            data.add(bean);
        }
        return data;
    }

    private void simulateFetchData() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.append(addData(currentItemPosition, 20));
                mAdapter.setLoading(false);
            }
        }, 2000);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NewApi")
    private void changeStatusBarColor(Bitmap bitmap) {
        Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch vibrant = palette.getDarkVibrantSwatch();
                collapsing_toolbar.setContentScrimColor(vibrant.getRgb());
                if (android.os.Build.VERSION.SDK_INT >= 21) {
                    Window window = getWindow();
                    window.setStatusBarColor(vibrant.getRgb());
                    window.setNavigationBarColor(vibrant.getRgb());
                }
            }
        });
    }

}
