package com.libiao.libiaodemo.component

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.libiao.libiaodemo.component.plugin.ProxyPluginActivity
import com.libiao.libiaodemo.component.workmanager.WorkManagerTestActivity
import com.permissionx.guolindev.PermissionX


class ComponentUI : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.component_main)
    }

    fun workManager(v: View) {
        val intent = Intent(this, WorkManagerTestActivity::class.java)
        startActivity(intent)
    }

    fun plugin(v: View) {

        PermissionX.init(this).permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE).request { allGranted, grantedList, deniedList ->
            if(allGranted) {
                val intent = Intent()
                Environment.getExternalStorageDirectory().path
                intent.putExtra(ProxyPluginActivity.PLUGIN_DEX_PATH, Environment.getExternalStorageDirectory().path+"/1/app-debug.apk")
                intent.putExtra(ProxyPluginActivity.PLUGIN_ACTIIVTY_CLASS_NAME, "com.example.plugindemo.MainActivity")
                intent.setClass(this, ProxyPluginActivity::class.java) // 其实启动的还是ProxyActivity
                startActivity(intent)
            } else {

            }
        }
    }
}