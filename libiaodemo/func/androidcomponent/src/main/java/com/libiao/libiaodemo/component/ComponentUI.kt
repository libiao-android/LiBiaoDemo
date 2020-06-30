package com.libiao.libiaodemo.component

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.libiao.libiaodemo.component.workmanager.WorkManagerTestActivity

class ComponentUI : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.component_main)
    }

    fun workManager(v: View) {
        val intent = Intent(this, WorkManagerTestActivity::class.java)
        startActivity(intent)
    }
}