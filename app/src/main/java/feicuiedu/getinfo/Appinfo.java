package feicuiedu.getinfo;

import android.app.ActivityManager;
import android.content.Context;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Pattern;

public class Appinfo extends AppCompatActivity {
    private TextView mTextView1,mTextView2,mTextView3,mTextView4,mTextView5,mTextView6,
            mTextView7,mTextView8,mTextView9,mTextView10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appinfo);
        //  Android获得屏幕的宽和高
               WindowManager windowManager = getWindowManager();
               Display display = windowManager.getDefaultDisplay();
               int screenWidth =  display.getWidth();
               int screenHeight = display.getHeight();
        mTextView1 = (TextView) findViewById(R.id.text1);
        mTextView2 = (TextView) findViewById(R.id.text2);
        mTextView3 = (TextView) findViewById(R.id.text3);
        mTextView4 = (TextView) findViewById(R.id.text4);
        mTextView5 = (TextView) findViewById(R.id.text5);
        mTextView6 = (TextView) findViewById(R.id.text6);
        mTextView7 = (TextView) findViewById(R.id.text7);
        mTextView8 = (TextView) findViewById(R.id.text8);
        mTextView9 = (TextView) findViewById(R.id.text9);
        mTextView10 = (TextView) findViewById(R.id.text10);


        mTextView1.setText("设备名称："+android.os.Build.MODEL);
        mTextView2.setText("系统版本："+ Build.VERSION.RELEASE);
        mTextView3.setText("全部运行内存："+this.getTotalMemory());
        mTextView4.setText("剩余运行内存："+this.getAvailMemory());
        mTextView5.setText("CPU名称："+this.getCpuName());
        mTextView6.setText("CPU数量："+this.getNumCores());
        mTextView7.setText("手机分辨率："+screenWidth+"*"+screenHeight);
        mTextView8.setText("相机分辨率："+getCameraSize());
        mTextView9.setText("基带版本："+this.getBaseband());
        if(this.getIsRooted()==true){
            mTextView10.setText("是否Root：是");
        }else{
            mTextView10.setText("是否Root：否");
        }
    }

   /* //获取内存
    private  static  long getMemory(){
        try {
            FileReader reader = new FileReader("/proc/meminfo");
            BufferedReader  bufferedReader =  new BufferedReader(reader);
            String text = bufferedReader.readLine();
            String [] split =text.split("\\s+");
           return Long.valueOf(split[1])*1024;
        } catch (IOException e) {
            e.printStackTrace();
        }
          return  0;
    }*/
    //获取系统总内存大小
    private String getTotalMemory() {
        String str1 = "/proc/meminfo";// 系统内存信息文件
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;

        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(
                    localFileReader, 8192);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小

            arrayOfString = str2.split("\\s+");
            for (String num : arrayOfString) {
                Log.i(str2, num + "\t");
            }

            initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
            localBufferedReader.close();

        } catch (IOException e) {
        }
        return Formatter.formatFileSize(getBaseContext(), initial_memory);// Byte转换为KB或者MB，内存大小规格化
    }
    // 获取android当前可用内存大小
    private String getAvailMemory() {

        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
         ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        //mi.availMem; 当前系统的可用内存

        return Formatter.formatFileSize(getBaseContext(), mi.availMem);// 将获取的内存大小规格化
    }


   /* private  static DecimalFormat sFormat = new DecimalFormat("#.00");
    //获取文件大小
    private  static  String getFileSize(long fileSize){
        StringBuilder stringBuilder = new StringBuilder();
            if(fileSize<1024){
              stringBuilder.append(fileSize);
                stringBuilder.append("Byte");
            }else if(fileSize<1048576){
                stringBuilder.append(sFormat.format((double) fileSize/1024));
                stringBuilder.append("KB");
            }else if(fileSize<1024*1024*1024){
             stringBuilder.append(sFormat.format(fileSize/(1024*1024*1024)));
             stringBuilder.append("MB");
            }else {
                stringBuilder.append(sFormat.format((double) fileSize/(1024*1024*1024*1024)));
                stringBuilder.append("GB");
            }
               return stringBuilder.toString();
    }*/
    //获得相机的最大分辨率
    private  String getCameraSize(){
        Camera camera = Camera.open();
        Camera.Parameters  parameters = camera.getParameters();
        List<Camera.Size> sizes = parameters.getSupportedPictureSizes();
        Camera.Size size = null;
        for(Camera.Size s:sizes){
               if(size == null){
                  size = s;
               }else if(size.height * size.width < s.height * s.width){
                 size = s;
               }
        }
        String maxSize = size.width+"***"+size.height;
        camera.release();
        return  maxSize;


    }

    // 获取CPU名字
    public static String getCpuName() {
        try {
            FileReader fr = new FileReader("/proc/cpuinfo");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            String[] array = text.split(":\\s+", 2);
            for (int i = 0; i < array.length; i++) {
            }
            return array[1];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //CPU个数
    private int getNumCores() {
        //Private Class to display only CPU devices in the directory listing
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                //Check if filename is "cpu", followed by a single digit number
                if(Pattern.matches("cpu[0-9]", pathname.getName())) {
                    return true;
                }
                return false;
            }
        }

        try {
            //Get directory containing CPU info
            File dir = new File("/sys/devices/system/cpu/");
            //Filter to only list the devices we care about
            File[] files = dir.listFiles(new CpuFilter());
            //Return the number of cores (virtual CPU devices)
            return files.length;
        } catch(Exception e) {
            //Print exception
            e.printStackTrace();
            //Default to return 1 core
            return 1;
        }
    }
    //获取基带版本
    private  static String getBaseband() {
        String Baseband = null;
        try {

            Class cl = Class.forName("android.os.SystemProperties");

            Object invoker = cl.newInstance();

            Method m = cl.getMethod("get", new Class[]{String.class, String.class});

            Object result = m.invoke(invoker, new Object[]{"gsm.version.baseband", "no message"});

            //  System.out.println("基带版本: " +(String)result);
            Baseband = (String) result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Baseband.toString();
    }
    //判断机器是否被root
   private  boolean getIsRooted(){
       boolean isRoot=false;
       try {
           if(!new File("system/bin/su").exists()&&(!new File("system/xbin/su").exists())){
               isRoot = false;
           }else {
               isRoot = true;
           }
       }catch(Exception e){
         e.printStackTrace();
       }
       return  isRoot;
   }
}

