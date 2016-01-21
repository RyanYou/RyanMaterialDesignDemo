package ryanyou.ryanmaterialdesigndemo.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.List;
import java.util.concurrent.TimeUnit;

import jp.wasabeef.blurry.Blurry;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import ryanyou.ryanmaterialdesigndemo.R;
import ryanyou.ryanmaterialdesigndemo.adapter.MovieDetailAdapter;
import ryanyou.ryanmaterialdesigndemo.bean.TestBean;
import ryanyou.ryanmaterialdesigndemo.service.MovieManager;
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
    private Subscription mGetDummyDataSubscription;
    private static final int INCREASE_DATA_COUNT = 20;

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
        mAdapter = new MovieDetailAdapter(MovieDetailActivity.this, null);
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
        simulateFetchData(0);
    }

    @Override
    protected void initEvents() {
        mAdapter.setOnPullUpRefreshListener(new MovieDetailAdapter.OnPullUpRefreshListener() {
            @Override
            public void onPullUpRefresh() {
                simulateFetchData(2);
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
    }

    private void simulateFetchData(long delay) {
        mGetDummyDataSubscription = MovieManager.get(this).getDummyData(INCREASE_DATA_COUNT)
                .delay(delay, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<TestBean>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(List<TestBean> testBeen) {
                        mAdapter.append(testBeen);
                        mAdapter.setLoading(false);
                    }
                });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mGetDummyDataSubscription != null && mGetDummyDataSubscription.isUnsubscribed()) {
            mGetDummyDataSubscription.unsubscribe();
            mGetDummyDataSubscription = null;
        }
    }
}
