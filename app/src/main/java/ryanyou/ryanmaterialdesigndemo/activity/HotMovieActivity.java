package ryanyou.ryanmaterialdesigndemo.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import ryanyou.ryanmaterialdesigndemo.R;
import ryanyou.ryanmaterialdesigndemo.bean.HotMovieBean;
import ryanyou.ryanmaterialdesigndemo.service.MovieService;
import ryanyou.ryanmaterialdesigndemo.service.RxServiceFactory;

public class HotMovieActivity extends BaseActivity {

    private static final String TAG = HotMovieActivity.class.getName();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView main_rv;

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_hot_movie);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_hot_movie_swipe_refresh_layout);
        main_rv = (RecyclerView) findViewById(R.id.activity_hot_movie_rv);
        setSwipeRefreshLayoutSchemeColors();
    }

    @Override
    protected void initData() {
        updateMovieData();
    }

    @Override
    protected void initEvents() {

    }

    @Override
    public void onClick(View v) {

    }

    private void setSwipeRefreshLayoutSchemeColors(){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch swatch1 = palette.getVibrantSwatch();
                Palette.Swatch swatch2 = palette.getLightVibrantSwatch();
                Palette.Swatch swatch3 = palette.getDarkVibrantSwatch();
                mSwipeRefreshLayout.setColorSchemeColors(swatch1.getRgb(), swatch2.getRgb());
            }
        });
    }


    private void updateMovieData(){
         RxServiceFactory.getService(MovieService.class).getHotMovieBean("hot_movie", "广州", "json", "ZxNG6jQfvzjWtbWdcVFeEXZ7")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HotMovieBean>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted");
                        dismissProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError");
                        dismissProgressDialog();
                    }

                    @Override
                    public void onNext(HotMovieBean hotMovieBean) {
                        Log.i(TAG, "onNext = " + hotMovieBean.toString());
                        Toast.makeText(HotMovieActivity.this, "onNext " + hotMovieBean.getStatus().toString(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        Log.i(TAG, "onStart");
                        showProgressDialog();
                    }
                });
    }


}
