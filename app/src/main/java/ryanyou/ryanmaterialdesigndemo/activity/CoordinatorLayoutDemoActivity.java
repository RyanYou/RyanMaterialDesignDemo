package ryanyou.ryanmaterialdesigndemo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ryanyou.ryanmaterialdesigndemo.R;
import ryanyou.ryanmaterialdesigndemo.adapter.CoordinatorLayoutDemoAdapter;
import ryanyou.ryanmaterialdesigndemo.bean.TestBean;

/**
 * Created by RyanYou on 15/8/12.
 */
public class CoordinatorLayoutDemoActivity extends AppCompatActivity{

    public static final String TAG = "CoordinatorLayoutDemoActivity";
    private RecyclerView main_rv;
    private CoordinatorLayoutDemoAdapter mAdapter;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout_demo);
        initViews();
        initData();
        initEvents();
    }

    private void initViews() {
        main_rv = (RecyclerView) findViewById(R.id.coordinator_activity_rv);
        initToolbar();
    }

    private int currentItemPosition = 0;
    private void initData() {
        mAdapter = new CoordinatorLayoutDemoAdapter(CoordinatorLayoutDemoActivity.this,addData(currentItemPosition,20));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        main_rv.setLayoutManager(linearLayoutManager);
        main_rv.setItemAnimator(new DefaultItemAnimator());
        main_rv.setAdapter(mAdapter);

    }

    private void initEvents() {
        mAdapter.setOnPullUpRefreshListener(new CoordinatorLayoutDemoAdapter.OnPullUpRefreshListener() {
            @Override
            public void onPullUpRefresh() {
                simulateFetchData();
            }
        });
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        collapsingToolbar.setTitle("Ryan Coordinator Layout Demo~");
    }

    private List<TestBean> addData(int start , int count){
        currentItemPosition = start + count;
        List<TestBean> data = new ArrayList<TestBean>();
        for (int i = start; i <  currentItemPosition ; i++){
            TestBean bean = new TestBean();
            bean.content = String.valueOf(i);
            data.add(bean);
        }
        return data;
    }

    private void simulateFetchData(){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.append(addData(currentItemPosition, 20));
                mAdapter.setLoading(false);
            }
        },4000);
    }


}
