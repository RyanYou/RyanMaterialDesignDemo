package ryanyou.ryanmaterialdesigndemo.utils;


import android.content.Context;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by RyanYou on 2016/1/20.
 */
public class DiskLruCacheUtils {

    public DiskLruCache mDiskLruCache;
    private static final int MAX_SIZE = 30 * 1024 * 1024;

    /**
     * 初始化DiskLruCache
     */
    public void getCache(Context context, String dataType) {
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
    private void saveDataToDiskLruCache(final String key) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //第一步:获取将要缓存的图片的对应唯一key值.
//                    String key = Utils.getStringByMD5(mImagePath);
                    //第二步:获取DiskLruCache的Editor
                    DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                    if (editor != null) {
                        //第三步:从Editor中获取OutputStream
                        OutputStream outputStream = editor.newOutputStream(0);
                        //第四步:下载网络图片且保存至DiskLruCache图片缓存中
                        boolean isSuccessfull= false;
                        if (isSuccessfull) {
                            editor.commit();
                        } else {
                            editor.abort();
                        }
                        mDiskLruCache.flush();
                    }
                } catch (Exception e) {

                }
            }
        }).start();
    }

}
