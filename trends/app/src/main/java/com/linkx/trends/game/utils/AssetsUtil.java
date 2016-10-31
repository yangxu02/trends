package com.linkx.trends.game.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by '' on 2015/7/28.
 */
public class AssetsUtil {

    private static void close(Closeable s) {
        if (null != s) {
            try {
                s.close();
            } catch (IOException e) {
            }
        }
    }

    private static ByteArrayOutputStream copyTo(Context context, String file) {
        InputStream is = null;
        try {
            AssetManager assetManager = context.getAssets();
            is = assetManager.open(file);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
            is.close();
            return os;
        } catch (IOException e) {
        } finally {
            close(is);
        }
        return null;
    }

    public static String getPath(Context context, String file) {
        return "file:///android_asset/" + file;
    }

    public static String getContent(Context context, String file) {
        ByteArrayOutputStream os = copyTo(context, file);
        if (null != os) {
            try {
                return os.toString("utf-8");
            } catch (UnsupportedEncodingException e) {
            } finally {
                close(os);
            }
        }
        return "";
    }

    public static Bitmap toBitMap(Context context, String file) {
        ByteArrayOutputStream os = copyTo(context, file);
        if (null != os) {
            try {
                byte[] data = os.toByteArray();
                return BitmapFactory.decodeByteArray(data, 0, data.length);
            } finally {
                close(os);
            }
        }
        return null;
    }
}
