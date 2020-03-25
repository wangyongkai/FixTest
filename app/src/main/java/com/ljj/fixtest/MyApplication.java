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

        //必须在使用Test类之前修改dex
        String dexPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "patch.dex";
        HotFix.inject(this, dexPath);


    }
}
