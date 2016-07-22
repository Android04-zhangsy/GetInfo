package feicuiedu.getinfo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private ListView              mListView;
    private Map<Integer, Boolean> misCheckedMap;
    private CheckBox              mallCheckBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.listview);
        mallCheckBox = (CheckBox)findViewById(R.id.check_all);
        misCheckedMap = new HashMap<Integer, Boolean>();

        mListView.setAdapter(new MyAdapter(this));
        AppInfomation infomation = new AppInfomation(this);
        infomation.getAppInfo();
        final ArrayList<TextView> list = infomation.mListPakageName;
         mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 Intent intent = new Intent();
                 intent.setData(Uri.parse("package:"+list.get(position).getText()));
                 startActivity(intent);
             }
         });
        mallCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Set<Integer> set = misCheckedMap.keySet();
                      Iterator<Integer> iterator = set.iterator();
                      if(isChecked){
                              while(iterator.hasNext()){
                                      Integer keyId = iterator.next();
                                  misCheckedMap.put(keyId,true);
                                  }
                          }else{
                              while(iterator.hasNext()){
                                      Integer keyId = iterator.next();
                                  misCheckedMap.put(keyId,false);
                                  }
                          }
                     // MyAdapter.notifyDataSetChanged();
                  }


        });
    }


}
