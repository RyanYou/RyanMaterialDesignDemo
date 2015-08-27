package ryanyou.ryanmaterialdesigndemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ryanyou.ryanmaterialdesigndemo.R;
import ryanyou.ryanmaterialdesigndemo.bean.HotMovieBean;

/**
 * Created by RyanYou on 15/8/27.
 */
public class HotMovieAdapter extends BaseRecyclerAdapter<HotMovieBean.ResultEntity.MovieEntity, HotMovieAdapter.HotMovieViewHolder> {

    public HotMovieAdapter(Context context, List<HotMovieBean.ResultEntity.MovieEntity> mData) {
        super(context, mData);
    }

    @Override
    public HotMovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_hot_movie, null);
        return new HotMovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof HotMovieViewHolder) {
            HotMovieBean.ResultEntity.MovieEntity entity = mData.get(position);
            ((HotMovieViewHolder) holder).tv.setText(entity.getMovie_name());
        }
    }

    class HotMovieViewHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public HotMovieViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.item_hot_movie_name_tv);
        }
    }

}
