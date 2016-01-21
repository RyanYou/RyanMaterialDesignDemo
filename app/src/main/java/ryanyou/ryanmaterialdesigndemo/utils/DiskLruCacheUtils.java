package ryanyou.ryanmaterialdesigndemo.utils;


import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;

/**
 * Created by RyanYou on 2016/1/20.
 */
public class DiskLruCacheUtils {

    public static final String TAG = "DiskLruCacheUtils";
    private static final int MAX_SIZE = 30 * 1024 * 1024;

    /**
     * 初始化DiskLruCache
     *
     * @param context
     * @param dataType
     */
    public static DiskLruCache getCache(Context context, String dataType) {
        DiskLruCache cache = null;
        try {
            File cacheDir = getDiskLruCacheDir(context, dataType);
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            int versionCode = CommonUtils.getAppVersionCode(context);
            cache = DiskLruCache.open(cacheDir, versionCode, 1, MAX_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cache;
    }

    /**
     * 获取DiskLruCache的缓存文件夹
     * 注意第二个参数dataType
     * DiskLruCache用一个String类型的唯一值对不同类型的数据进行区分.
     * 比如bitmap,object等文件夹.其中在bitmap文件夹中缓存了图片.
     * <p/>
     * 缓存数据的存放位置为:
     * /sdcard/Android/data//cache
     * 如果SD卡不存在时缓存存放位置为:
     * /data/data//cache
     */
    public static File getDiskLruCacheDir(Context context, String dataType) {
        String dirPath;
        File cacheFile = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
                !Environment.isExternalStorageRemovable()) {
            dirPath = context.getExternalCacheDir().getPath();
        } else {
            dirPath = context.getCacheDir().getPath();
        }
        cacheFile = new File(dirPath + File.separator + dataType);
        Log.d(TAG, String.format("cacheFile = %s", cacheFile.getAbsolutePath()));
        return cacheFile;
    }

}
