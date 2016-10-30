package com.linkx.trends.game.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

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
}
