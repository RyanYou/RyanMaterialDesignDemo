package ryanyou.ryanmaterialdesigndemo.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RyanYou on 15/8/27.
 */
public abstract class BaseRecyclerAdapter<T,VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter {

    protected List<T> mData;
    protected Activity context;
    protected LayoutInflater mLayoutInflater;

    public BaseRecyclerAdapter(Activity context,List<T> mData){
        this.context = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        setData(mData);
    }

    public void setData(List<T> mData){
        this.mData = (mData != null) ? mData : new ArrayList<T>();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public abstract VH onCreateViewHolder(ViewGroup viewGroup, int viewType);

}
