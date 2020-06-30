package com.libiao.libiaodemo.component.workmanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.work.ListenableWorker;

import com.google.common.util.concurrent.ListenableFuture;

public class Test {
    public void test() {
        ListenableFuture<ListenableWorker.Result> l = CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver<ListenableWorker.Result>() {
            @Nullable
            @Override
            public Object attachCompleter(@NonNull CallbackToFutureAdapter.Completer<ListenableWorker.Result> completer) throws Exception {
                return null;
            }
        });
    }
}
