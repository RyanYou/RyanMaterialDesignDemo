package ryanyou.ryanmaterialdesigndemo;

import android.app.Application;

import ryanyou.ryanmaterialdesigndemo.utils.LeakCanaryHelper;

/**
 * Created by RyanYou on 2016/1/22.
 */
public class BaseApp extends Application{

    public LeakCanaryHelper mLeakCanaryHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        mLeakCanaryHelper = LeakCanaryHelper.getInstance(this);
    }
}
