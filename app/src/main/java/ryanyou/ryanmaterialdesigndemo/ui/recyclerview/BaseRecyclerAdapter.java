package ryanyou.ryanmaterialdesigndemo.ui.recyclerview;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RyanYou on 15/8/27.
 */
public abstract class BaseRecyclerAdapter<T,VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter {

    private static final String TAG = BaseRecyclerAdapter.class.getSimpleName();
    protected List<T> mData;
    protected Activity context;
    protected LayoutInflater mLayoutInflater;
    protected OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface OnItemClickListener{
        void onClick(int position);
    }

    public BaseRecyclerAdapter(Activity context,List<T> mData){
        this.context = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        setData(mData);
    }

    public void setData(List<T> mData){
        this.mData = (mData != null) ? mData : new ArrayList<T>();
    }

    public void append(List<T> newData){
        mData.addAll(newData);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public abstract VH onCreateViewHolder(ViewGroup viewGroup, int viewType);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (mOnItemClickListener != null && holder.itemView != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(position);
                }
            });
        }
    }

}
