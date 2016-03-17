package ryanyou.ryanmaterialdesigndemo.service;


import android.util.Log;

import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Ryan.You on 2015/8/26.
 */
public class RxServiceFactory {

    private static final String TAG = "RxServiceFactory";
    private static final String BAIDU_API_PATH = "http://api.map.baidu.com";

    private RxServiceFactory() {
    }

    public static <TService> TService getService(Class<? extends TService> cls) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setErrorHandler(new DefaultErrorHandler())
                .setEndpoint(BAIDU_API_PATH)
                .build();
        return restAdapter.create(cls);
    }

    private static class DefaultErrorHandler implements ErrorHandler{
        @Override
        public Throwable handleError(RetrofitError cause) {

            Response response = cause.getResponse();
            response.getStatus();

            // 身份信息过期，重新登录
            if (response.getStatus() == 401) {
                Log.i(TAG, "Session timeout, re-login...");
            }

            return null;
        }
    }


}
