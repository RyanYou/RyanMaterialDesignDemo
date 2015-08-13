package ryanyou.ryanmaterialdesigndemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ryanyou.ryanmaterialdesigndemo.R;
import ryanyou.ryanmaterialdesigndemo.bean.TestBean;

/**
 * Created by RyanYou on 15/8/13.
 */
public class CoordinatorLayoutDemoAdapter extends RecyclerView.Adapter {

    private List<TestBean> mData;
    private Context context;

    public CoordinatorLayoutDemoAdapter(Context context,List<TestBean> mData){
        this.mData = (mData != null) ? mData : new ArrayList<TestBean>();
        this.context = context;
    }

    @Override
    public TestBeanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_main_activity_adapter,null);
        return new TestBeanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((TestBeanViewHolder) holder).bindData(mData.get(position));
    }


    @Override
    public int getItemCount() {
        return mData.size();
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

}
