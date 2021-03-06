# 金管家app内存优化方案

## 设备分级

### 设备分级
低端机关闭复杂的动画或某些功能；

### 缓存管理
统一的缓存管理机制，使用OnTrimMemory回调，根据不同的状态决定释放多少内存；

### 进程模型
一个空进程大约占用10M内存，适当的进程数，减少常驻进程；

### 安装包大小
安装包中的代码、资源、图片、so库的体积都会占用内存

## Bitmap优化
### 统一图片库
1、收拢图片的调用，方便做整体的控制策略，比如低端机使用565格式、更加严格的缩放算法。

2、收拢Bitmap.createBitmap、BitmapFactory相关的接口。

### 统一监控
1、大图片监控，比如长宽远大于view宽高甚至屏幕宽高。
2、重复图片监控，matrix面板工具可以自动将重复Bitmap的图片和引用链输出。
3、图片总内存，通过收拢图片的使用，统计应用所有图片占用的内存，在线上按不同的系统、屏幕分辨率等维度去分析图片内存的占用情况。

## 内存泄漏

### Java内存泄漏
1、自动检测Activity泄漏

### Native内存泄漏监控
1、Malloc调试和Malloc钩子

## 内存监控
### 采集方式
比如采样用户，每5分钟采集一次PSS、Java堆、图片总内存。

目前的采集策略：

"memory":{"interval":60000,"enable":true,"sampleRate":0.5}

采集字段：

private String pageName; 当前页面

private long sysRemain; 系统剩余内存

private long appRemain; app剩余堆内存

private long appUsed; app所有进程使用内存PSS

private float memRate; app内存占系统内存比

修改：

采集策略：用户采样，单个用户数据不采样

字段：

private String pageName; 当前页面

//系统总内存
//系统剩余内存
//app使用总内存pss
//app主进程使用内存pss
//最大堆内存
//app主进程使用堆内存


### 计算指标
内存异常率：反映内存占用的异常情况，比如PSS大于400M

触顶率：反映Java内存的使用情况，如果超过85%最大堆限制，GC会变得更加频繁，容易造成OOM和卡顿

平均值：反映内存的平均情况

时间维度内存变化曲线：反映内存使用情况

## GC监控

通过Debug.startAllocCounting来监控Java内存分配和GC的情况。

```
// 运行的GC次数
Debug.getRuntimeStat("art.gc.gc-count");
// GC使用的总耗时，单位是毫秒
Debug.getRuntimeStat("art.gc.gc-time");
// 阻塞式GC的次数
Debug.getRuntimeStat("art.gc.blocking-gc-count");
// 阻塞式GC的总耗时
Debug.getRuntimeStat("art.gc.blocking-gc-time");
```
需要关注阻塞式GC的次数和耗时，因为它会暂停应用线程，可能会导致应用发生卡顿。
可以更加细粒度地分应用场景统计。
