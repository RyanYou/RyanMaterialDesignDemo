package ryanyou.ryanmaterialdesigndemo.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by RyanYou on 15/9/5.
 */
public class CommonUtils {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        //canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 获取APP当前版本号
     *
     * @param context
     * @return
     */
    public static int getAppVersionCode(Context context) {
        int versionCode = 1;
        try {
            String packageName = context.getPackageName();
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }


        /**
         * 将字符串用MD5编码.
         * 比如在改示例中将url进行MD5编码
         */
        public static String getStringByMD5(String string) {
            String md5String = null;
            try {
                // Create MD5 Hash
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                messageDigest.update(string.getBytes());
                byte messageDigestByteArray[] = messageDigest.digest();
                if (messageDigestByteArray == null || messageDigestByteArray.length == 0) {
                    return md5String;
                }

                // Create hexadecimal String
                StringBuffer hexadecimalStringBuffer = new StringBuffer();
                int length = messageDigestByteArray.length;
                for (int i = 0; i < length; i++) {
                    hexadecimalStringBuffer.append(Integer.toHexString(0xFF & messageDigestByteArray[i]));
                }
                md5String = hexadecimalStringBuffer.toString();
                return md5String;
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return md5String;
        }

}
