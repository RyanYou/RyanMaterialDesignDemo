package ryanyou.ryanmaterialdesigndemo.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import jp.wasabeef.blurry.Blurry;
import ryanyou.ryanmaterialdesigndemo.R;
import ryanyou.ryanmaterialdesigndemo.bean.HotMovieBean;
import ryanyou.ryanmaterialdesigndemo.ui.recyclerview.BaseRecyclerAdapter;

/**
 * Created by RyanYou on 15/8/27.
 */
public class HotMovieAdapter extends BaseRecyclerAdapter<HotMovieBean.ResultEntity.MovieEntity, HotMovieAdapter.HotMovieViewHolder> {

    public HotMovieAdapter(Activity context, List<HotMovieBean.ResultEntity.MovieEntity> mData) {
        super(context, mData);
    }

    @Override
    public HotMovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_hot_movie, null);
        return new HotMovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof HotMovieViewHolder) {
            HotMovieBean.ResultEntity.MovieEntity entity = mData.get(position);
            HotMovieViewHolder h = (HotMovieViewHolder) holder;
            h.tv.setText(entity.getMovie_name());
            Glide.with(context)
                    .load(entity.getMovie_picture())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(h.iv);
        }
    }

    class HotMovieViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        ImageView iv;
        RelativeLayout rootLayout;

        public HotMovieViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.item_hot_movie_name_tv);
            iv = (ImageView) itemView.findViewById(R.id.item_hot_movie_img_iv);
        }
    }


}
