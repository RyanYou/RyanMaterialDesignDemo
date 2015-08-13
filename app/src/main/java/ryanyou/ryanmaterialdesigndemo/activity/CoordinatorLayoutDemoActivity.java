package ryanyou.ryanmaterialdesigndemo.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ryanyou.ryanmaterialdesigndemo.R;
import ryanyou.ryanmaterialdesigndemo.adapter.CoordinatorLayoutDemoAdapter;
import ryanyou.ryanmaterialdesigndemo.adapter.MainAdapter;
import ryanyou.ryanmaterialdesigndemo.bean.TestBean;

/**
 * Created by RyanYou on 15/8/12.
 */
public class CoordinatorLayoutDemoActivity extends AppCompatActivity{

    private RecyclerView main_rv;

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

    private void initData() {
        List<TestBean> data = new ArrayList<TestBean>();
        for (int i = 0; i < 20 ; i++){
            TestBean bean = new TestBean();
            bean.content = String.valueOf(i);
            data.add(bean);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        main_rv.setLayoutManager(linearLayoutManager);
        main_rv.setAdapter(new CoordinatorLayoutDemoAdapter(CoordinatorLayoutDemoActivity.this,data));

    }

    private void initEvents() {

    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        collapsingToolbar.setTitle("Ryan Coordinator Layout Demo~");
    }

}
