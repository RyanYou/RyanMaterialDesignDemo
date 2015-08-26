package ryanyou.ryanmaterialdesigndemo.rest;


import retrofit.RestAdapter;

/**
 * Created by Ryan.You on 2015/8/26.
 */
public class RestClient  {

    public static final String WEATHER_URL = "http://api.map.baidu.com";
    private  RestAdapter mRestAdapter;

    public RestClient(){
        mRestAdapter = new RestAdapter.Builder().setEndpoint(WEATHER_URL).build();
    }

    public MovieService getMovieService(){
         return mRestAdapter.create(MovieService.class);
    }

}
