package ryanyou.ryanmaterialdesigndemo.rest;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import ryanyou.ryanmaterialdesigndemo.bean.HotMovieBean;

/**
 * Created by Administrator on 2015/8/26.
 */
public interface MovieService {

    @GET("/telematics/v3/movie")
    public void getHotMovieBean(
            @Query("qt") String qt,
            @Query("location") String location,
            @Query("output") String output,
            @Query("ak") String ak,
            Callback<HotMovieBean> callback);


}
