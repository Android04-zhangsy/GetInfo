package feicuiedu.getinfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/19.
 */
public class AppInfomation {
    /**
     * 获取应用信息
     */
    private Context              mContext;
    public  List<PackageInfo>    mInfoList;
    public  ArrayList<ImageView> mListIcon;
    public  ArrayList<TextView>  mListPakageName;
    public  ArrayList<TextView>  mListVersionName;
    public  ArrayList<TextView>  mListAppName;

    public AppInfomation(Context context) {
        mContext = context;
        mInfoList = new ArrayList<>();
        mListIcon = new ArrayList<>();
        mListAppName = new ArrayList<>();
        mListPakageName = new ArrayList<>();
        mListVersionName = new ArrayList<>();
    }

    /**
     * 获取具体信息
     */
    public void getAppInfo() {
        PackageManager packageManager = mContext.getPackageManager();
        //获取应用信息
        mInfoList = packageManager.getInstalledPackages(0);
        for (int i = 0; i < mInfoList.size(); i++) {

            PackageInfo packageInfo = mInfoList.get(i);
            ApplicationInfo appInfo = packageInfo.applicationInfo;
            if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                //判断是否是自己安装的应用
                Drawable icon = appInfo.loadIcon(packageManager);
                ImageView imageView = new ImageView(mContext);
                imageView.setImageDrawable(icon);
                //获取icon集合
                mListIcon.add(imageView);
                //获取包名集合
                String name = packageInfo.packageName;
                TextView tvPackage = new TextView(mContext);
                tvPackage.setText(name);
                mListPakageName.add(tvPackage);
                //获取版本号
                String versionName = packageInfo.versionName;
                TextView tvVersion = new TextView(mContext);
                tvVersion.setText(versionName);
                mListVersionName.add(tvVersion);
                //获取应用名
                String appName = (String) appInfo.loadLabel(packageManager);
                TextView tvAppName = new TextView(mContext);
                tvAppName.setText(appName);
                mListAppName.add(tvAppName);
            }
        }
    }
}
