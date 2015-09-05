package ryanyou.ryanmaterialdesigndemo.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import ryanyou.ryanmaterialdesigndemo.R;
import ryanyou.ryanmaterialdesigndemo.adapter.HotMovieAdapter;
import ryanyou.ryanmaterialdesigndemo.bean.HotMovieBean;
import ryanyou.ryanmaterialdesigndemo.service.MovieService;
import ryanyou.ryanmaterialdesigndemo.service.RxServiceFactory;
import ryanyou.ryanmaterialdesigndemo.ui.recyclerview.BaseRecyclerAdapter;
import ryanyou.ryanmaterialdesigndemo.ui.recyclerview.BaseSpaceItemDecoration;
import ryanyou.ryanmaterialdesigndemo.utils.CommonUtils;

/**
 * 首页热门电影Activity
 * Created by RyanYou
 */
public class HotMovieActivity extends BaseActivity {

    private static final String TAG = HotMovieActivity.class.getName();
    private RecyclerView main_rv;
    private HotMovieAdapter mHotMovieAdapter;
    private List<HotMovieBean.ResultEntity.MovieEntity> mDataSource;

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_hot_movie);
        main_rv = (RecyclerView) findViewById(R.id.activity_hot_movie_rv);
        mDataSource = new ArrayList<>();
        mHotMovieAdapter = new HotMovieAdapter(HotMovieActivity.this, mDataSource);
        GridLayoutManager layoutManager = new GridLayoutManager(ct, 3);
        main_rv.setLayoutManager(layoutManager);
        main_rv.addItemDecoration(new BaseSpaceItemDecoration(CommonUtils.dip2px(this, 8)));
        main_rv.setHasFixedSize(true);
        main_rv.setAdapter(mHotMovieAdapter);
    }

    @Override
    protected void initData() {
        updateMovieData();
    }

    @Override
    protected void initEvents() {
        mHotMovieAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(final int position) {
                if (mDataSource != null) {
                    Intent intent = new Intent(HotMovieActivity.this, MovieDetailActivity.class);
                    intent.putExtra("movie_pic",mDataSource.get(position).getMovie_picture());
                    intent.putExtra("movie_big_pic",mDataSource.get(position).getMovie_big_picture());
                    startActivity(intent);
                }
            }
        });
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
                        Toast.makeText(ct,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(HotMovieBean hotMovieBean) {
                        Log.i(TAG, "onNext = " + hotMovieBean.toString());
                        if (hotMovieBean.getResult() != null) {
                            List<HotMovieBean.ResultEntity.MovieEntity> newData = hotMovieBean.getResult().getMovie();
                            mHotMovieAdapter.append(newData);
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



}
