package ryanyou.ryanmaterialdesigndemo.utils;


import android.content.Context;
import android.util.Log;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by RyanYou on 2016/1/20.
 */
public class DiskLruCacheUtils {

    public static final String TAG = "DiskLruCacheUtils";
    public static DiskLruCache mDiskLruCache;
    private static final int MAX_SIZE = 30 * 1024 * 1024;

    /**
     * 初始化DiskLruCache
     */
    public static void initCache(Context context, String dataType) {
        try {
            File cacheDir = CommonUtils.getDiskLruCacheDir(context, dataType);
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            int versionCode = CommonUtils.getAppVersionCode(context);
            mDiskLruCache = DiskLruCache.open(cacheDir, versionCode, 1, MAX_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 将数据写入DiskLruCache
    private static void saveDataToDiskLruCache(final List data) {
        if (mDiskLruCache == null){
            throw new IllegalStateException("you must init cache first!");
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //第一步:获取将要缓存的图片的对应唯一key值.
//                    String key = Utils.getStringByMD5(mImagePath);
                    String key = "arraylist";
                    DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                    if (editor != null) {
                        OutputStream outputStream = editor.newOutputStream(0);
                        boolean isSuccessfull = IOUtils.saveListToFile(data, outputStream);
                        if (isSuccessfull) {
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

}
