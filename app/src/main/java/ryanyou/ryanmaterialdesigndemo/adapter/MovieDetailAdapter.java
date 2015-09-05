package ryanyou.ryanmaterialdesigndemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ryanyou.ryanmaterialdesigndemo.R;
import ryanyou.ryanmaterialdesigndemo.bean.TestBean;

/**
 * Created by RyanYou on 15/8/13.
 */
public class MovieDetailAdapter extends RecyclerView.Adapter {

    public static final String TAG = MovieDetailAdapter.class.getSimpleName();
    private List<TestBean> mData;
    private Context context;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    private OnPullUpRefreshListener mOnPullUpRefreshListener;
    private boolean isLoading = false;

    public void setLoading(boolean newState){
        if (newState){
            isLoading = true;
            if (mOnPullUpRefreshListener != null){
                mOnPullUpRefreshListener.onPullUpRefresh();
            }
        }else {
            isLoading = false;
        }
    }

    public interface OnPullUpRefreshListener{
        public void onPullUpRefresh();
    }

    public void setOnPullUpRefreshListener(OnPullUpRefreshListener mOnPullUpRefreshListener) {
        this.mOnPullUpRefreshListener = mOnPullUpRefreshListener;
    }

    public boolean isLoading(){
        return isLoading;
    }

    public MovieDetailAdapter(Context context, List<TestBean> mData){
        this.mData = (mData != null) ? mData : new ArrayList<TestBean>();
        this.context = context;
    }

    public void append(List<TestBean> data) {
        if (data != null) {
            this.mData.addAll(data);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mData.size()){
            return TYPE_FOOTER;
        }else {
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size() + 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case TYPE_ITEM:
                view = LayoutInflater.from(context).inflate(R.layout.item_main_activity_adapter, null);
                return new TestBeanViewHolder(view);
            case TYPE_FOOTER:
                view = LayoutInflater.from(context).inflate(R.layout.item_main_activity_adapter_footer, null);
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                return new FooterViewHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TestBeanViewHolder) {
            ((TestBeanViewHolder) holder).bindData(mData.get(position));
        }else if(holder instanceof FooterViewHolder){
            if (!isLoading) {
                setLoading(true);
            }
        }
    }

    private class TestBeanViewHolder extends RecyclerView.ViewHolder{
        ImageView iv;
        TextView tv;

        public TestBeanViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.main_adapter_iv);
            tv = (TextView) itemView.findViewById(R.id.main_adapter_tv);
        }

        public void bindData(TestBean bean){
            tv.setText(bean.content);
        }
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder{
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }



}
