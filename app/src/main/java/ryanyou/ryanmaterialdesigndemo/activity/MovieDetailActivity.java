package ryanyou.ryanmaterialdesigndemo.activity;

import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ryanyou.ryanmaterialdesigndemo.R;
import ryanyou.ryanmaterialdesigndemo.adapter.MovieDetailAdapter;
import ryanyou.ryanmaterialdesigndemo.bean.TestBean;

/**
 * 影片详情Activity
 * Created by RyanYou on 15/8/12.
 */
public class MovieDetailActivity extends BaseActivity {

    public static final String TAG = "MovieDetailActivity";
    private RecyclerView main_rv;
    private MovieDetailAdapter mAdapter;
    private Handler mHandler = new Handler();
    private int currentItemPosition = 0;

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_movie_detial);
        main_rv = (RecyclerView) findViewById(R.id.activity_movie_detail_rv);
        initToolbar();
    }

    @Override
    protected void initData() {
        mAdapter = new MovieDetailAdapter(MovieDetailActivity.this, addData(currentItemPosition, 20));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        main_rv.setLayoutManager(linearLayoutManager);
        main_rv.setAdapter(mAdapter);
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
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
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

}
