package com.linkx.trends.game.utils;

import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;
import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Created by ulyx.yang on 2016/9/16.
 */
public class IOUtil {
    private final static String DAY_FORMAT = "yyyyMMdd";
    private final static String DATA_DIR = ".trends/data";
    private final static String TMP_DIR = ".trends/tmp";

    public static String dataFileDir() {
        File dir = Environment.getExternalStorageDirectory();
        return Joiner.on('/').join(dir.getAbsolutePath(), DATA_DIR);
    }

    public static String dataFileName(String tag) {
        return dataFileName(tag, System.currentTimeMillis());
    }

    public static String gameDataFileName(String tag) {
        File dir = Environment.getExternalStorageDirectory();
        return Joiner.on('/').join(dir.getAbsolutePath(), DATA_DIR, tag);
    }

    public static String dataFileName(String tag, String dayStr) {
        File dir = Environment.getExternalStorageDirectory();
        return Joiner.on('/').join(dir.getAbsolutePath(), DATA_DIR, dayStr, tag);
    }

    public static String dataFileName(String tag, long mills) {
        String dayStr = DateFormat.format(DAY_FORMAT, mills).toString();
        return dataFileName(tag, dayStr);
    }

    public static String tmpFileName(String tag) {
        File dir = Environment.getExternalStorageDirectory();
        return Joiner.on('/').join(dir.getAbsolutePath(), TMP_DIR, tag);
    }


    public static String dayStr(long mills) {
        return DateFormat.format(DAY_FORMAT, mills).toString();
    }

    public static void appendLine(String fileName, String data) throws IOException {
        File file = new File(fileName);
        Files.createParentDirs(file);
        Files.append(data + "\n", file, Charsets.UTF_8);
    }

    public static void writeLine(String fileName, String data) throws IOException {
        File file = new File(fileName);
        Files.createParentDirs(file);
        Files.write(data + "\n", file, Charsets.UTF_8);
    }

    public static String readFirstLine(String fileName) throws IOException {
        File file = new File(fileName);
        return Files.readFirstLine(file, Charsets.UTF_8);
    }

    public static void truncate(String fileName) throws IOException {
        File file = new File(fileName);
        Files.write("\r", file, Charsets.UTF_8);
    }

    public static String readFromNetworkAndCache(String url, String fileName) {
        String content = IOUtil.readFromNetwork(url);
        if (!Strings.isNullOrEmpty(content)) {
            try {
                File file = new File(fileName);
                Files.createParentDirs(file);
                Files.write(content, file, Charsets.UTF_8);
            } catch (IOException e) {
                Log.w("Trends", e);
            }
        }
        return content;
    }

    public static String readFromNetwork(String url) {
        return readFromNetwork(url, 3);
    }

    public static String readFromNetwork(String url, int retries) {
        if (Strings.isNullOrEmpty(url)) return "";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
            .url(url)
            .build();
        int i = 0;
        while (i < retries) {
            try {
                Response response = client.newCall(request).execute();
                if (response.code() == 200) {
                    String content = response.body().string();
                    Log.d("Trends", StringEscapeUtils.unescapeJava(content));
                    if (!content.contains("dnserror")) {
                        return content;
                    }
                }
            } catch (Exception e) {
                Log.w("Trends", e);
            }
            ++i;
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                Log.w("Trends", e);
            }
        }
        return "";
    }
}
