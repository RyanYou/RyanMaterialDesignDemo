package ryanyou.ryanmaterialdesigndemo.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;
import java.util.concurrent.TimeUnit;

import jp.wasabeef.blurry.Blurry;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import ryanyou.ryanmaterialdesigndemo.R;
import ryanyou.ryanmaterialdesigndemo.adapter.MovieDetailAdapter;
import ryanyou.ryanmaterialdesigndemo.bean.TestBean;
import ryanyou.ryanmaterialdesigndemo.service.MovieManager;
import ryanyou.ryanmaterialdesigndemo.utils.BitmapLruCacheHelper;
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
    private Subscription mGetDummyDataSubscription;
    private static final int INCREASE_DATA_COUNT = 20;
    private String mMoviePicUrl;

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
        simulateFetchData(0);
        mMoviePicUrl = getIntent().getStringExtra("movie_pic");

        loadThumbnail()
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<GlideDrawable>() {
                    @Override
                    public void call(final GlideDrawable resource) {
                        Blurry.with(MovieDetailActivity.this)
                                .radius(8)
                                .sampling(8)
                                .async()
                                .animate(200)
                                .capture(pic_iv)
                                .into((ImageView) MovieDetailActivity.this.findViewById(R.id.activity_movie_detail_blurry_iv));
                        changeStatusBarColor(CommonUtils.drawableToBitmap(resource)); //改变status bar的颜色
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d(TAG, String.format("loadThumbnail onError %s", throwable.getLocalizedMessage()));
                    }
                });
    }

    private Observable<GlideDrawable> loadThumbnail() {
        return Observable.create(new Observable.OnSubscribe<GlideDrawable>() {
            @Override
            public void call(final Subscriber<? super GlideDrawable> subscriber) {
                Glide.with(MovieDetailActivity.this).load(mMoviePicUrl)
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                subscriber.onError(e);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                subscriber.onNext(resource);
                                subscriber.onCompleted();
                                return false;
                            }
                        })
                        .into(pic_iv);
            }
        });
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
    private void changeStatusBarColor(final Bitmap bitmap) {
        Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                try {
                    Log.d(TAG, "changeStatusBarColor onGenerated!");
                    Palette.Swatch vibrant = palette.getDarkVibrantSwatch();
                    collapsing_toolbar.setContentScrimColor(vibrant.getRgb());
                    if (android.os.Build.VERSION.SDK_INT >= 21) {
                        Window window = getWindow();
                        window.setStatusBarColor(vibrant.getRgb());
                        window.setNavigationBarColor(vibrant.getRgb());
                    }
                } catch (Exception e) {
                    Log.d(TAG, String.format("changeStatusBarColor error! %s", e.getLocalizedMessage()));
                } finally {
                    BitmapLruCacheHelper.getMemoryCache().put(mMoviePicUrl, bitmap);
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
        MovieManager.get(this).resetDummyPosition();
    }
}
