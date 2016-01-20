package ryanyou.ryanmaterialdesigndemo.utils;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by RyanYou on 2016/1/20.
 */
public class IOUtils {

    private static final String TAG = "IOUtils";

    public static boolean saveListToFile(List data, OutputStream outputStream) {
        boolean result = false;
        ObjectOutputStream obj = null;
        try {
            obj = new ObjectOutputStream(outputStream);
            obj.writeObject(data);
            obj.flush();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                obj.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
