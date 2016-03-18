package ryanyou.ryanmaterialdesigndemo.service;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Ryan.You on 2016/3/18.
 */
public class WeatherManager {

    public static final String TAG = "WeatherManager";
    public static WeatherManager instance = null;

    public static void main(String[] args) {
        WeatherManager.getInstance().getWeather();
    }

    public static WeatherManager getInstance() {
        if (instance == null) {
            synchronized (WeatherManager.class) {
                if (instance == null) {
                    instance = new WeatherManager();
                }
            }
        }
        return instance;
    }

    public void getWeather() {
        OkHttpClient okHttpClient = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("location","广州")
                .add("output","json")
                .add("ak","ZxNG6jQfvzjWtbWdcVFeEXZ7")
                .build();

        final Request request = new Request.Builder()
                .url("http://api.map.baidu.com/telematics/v3/weather?location=%E5%8C%97%E4%BA%AC&output=json&ak=ZxNG6jQfvzjWtbWdcVFeEXZ7")
                .get()
//                .url("http://api.map.baidu.com/telematics/v3/weather")
//                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                Log.i(TAG, String.format("onFailure e = %s", e.getLocalizedMessage()));
                System.out.println(String.format("onFailure e = %s", e.getLocalizedMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                Log.i(TAG, String.format("onResponse response = %s", response.body().toString()));
                System.out.println(String.format("onResponse response = %s", response.body().string()));
            }
        });
    }

}
