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

        //Tinker是全量包  能解决差分包不在同一个dex导致的CLASS_ISPREVERIFIED问题

        //---------------------------------------------------------------------------------


        //tinker更新so
        //把SO的加载流程过完之后简单总结一下,想要做SO的补丁更新有两个方式,
        // 一种是使用System.load方法接管SO加载入口,
        // 另外一种是hook 插入补丁SO到ClassLoader中SO文件Element数组的前部.


        //---------------------------------------------------------------------------------


        //tinker更新资源
        //应用启动还是启动base.apk 读取资源还是base.apk里的资源  所以要引导程序不要去读取base.apk里
        // 的资源  而是读取fix.apk里的资源


        //ActivityThread类的成员变量mActiveResources指向的是一个HashMap。
        // 这个HashMap用来维护在当前应用程序进程中加载的每一个Apk文件及其对应的Resources对象的对应关系。
        //在调用ActivityThread类的成员函数getTopLevelResources来获得一个Resources对象的时候，
        // 需要指定要获取的Resources对象所对应的Apk文件路径，这个Apk文件路径就保存在LoadedApk类的成员变量mResDir中。
        // 例如，假设我们要获取的Resources对象是用来访问系统自带的音乐播放器的资源的，那么对应的Apk文件路径就为/system/app/Music.apk。


        //由上分析可知，ContextImpl创建过程中，会调研getResources()获得Resources对象，
        // 而getResources最后调用getTopLevelResources方法。getTopLevelResources方法
        // 首先从缓存中拿Resources对象，没有拿到则先创建AssetManager对象，
        // 并通过AssetManager的addAssetPath实现系统资源文件、当前APK资源文件的加载，然后再创建Resources对象返回。


        //---------------------------------------------------------------------------------

        //必须在使用Test类之前修改dex
        String dexPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "patch.dex";
        HotFix.inject(this, dexPath);


    }
}
