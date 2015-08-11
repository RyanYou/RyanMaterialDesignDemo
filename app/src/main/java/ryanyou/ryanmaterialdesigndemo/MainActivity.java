package ryanyou.ryanmaterialdesigndemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ryanyou.ryanmaterialdesigndemo.adapter.MainAdapter;
import ryanyou.ryanmaterialdesigndemo.bean.TestBean;

public class MainActivity extends AppCompatActivity {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView main_lv;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initData();
        initEvent();
    }

    private void initViews() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.main_activity_swipe_refresh_layout);
        setSwipeRefreshLayoutSchemeColors();
        main_lv = (ListView) findViewById(R.id.main_activity_lv);
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

    private void initData() {
        List<TestBean> data = new ArrayList<TestBean>();
        for (int i = 0; i < 20 ; i++){
            TestBean bean = new TestBean();
            bean.content = String.valueOf(i);
            data.add(bean);
        }
        main_lv.setAdapter(new MainAdapter(MainActivity.this,data));
    }

    private void initEvent() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                simulateFetchData();
            }
        });
    }

    private void simulateFetchData(){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "SimulateFetchData Finished!", Toast.LENGTH_SHORT).show();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        },2500);
    }

}
