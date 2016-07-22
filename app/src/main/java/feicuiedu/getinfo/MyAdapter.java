package feicuiedu.getinfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/7/19.
 */
public class MyAdapter extends BaseAdapter {
    private  AppInfomation mappInfomation ;
    private Context        mContext;
    public  MyAdapter(Context context){
        mContext = context;
       mappInfomation = new AppInfomation(mContext);
        mappInfomation.getAppInfo();
    }
    @Override
    public int getCount() {
        if (0 != mappInfomation.mListIcon.size()) {
            return mappInfomation.mListIcon.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item, null);
            holder.mImageView = (ImageView) convertView.findViewById(R.id.image);
            holder.mTvPackage = (TextView) convertView.findViewById(R.id.title);
            holder.mTvApp = (TextView) convertView.findViewById(R.id.info);
            holder.mTvVersion = (TextView) convertView.findViewById(R.id.ver);
            //给convertView一个标记
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mImageView.setImageDrawable(mappInfomation.mListIcon.get(position).getDrawable());
        holder.mTvPackage.setText("包名:  "+mappInfomation.mListPakageName.get(position).getText());
        holder.mTvApp.setText("应用名:  "+mappInfomation.mListAppName.get(position).getText());
        holder.mTvVersion.setText("版本号:  "+mappInfomation.mListVersionName.get(position).getText());
        return convertView;
    }

    private static class ViewHolder{
        ImageView mImageView;
        TextView mTvPackage;
        TextView mTvApp;
        TextView mTvVersion;
    }
}
