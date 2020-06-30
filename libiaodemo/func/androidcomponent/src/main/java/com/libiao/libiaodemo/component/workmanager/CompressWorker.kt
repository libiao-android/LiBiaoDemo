package com.libiao.libiaodemo.component.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

class CompressWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        Log.i("libiao", "CompressWorker doWork: ${Thread.currentThread().name}")
        Log.i("libiao", "CompressWorker inputData: $inputData")
        val firstUpdate = workDataOf("progress" to 0)
        setProgressAsync(firstUpdate)
        Thread.sleep(2000)

        val mUpdate = workDataOf("progress" to 50)
        setProgressAsync(mUpdate)

        Thread.sleep(2000)

        val endUpdate = workDataOf("progress" to 100)
        setProgressAsync(endUpdate)

        return Result.success()
    }

    override fun onStopped() {
        Log.i("libiao", "CompressWorker onStopped: ${Thread.currentThread().name}")
    }

}