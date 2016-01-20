package ryanyou.ryanmaterialdesigndemo.service;


import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import ryanyou.ryanmaterialdesigndemo.bean.HotMovieBean;

/**
 * Created by youhouchang on 2016/1/20.
 */
public final class MovieManager {

    private static final String TAG = "MovieManager";
    private static MovieManager instance = null;
    private MovieManager(){}

    public static MovieManager get(){
        if (instance == null){
            synchronized (MovieManager.class){
                if (instance == null){
                    instance = new MovieManager();
                }
            }
        }
        return instance;
    }

    private Observable<HotMovieBean> getHotMovieBean(){
        return Observable.create(new Observable.OnSubscribe<HotMovieBean>() {
            @Override
            public void call(final Subscriber<? super HotMovieBean> subscriber) {
                RxServiceFactory.getService(MovieService.class)
                        .getHotMovieBean("hot_movie", "广州", "json", "ZxNG6jQfvzjWtbWdcVFeEXZ7")
                        .subscribe(new Action1<HotMovieBean>() {
                            @Override
                            public void call(HotMovieBean hotMovieBean) {
                                subscriber.onNext(hotMovieBean);
                                subscriber.onCompleted();
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                subscriber.onError(throwable);
                            }
                        });
            }
        });
    }

    public Observable<List<HotMovieBean.ResultEntity.MovieEntity>> getMovies(){
        return getHotMovieBean().map(new Func1<HotMovieBean, List<HotMovieBean.ResultEntity.MovieEntity>>() {
            @Override
            public List<HotMovieBean.ResultEntity.MovieEntity> call(HotMovieBean hotMovieBean) {
                List<HotMovieBean.ResultEntity.MovieEntity> result = new ArrayList<HotMovieBean.ResultEntity.MovieEntity>();
                if (hotMovieBean.getResult() != null) {
                    result.addAll(hotMovieBean.getResult().getMovie());
                }
                return result;
            }
        });
    }

}
