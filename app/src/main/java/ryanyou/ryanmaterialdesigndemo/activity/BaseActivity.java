package ryanyou.ryanmaterialdesigndemo.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by RyanYou on 15/8/16.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = BaseActivity.class.getSimpleName();
    protected Context ct;
    protected ProgressDialog mProgressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ct = this;
        initViews();
        initData();
        initEvents();
    }

    /**
     * 初始化控件
     */
    protected abstract void initViews();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化事件
     */
    protected abstract void initEvents();

    protected void showProgressDialog(){
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(ct);
            mProgressDialog.setMessage("加载中。。");
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
        mProgressDialog.show();
    }

    protected void dismissProgressDialog(){
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

}
