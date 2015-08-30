package ryanyou.ryanmaterialdesigndemo.activity;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import ryanyou.ryanmaterialdesigndemo.R;
import ryanyou.ryanmaterialdesigndemo.adapter.HotMovieAdapter;
import ryanyou.ryanmaterialdesigndemo.bean.HotMovieBean;
import ryanyou.ryanmaterialdesigndemo.service.MovieService;
import ryanyou.ryanmaterialdesigndemo.service.RxServiceFactory;

/**
 * 首页热门电影Activity
 */
public class HotMovieActivity extends BaseActivity {

    private static final String TAG = HotMovieActivity.class.getName();
    private RecyclerView main_rv;
    private HotMovieAdapter mHotMovieAdapter;

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_hot_movie);
        main_rv = (RecyclerView) findViewById(R.id.activity_hot_movie_rv);
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

    private void updateMovieData() {
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
                        if (hotMovieBean.getResult() != null) {
                            List<HotMovieBean.ResultEntity.MovieEntity> list = hotMovieBean.getResult().getMovie();
                            mHotMovieAdapter = new HotMovieAdapter(HotMovieActivity.this, list);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ct,LinearLayoutManager.HORIZONTAL,true);
                            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//                            GridLayoutManager gridLayoutManager = new GridLayoutManager(ct,1);
                            main_rv.setLayoutManager(linearLayoutManager);
                            main_rv.setItemAnimator(new DefaultItemAnimator());
                            main_rv.setAdapter(mHotMovieAdapter);
                        }
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        Log.i(TAG, "onStart");
                        showProgressDialog();
                    }
                });
    }


    private void testCommit(){
        Log.i(TAG,"Hello");
    }

}
