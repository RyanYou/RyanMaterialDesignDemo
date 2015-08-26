package ryanyou.ryanmaterialdesigndemo.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import retrofit.Callback;
import retrofit.RetrofitError;
import ryanyou.ryanmaterialdesigndemo.R;
import ryanyou.ryanmaterialdesigndemo.bean.HotMovieBean;
import ryanyou.ryanmaterialdesigndemo.rest.RestClient;

public class HotMovieActivity extends BaseActivity {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView main_rv;
    private Handler mHandler = new Handler();

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_hot_movie);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_hot_movie_swipe_refresh_layout);
        main_rv = (RecyclerView) findViewById(R.id.activity_hot_movie_rv);
        setSwipeRefreshLayoutSchemeColors();
    }

    @Override
    protected void initData() {
        updateMovieData2();
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


    //    qt=hot_movie&location=%E5%B9%BF%E5%B7%9E&output=json&ak=ZxNG6jQfvzjWtbWdcVFeEXZ7
    private void updateMovieData2(){
        new RestClient().getMovieService().getHotMovieBean("hot_movie", "广州", "json", "ZxNG6jQfvzjWtbWdcVFeEXZ7",
        new Callback<HotMovieBean>() {

            @Override
            public void success(HotMovieBean hotMovieBean, retrofit.client.Response response) {
                Toast.makeText(ct, hotMovieBean.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(ct, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }


}
