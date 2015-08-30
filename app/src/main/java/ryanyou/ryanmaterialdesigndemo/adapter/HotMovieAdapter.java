package ryanyou.ryanmaterialdesigndemo.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import ryanyou.ryanmaterialdesigndemo.R;
import ryanyou.ryanmaterialdesigndemo.bean.HotMovieBean;

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
//        RelativeLayout parentlayout = (RelativeLayout) itemView.findViewById(R.id.item_hot_movie_parent_rl);
//        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(context.getWindowManager().getDefaultDisplay().getWidth(), 1000);
//        param.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 1);
//        parentlayout.setLayoutParams(param);
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
        ImageView iv;

        public HotMovieViewHolder(View itemView) {
            super(itemView);
            tv = (TextView)  itemView.findViewById(R.id.item_hot_movie_name_tv);
            iv = (ImageView) itemView.findViewById(R.id.item_hot_movie_img_iv);
            Glide.with(context)
                    .load("http://nuuneoi.com/uploads/source/playstore/cover.jpg")
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(iv);

        }
    }

}
