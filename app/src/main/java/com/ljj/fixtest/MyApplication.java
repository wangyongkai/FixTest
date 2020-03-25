package com.ljj.fixtest;

import java.io.File;

import android.app.Application;
import android.os.Environment;

import com.ljj.patch.util.HotFix;

/**
 * Created by ljj on 2017/11/21.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //HotFix.init(this);


        //---------------------------------------------------------------------------------

        //热修复原理：
        //在使用某个类之前，DexClassLoader这个类（此时不是加载 这是loaddex），然后反射拿到dexElements
        // 与PathClassLoader的反射拿到的dexElements进行合并  合并要放在PathClassLoader的反射拿到的dexElements的前面
        //这样之后，使用这个类时，PathClassLoader会先从前面开始加载。加载后，双亲委派，后的DexClassLoader就永远不会加载。

        //---------------------------------------------------------------------------------

        //Tinker原理：
        //下载patch包 在app内将patch包合并为全量包  然后将这个全量包插入到dexElements数组的最前面
        //因为app安装目录是不能修改的  也就是应用启动后 还是会从安装目录读取之前的dex  所以只能插入dexElements前面
        //每次启动都是这样 不是这次启动合并了  下次就会正常启动 还是会插入

        //---------------------------------------------------------------------------------

        //必须在使用Test类之前修改dex
        String dexPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "patch.dex";
        HotFix.inject(this, dexPath);


    }
}
