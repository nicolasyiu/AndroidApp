package mumaoxi.androidapp.model;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by saxer on 12/9/15.
 */
public class App {
    public Drawable icon;
    public String name;
    public String packageName;
    public String versionName;
    public int versionCode;

    /**
     * 获取应用程序列表
     *
     * @param context
     * @return
     */
    public static List<App> getApplist(Context context) {
        List<App> apps = new ArrayList<>();
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> mPacks = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        for (PackageInfo info : mPacks) {
            if ((info.applicationInfo.flags & info.applicationInfo.FLAG_SYSTEM) <= 0 && (info.packageName.contains("xiaomi") ||
                    info.packageName.contains("miui"))) {
                App app = new App();
                app.name = info.applicationInfo.loadLabel(pm).toString();
                app.packageName = info.packageName;
                app.versionCode = info.versionCode;
                app.versionName = info.versionName;
                app.icon = info.applicationInfo.loadIcon(pm);
                apps.add(app);
            }
        }
        return apps;
    }

    /**
     * @param apps
     * @param fileName
     * @throws Exception
     */
    public static void writeToCSV(List<App> apps, String fileName) throws Exception {
        FileOutputStream outputStream = new FileOutputStream(new File(Environment.getExternalStorageDirectory(), fileName));
        StringBuffer sb = new StringBuffer();
        sb.append("name,package_name,version_name,version_code\n");
        for (App app : apps) {
            sb.append(String.format("%1$s,%2$s,%3$s,%4$s\n", app.name, app.packageName, app.versionName, app.versionCode));
        }
        outputStream.write(sb.toString().getBytes());
        outputStream.close();
    }
}
