package ryanyou.ryanmaterialdesigndemo.service;


import retrofit.RestAdapter;

/**
 * Created by Ryan.You on 2015/8/26.
 */
public class RxServiceFactory {

    private static final String WEATHER_URL = "http://api.map.baidu.com";

    private RxServiceFactory(){
    }

    public static <TService> TService getService(Class <? extends TService> cls){
         RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(WEATHER_URL).build();
         return restAdapter.create(cls);
    }


}
