package ryanyou.ryanmaterialdesigndemo.utils;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;


import java.lang.reflect.Method;

/**
 * LeakCanaryHelper
 * Created by youhouchang on 15/11/29.
 */
public class LeakCanaryHelper {

    private static final String TAG = "LeakCanaryHelper";
    private Object mRefWatcher;
    private static LeakCanaryHelper instance;

    private LeakCanaryHelper() {
    }

    public static LeakCanaryHelper getInstance(Application app) {
        if (instance == null) {
            synchronized (LeakCanaryHelper.class) {
                if (instance == null) {
                    instance = new LeakCanaryHelper();
                    instance.install(app);
                }
            }
        }
        return instance;
    }

    public void watch(Object watchReference) {
        this.watch(watchReference, "");
    }

    public void watch(@NonNull Object watchReference, @NonNull String referenceName) {
        if (mRefWatcher != null) {
            try {
                Class<?> watcherClass = Class.forName("com.squareup.leakcanary.RefWatcher");
                Method method = watcherClass.getMethod("watch", Object.class, String.class);
                method.invoke(mRefWatcher, watchReference, referenceName);
            } catch (Exception e) {
                Log.e(TAG,"leakcanary watch error!");
                e.printStackTrace();
            }
        } else {
            return;
        }
    }

    private void install(Application app){
        Class<?> leakcanary = null;
        try {
            leakcanary = Class.forName("com.squareup.leakcanary.LeakCanary");
            Method method = leakcanary.getMethod("install", Application.class);
            mRefWatcher = method.invoke(null,app);
            Log.d(TAG,"installByReflect install success!");
        } catch (Exception e) {
            Log.e(TAG,"installByReflect error!");
            e.printStackTrace();
        }
    }

}
