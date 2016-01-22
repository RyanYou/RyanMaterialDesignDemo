package ryanyou.ryanmaterialdesigndemo.service;


import android.content.Context;
import android.util.Log;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import ryanyou.ryanmaterialdesigndemo.bean.HotMovieBean;
import ryanyou.ryanmaterialdesigndemo.bean.TestBean;
import ryanyou.ryanmaterialdesigndemo.utils.DiskLruCacheUtils;
import ryanyou.ryanmaterialdesigndemo.utils.IOUtils;

/**
 * Created by RyanYou on 2016/1/20.
 */
public final class MovieManager {

    public static final String TAG = "MovieManager";
    private static MovieManager instance = null;
    private DiskLruCache mDiskLruCache;
    private int mDummyCurrentItemPosition = 0;

    private MovieManager(Context context) {
        this.mDiskLruCache = DiskLruCacheUtils.getCache(context, TAG);
    }

    public static MovieManager get(Context context) {
        if (instance == null) {
            synchronized (MovieManager.class) {
                if (instance == null) {
                    instance = new MovieManager(context);
                }
            }
        }
        return instance;
    }

    public Observable<List<HotMovieBean.ResultEntity.MovieEntity>> getMovies(final boolean forceReload) {
        return Observable.create(new Observable.OnSubscribe<HotMovieBean>() {
            @Override
            public void call(final Subscriber<? super HotMovieBean> subscriber) {
                if (forceReload) {
                    RxServiceFactory.getService(MovieService.class)
                            .getHotMovieBean("hot_movie", "广州", "json", "ZxNG6jQfvzjWtbWdcVFeEXZ7")
                            .subscribe(new Action1<HotMovieBean>() {
                                @Override
                                public void call(HotMovieBean hotMovieBean) {
                                    Log.d(TAG, "getMovies() onNext!");
                                    saveDataToDiskLruCache(hotMovieBean);
                                    subscriber.onNext(hotMovieBean);
                                    subscriber.onCompleted();
                                }
                            }, new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    Log.d(TAG, "getMovies() onError " + throwable.getLocalizedMessage());
                                    subscriber.onError(throwable);
                                }
                            });
                } else {
                    Object cacheData = getDataFromDiskLruCache();
                    if (cacheData != null) {
                        Log.d(TAG, "getMovies() - getDataFromDiskLruCache , success!");
                        subscriber.onNext((HotMovieBean) cacheData);
                        subscriber.onCompleted();
                    }else {
                        subscriber.onError(new Throwable("no cache data!"));
                    }
                }
            }
        }).map(new Func1<HotMovieBean, List<HotMovieBean.ResultEntity.MovieEntity>>() {
            @Override
            public List<HotMovieBean.ResultEntity.MovieEntity> call(HotMovieBean hotMovieBean) {
                Log.d(TAG, "getMovies() map!");
                List<HotMovieBean.ResultEntity.MovieEntity> result = new ArrayList<HotMovieBean.ResultEntity.MovieEntity>();
                if (hotMovieBean != null && hotMovieBean.getResult() != null) {
                    result.addAll(hotMovieBean.getResult().getMovie());
                }
                return result;
            }
        });
    }

    public Observable<List<TestBean>> getDummyData(final int count){
        return Observable.create(new Observable.OnSubscribe<List<TestBean>>() {
            @Override
            public void call(Subscriber<? super List<TestBean>> subscriber) {
                List<TestBean> data = new ArrayList<TestBean>();
                for (int i = mDummyCurrentItemPosition; i < mDummyCurrentItemPosition + count; i++) {
                    TestBean bean = new TestBean();
                    bean.content = String.valueOf(i);
                    data.add(bean);
                }
                mDummyCurrentItemPosition += count;
                subscriber.onNext(data);
                subscriber.onCompleted();
            }
        });
    }

    public void resetDummyPosition(){
        this.mDummyCurrentItemPosition = 0;
    }

    /**
     * 将数据写入DiskLruCache
     *
     * @param data
     */
    public void saveDataToDiskLruCache(final Object data) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    String key = CommonUtils.getStringByMD5(mImagePath);
                    String key = "arraylist";
                    DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                    if (editor != null) {
                        OutputStream outputStream = editor.newOutputStream(0);
                        boolean isSuccessful = IOUtils.flushDataToOutputSteam(data, outputStream);
                        if (isSuccessful) {
                            editor.commit();
                            Log.d(TAG, "saveDataToDiskLruCache successful!");
                        } else {
                            editor.abort();
                            Log.d(TAG, "saveDataToDiskLruCache failure!");
                        }
                        mDiskLruCache.flush();
                    }
                } catch (Exception e) {
                }
            }
        }).start();
    }

    /**
     * 从DiskLruCache中读取数据
     */
    public Object getDataFromDiskLruCache() {
        Object result = null;
        try {
            String key = "arraylist";
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
            if (snapshot != null) {
                InputStream inputStream = snapshot.getInputStream(0);
                result = IOUtils.readDataFromInputSteam(inputStream);
                Log.d(TAG, "getDataFromDiskLruCache success!");
            }
        } catch (Exception e) {
            Log.d(TAG, "getDataFromDiskLruCache failure e " + e.getLocalizedMessage());
        }
        return result;
    }

}
