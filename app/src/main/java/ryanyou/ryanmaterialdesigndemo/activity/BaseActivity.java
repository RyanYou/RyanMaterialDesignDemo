package ryanyou.ryanmaterialdesigndemo.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by RyanYou on 15/8/16.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = BaseActivity.class.getSimpleName();
    protected RequestQueue mRequestQueue;
    protected Context ct;
    protected ProgressDialog mProgressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ct = this;
        initViews();
        mRequestQueue = Volley.newRequestQueue(this);
        initData();
    }

    protected  interface OnVolleyStartListener{
        public void onStart();
    }

    /**
     * 利用Volley发送请求
     * @param method
     * @param url
     * @param listener
     * @param errorListener
     * @param onVolleyStartListener
     */
    protected void loadData(int method,String url,Response.Listener<String> listener,Response.ErrorListener errorListener,OnVolleyStartListener onVolleyStartListener){
        StringRequest request = new StringRequest(Request.Method.GET, url,listener,errorListener);
        request.setTag(TAG);
        if (onVolleyStartListener != null) onVolleyStartListener.onStart();
        mRequestQueue.add(request);
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
