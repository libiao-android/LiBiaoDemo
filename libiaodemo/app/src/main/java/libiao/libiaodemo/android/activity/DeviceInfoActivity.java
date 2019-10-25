package libiao.libiaodemo.android.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Debug;
import android.os.Process;
import android.support.annotation.Nullable;
import android.widget.TextView;

import libiao.libiaodemo.R;

public class DeviceInfoActivity extends Activity {

    private TextView mMaxheapTv;
    private TextView mUsedHeapTv;
    private TextView mPssTv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_info_activity_main);
        initView();
    }

    private void initView() {
        mMaxheapTv = findViewById(R.id.tv_device_info_max_heap);
        mMaxheapTv.setText("app堆内存最大值：" + getMaxMemory());

        mUsedHeapTv = findViewById(R.id.tv_device_info_used_heap);
        mUsedHeapTv.setText("app已使用的堆内存：" + Runtime.getRuntime().totalMemory() / (1024 * 1024) + " M");

        mPssTv = findViewById(R.id.tv_device_info_pss);
        mPssTv.setText("app已使用的物理内存：" + getProcessPSS()/1024 + " M");

    }

    /**
     * 获取当前进程的PSS,单位KB
     *
     * @param
     * @return
     */
    private long getProcessPSS() {
        try {
            ActivityManager activityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
            Debug.MemoryInfo[] memoryArray = activityManager.getProcessMemoryInfo(new int[]{Process.myPid()});
            if (memoryArray != null && memoryArray.length > 0) {
                return memoryArray[0].getTotalPss();
            }
        } catch (Exception e) {

        }
        return 0;
    }


    /**
     * 获取默认情况下APP可以分配的最大堆内存，单位KB
     *
     *
     * adb shell getprop | grep dalvik.vm.heapsize = 512
     *
     *
     * ActivityManager.getMemoryClass()获得内用正常情况下内存的大小 = 384
     * ActivityManager.getLargeMemoryClass()可以获得开启largeHeap最大的内存大小 = 512
     *
     *
     * @param
     * @return
     */
    private long getMemoryClass() {
        try {
            ActivityManager activityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
            return activityManager.getMemoryClass();
        } catch (Exception e) {

        }
        return 0;
    }

    /**
     *
     * java.lang.Runtime类中的freeMemory(),totalMemory(),maxMemory ()这几个方法
     *
     * maxMemory()这个方法返回的是java虚拟机（这个进程）能构从操作系统那里挖到的最大的内存，以字节为单位，如果在运行java程序的时 候，没有添加-Xmx参数，那么就是64兆，也就是说maxMemory()返回的大约是64*1024*1024字节，这是java虚拟机默认情况下能 从操作系统那里挖到的最大的内存。如果添加了-Xmx参数，将以这个参数后面的值为准，例如java -cp ClassPath -Xmx512m ClassName，那么最大内存就是512*1024*0124字节。
     *
     * totalMemory()这个方法返回的是java虚拟机现在已经从操作系统那里挖过来的内存大小，也就是java虚拟机这个进程当时所占用的所有 内存。如果在运行java的时候没有添加-Xms参数，那么，在java程序运行的过程的，内存总是慢慢的从操作系统那里挖的，基本上是用多少挖多少，直 挖到maxMemory()为止，所以totalMemory()是慢慢增大的。如果用了-Xms参数，程序在启动的时候就会无条件的从操作系统中挖- Xms后面定义的内存数，然后在这些内存用的差不多的时候，再去挖。
     *
     * freeMemory()是什么呢，刚才讲到如果在运行java的时候没有添加-Xms参数，那么，在java程序运行的过程的，内存总是慢慢的从操 作系统那里挖的，基本上是用多少挖多少，但是java虚拟机100％的情况下是会稍微多挖一点的，这些挖过来而又没有用上的内存，实际上就是 freeMemory()，所以freeMemory()的值一般情况下都是很小的，但是如果你在运行java程序的时候使用了-Xms，这个时候因为程 序在启动的时候就会无条件的从操作系统中挖-Xms后面定义的内存数，这个时候，挖过来的内存可能大部分没用上，所以这个时候freeMemory()可 能会有些大。
     *
     * @return
     */
    private String getMaxMemory() { // 正常返回384，开启largeHeap返回512
        return Runtime.getRuntime().maxMemory() / (1024 * 1024) + " M";
    }

    /**
     *
     * 厂商在定制android系统时，通过dalvik.vm.heapsize 参数限制了每一个dalvik 进程的最大堆内存。程序申请的java heap对象超过了dalvik vm heapsize 时，就会触发OOM。
     *
     * java程序发生OMM并不是表示RAM不足。而是堆内存超出了dalvik.vm.heapsize的限制。如果RAM真的不足，会发生什么呢？这时Android的memory killer会起作用，当RAM所剩不多时，memory killer会杀死一些优先级比较低的进程来释放物理内存，让高优先级程序得到更多的内存。
     *
     * dalvik.vm.heapsize 的显示，仅是对dalvik进程中java对的限制。对native 堆 并没有限制。所以在android程序中natvie 堆的内存可以很大。
     *
     *
     * 应用程序如何突破dalvik.vm.heapsize 的限制 ？
     *
     * 1、创建子进程。创建一个新的进程，那么我们就可以把一些对象分配到新进程的heap上了，从而达到一个应用程序使用更多的内存的目的。
     * 2、使用jni在native heap上申请空间（推荐使用）。nativeheap的增长并不受dalvik vm heapsize的限制。只要RAM有剩余空间，程序员可以一直在native heap上申请空间，当然如果 RAM快耗尽，memory killer会杀进程释放RAM。
     * 3、使用显存。使用 OpenGL textures 等 API ， texture memory 不受 dalvik vm heapsize 限制。
     *
     *
     *
     *
     */
}
