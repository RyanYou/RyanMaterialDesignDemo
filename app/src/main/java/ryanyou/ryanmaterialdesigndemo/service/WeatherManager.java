package ryanyou.ryanmaterialdesigndemo.service;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;


/**
 * Created by Ryan.You on 2016/3/18.
 */
public class WeatherManager {

    public static final String TAG = "WeatherManager";
    public static WeatherManager instance = null;
    public static final String API_KEY = "ZxNG6jQfvzjWtbWdcVFeEXZ7";

    public static void main(String[] args) {
//        WeatherManager.getInstance().getWeather("广州", API_KEY);
        WeatherManager.getInstance().postWeather("广州", API_KEY);
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

    public void getWeather(String location, String apiKey) {
        OkHttpClient okHttpClient = new OkHttpClient();

        final Request request = new Request.Builder()
                .url("http://api.map.baidu.com/telematics/v3/weather?location=%E5%8C%97%E4%BA%AC&output=json&ak=ZxNG6jQfvzjWtbWdcVFeEXZ7")
                .get()
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                System.out.println(String.format("onFailure e = %s", e.getLocalizedMessage()));
            }

            @Override
            public void onResponse(Response response) throws IOException {
                System.out.println(String.format("onResponse response = %s", response.body().string()));
            }
        });
    }

    private void postWeather(String location, String apiKey) {
        OkHttpClient okHttpClient = new OkHttpClient();

        RequestBody formBody = new FormEncodingBuilder()
                .add("location", location)
                .add("output", "json")
                .add("ak", apiKey)
                .build();

        Request request = new Request.Builder()
                .url("http://api.map.baidu.com/telematics/v3/weather")
                .post(formBody)
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                System.out.println(String.format("onFailure e = %s", e.getLocalizedMessage()));
            }

            @Override
            public void onResponse(Response response) throws IOException {
                System.out.println(String.format("onResponse response = %s", response.body().string()));
            }
        });

    }

}
