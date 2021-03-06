# Glide Gif加载流程

**EngineRunnable**.run

-Resource\<?> **EngineRunnable**.decode

--**EngineRunnable**.decodeFromSource

---Resource\<Z>  **DecodeJob**.decodeFromSource

----Resource\<T> **DecodeJob**.decodeSource

-----Resource\<T> **DecodeJob**.decodeFromSourceData(A data): ImageVideoWrapper data = ImageVideoModelLoader.loadData

------Resource\<GifBitmapWrapper> **GifBitmapWrapperResourceDecoder**.decode(ImageVideoWrapper source, int width, int height)

-------GifBitmapWrapper **GifBitmapWrapperResourceDecoder**.decodeStream

--------GifBitmapWrapper **GifBitmapWrapperResourceDecoder**.decodeGifWrapper(InputStream bis, int width, int height)

---------GifDrawableResource **GifResourceDecoder**.decode(InputStream source, int width, int height)

----------GifDrawableResource **GifResourceDecoder**.decode(byte[] data, int width, int height, GifHeaderParser parser, GifDecoder decoder) // **Resource\<T> == GifDrawableResource**



----Resource\<Z> **DecodeJob**.transformEncodeAndTranscode(Resource\<T> decoded)

-----Resource\<T> **DecodeJob**.transform(Resource\<T> decoded)

------Resource\<GifBitmapWrapper> **GifBitmapWrapperTransformation**.transform(Resource\<GifBitmapWrapper> resource, int outWidth, int outHeight)

-------Resource\<GifDrawable> **GifDrawableTransformation**.transform(Resource\<GifDrawable> resource, int outWidth, int outHeight)

--------Bitmap **FitCenter**.transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight)

---------Bitmap **TransformationUtils**.fitCenter(Bitmap toFit, BitmapPool pool, int width, int height) //**完成采样**

-----**DecodeJob**.writeTransformedToCache(Resource\<T> transformed) //**写Disk缓存**

-----Resource\<Z> **DecodeJob**.transcode(Resource\<T> transformed)

------Resource\<GlideDrawable> **GifBitmapWrapperDrawableTranscoder**.transcode(Resource\<GifBitmapWrapper> toTranscode)


-**EngineRunnable**.onLoadComplete(Resource resource)

--**EngineJob**.onResourceReady(final Resource\<?> resource) //**切换到主线程**

---**EngineJob**.handleResultOnMainThread()

----**Engine**.onEngineJobComplete(Key key, EngineResource<?> resource) //**写活动缓存**。 Map\<Key, WeakReference\<EngineResource\<?>>> activeResources

---**GenericRequest**.onResourceReady(Resource\<?> resource)

----**GenericRequest**.onResourceReady(Resource\<?> resource, R result)

-----**GlideDrawableImageViewTarget**.onResourceReady(GlideDrawable resource, GlideAnimation\<? super GlideDrawable> animation)

------**GifDrawable**.start() //循环播放

-------**GifFrameLoader**.start()

--------GifFrameLoader.loadNextFrame()

---------**DelayTarget**(Handler handler, int index, long targetTime)
