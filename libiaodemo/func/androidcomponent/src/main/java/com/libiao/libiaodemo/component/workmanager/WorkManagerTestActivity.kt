package com.libiao.libiaodemo.component.workmanager

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.*
import com.libiao.libiaodemo.component.R
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class WorkManagerTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.workmanager_main)

        doOtherThing()
    }

    private fun doOtherThing() {
        Log.i("libiao", "doOtherThing: ${Thread.currentThread().name}")

        val constraints = Constraints.Builder().setRequiresBatteryNotLow(true).setRequiresCharging(false).build()

        val imageData = workDataOf("key" to "value")

        val uploadWorkRequest = OneTimeWorkRequestBuilder<UploadWorker>()
                .setConstraints(constraints)
                .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, OneTimeWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
                .setInputData(imageData)
                .addTag("libiao")
                .build()

        val filter1Worker = OneTimeWorkRequestBuilder<Filter1Worker>().build()
        val filter2Worker = OneTimeWorkRequestBuilder<Filter2Worker>().build()
        val filter3Worker = OneTimeWorkRequestBuilder<Filter3Worker>().build()
        val compressWorker = OneTimeWorkRequestBuilder<CompressWorker>()
                .setInputMerger(ArrayCreatingInputMerger::class)
                .setInputData(workDataOf("done" to "compressWorker"))
                .build()

        WorkManager.getInstance(this).enqueue(uploadWorkRequest)
       // WorkManager.getInstance(this).beginWith(listOf(filter1Worker, filter2Worker, filter3Worker)).then(compressWorker).then(uploadWorkRequest).enqueue()

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(uploadWorkRequest.id).observe(this, Observer {
            Log.i("libiao", "uploadWorkRequest: ${Thread.currentThread().name}")
            Log.i("libiao", "uploadWorkRequest: $it")

        })

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(filter1Worker.id).observe(this, Observer {
            Log.i("libiao", "filter1Worker: ${Thread.currentThread().name}")
            Log.i("libiao", "filter1Worker: $it")

        })

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(filter2Worker.id).observe(this, Observer {
            Log.i("libiao", "filter2Worker: ${Thread.currentThread().name}")
            Log.i("libiao", "filter2Worker: $it")

        })

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(filter3Worker.id).observe(this, Observer {
            Log.i("libiao", "filter3Worker: ${Thread.currentThread().name}")
            Log.i("libiao", "filter3Worker: $it")

        })

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(compressWorker.id).observe(this, Observer {
            Log.i("libiao", "compressWorker: ${Thread.currentThread().name}")
            Log.i("libiao", "compressWorker: $it")

        })

//        Handler(Looper.getMainLooper()).postDelayed({
//            WorkManager.getInstance(this).cancelAllWork()
//        }, 8000)
//
       // WorkManager.getInstance(this).beginUniqueWork("yuanting", ExistingWorkPolicy.KEEP, listOf(filter1Worker, filter2Worker, filter3Worker))

    }

}