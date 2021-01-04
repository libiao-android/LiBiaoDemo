package com.libiao.libiaodemo.component.plugin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import dalvik.system.DexClassLoader
import java.lang.reflect.AccessibleObject.setAccessible
import java.lang.reflect.InvocationTargetException
import android.content.res.AssetManager
import android.content.res.Resources
import java.lang.reflect.AccessibleObject.setAccessible


class ProxyPluginActivity : Activity() {

    companion object {
        const val PLUGIN_DEX_PATH = "plugin.dex.path"
        const val PLUGIN_ACTIIVTY_CLASS_NAME = "plugin.activity.class.name"
    }
    var mPluginActivity: Activity? = null
    var mPluginResourcs: Resources? = null
    var mPluginDexPath: String? = null
    var mPluginActivityClassName: String? = null
    private var mTheme: Resources.Theme? = null
    private var assetManager: AssetManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent

        mPluginDexPath = intent.getStringExtra(PLUGIN_DEX_PATH)
        mPluginActivityClassName = intent.getStringExtra(PLUGIN_ACTIIVTY_CLASS_NAME)

        Log.i("libiao", "pluginDexPath = $mPluginDexPath")
        Log.i("libiao", "pluginActivityClassName = $mPluginActivityClassName")

        if (TextUtils.isEmpty(mPluginDexPath) || TextUtils.isEmpty(mPluginActivityClassName)) {
            return
        }

        loadApkResources()

        // 根据apk路径加载apk代码到DexClassLoader中
        val dexOutputDir = this.getDir("dex", 0)

        Log.i("libiao", "dexOutputDir = $dexOutputDir")

        val dexClassLoader = DexClassLoader(mPluginDexPath,
                dexOutputDir.absolutePath, null, ClassLoader.getSystemClassLoader())

        var pluginActivityClass: Class<*>? = null
        try {
            pluginActivityClass = dexClassLoader.loadClass(mPluginActivityClassName)
            pluginActivityClass?.also {
                mPluginActivity = it.newInstance() as Activity
                Log.i("libiao", "ProxyPluginActivity mPluginActivity: $mPluginActivity")
            }
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        }


        // 调用功能Activity的setProxyActivity方法，给其设置代理Activity
        try {
            val setProxyActivityMethod = pluginActivityClass?.getDeclaredMethod("setProxyActivity", Activity::class.java)
            setProxyActivityMethod?.invoke(mPluginActivity, this)
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }


        // 调用功能Activity实例的onCreate方法
        try {
            val onCreateMethod = Activity::class.java.getDeclaredMethod("onCreate", Bundle::class.java)
            onCreateMethod.isAccessible = true
            onCreateMethod.invoke(mPluginActivity, savedInstanceState)
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }

    }

    // 加载插件Apk的资源
    private fun loadApkResources() {
        try {
            assetManager = AssetManager::class.java.newInstance() // 通过反射创建一个AssetManager对象
            val addAssetPathMethod = AssetManager::class.java.getDeclaredMethod("addAssetPath", String::class.java) // 获得AssetManager对象的addAssetPath方法
            addAssetPathMethod.isAccessible = true
            addAssetPathMethod.invoke(assetManager, mPluginDexPath) // 调用AssetManager的addAssetPath方法，将apk的资源添加到AssetManager中管理


            //初始化AssetManager内部参数
//            val ensureStringBlocks = AssetManager::class.java.getDeclaredMethod("ensureStringBlocks")
//            ensureStringBlocks.isAccessible = true
//            ensureStringBlocks.invoke(assetManager)




            mPluginResourcs = Resources(assetManager, super.getResources().displayMetrics, super.getResources().configuration) // 根据AssetMananger创建一个Resources对象

           /* mTheme = mPluginResourcs?.newTheme()
            mTheme?.setTo(super.getTheme())*/
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }

    }

    // 重写ProxyActivity的getResources方法，让其返回插件Apk的资源对象
    override fun getResources(): Resources? {
        return if (mPluginResourcs != null) {
            mPluginResourcs
        } else super.getResources()
    }


    override fun getAssets(): AssetManager? {
        return if (assetManager == null) super.getAssets() else assetManager
    }

    override fun getTheme(): Resources.Theme? {
        return if (mTheme == null) super.getTheme() else mTheme
    }

    override fun setContentView(layoutResID: Int) {
        Log.i("libiao", "setContentView = $layoutResID")
        super.setContentView(layoutResID)
    }

}