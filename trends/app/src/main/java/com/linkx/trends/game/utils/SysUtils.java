package com.linkx.trends.game.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import java.util.List;

/**
 * Created by ulyx.yang on 2016/10/30.
 */
public class SysUtils {
    private static final String GooglePlayStorePackageNameOld = "com.google.market";
    private static final String GooglePlayStorePackageNameNew = "com.android.vending";

    public static boolean hasGooglePlayInstalled(Context context) {
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packages = packageManager.getInstalledPackages(0);
        for (PackageInfo packageInfo : packages) {
            if (packageInfo.packageName.equals(GooglePlayStorePackageNameOld) ||
                packageInfo.packageName.equals(GooglePlayStorePackageNameNew)) {
                return true;
            }
        }
        return false;
    }

    public static String bundleToMarketLink(String bundle) {
        return "market://details?id=" + bundle;
    }

     public static String bundleToPlayStoreLink(String bundle) {
        return "https://play.google.com/store/apps/details?id=" + bundle;
    }

    public static void openWithMarket(Context context, String bundle) {
            Uri uri = Uri.parse(bundleToMarketLink(bundle));
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
    }
}
