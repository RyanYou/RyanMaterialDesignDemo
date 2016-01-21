package ryanyou.ryanmaterialdesigndemo.utils;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by RyanYou on 2016/1/20.
 */
public class IOUtils {

    private static final String TAG = "IOUtils";

    public static boolean flushDataToOutputSteam(Object data, OutputStream outputStream) {
        boolean result = false;
        ObjectOutputStream ops = null;
        try {
            ops = new ObjectOutputStream(outputStream);
            ops.writeObject(data);
            ops.flush();
            result = true;
            Log.d(TAG, "flushDataToOutputSteam successful!");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "flushDataToOutputSteam failure! " + e.getLocalizedMessage());
        } finally {
            try {
                ops.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static Object readDataFromInputSteam(InputStream inputStream) {
        ObjectInputStream ois = null;
        Object result = null;
        try {
            ois = new ObjectInputStream(inputStream);
            result = ois.readObject();
            Log.d(TAG, "readDataFromInputSteam successful!");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "readDataFromInputSteam failure!" + e.getLocalizedMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.d(TAG, "readDataFromInputSteam failure! " + e.getLocalizedMessage());
        } finally {
            try {
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
