/*
 * Tencent is pleased to support the open source community by making wechat-matrix available.
 * Copyright (C) 2018 THL A29 Limited, a Tencent company. All rights reserved.
 * Licensed under the BSD 3-Clause License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://opensource.org/licenses/BSD-3-Clause
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tencent.matrix.iocanary.detect;


import android.os.Build;
import android.util.Log;

import com.tencent.matrix.report.IssuePublisher;
import com.tencent.matrix.util.MatrixLog;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * This hooker is special and it acts as a detector too. So it's also a IssuePublisher
 * When it get the hook, it get a issue too.
 * Issue is that a closeable leak which we must avoid in our project.
 *
 * @author liyongjie
 *         Created by liyongjie on 2017/6/9.
 */

public final class CloseGuardHooker {
    private static final String TAG = "Matrix.CloseGuardHooker";

    private volatile boolean mIsTryHook;

    private volatile static Object sOriginalReporter;

    private final IssuePublisher.OnIssueDetectListener issueListener;

    private String closeGuardField_report;
    private String closeGuardField_enable;

    public CloseGuardHooker(IssuePublisher.OnIssueDetectListener issueListener) {
        this.issueListener = issueListener;
        if(Build.VERSION.SDK_INT >= 28) {
            closeAndroidPDialog();
            closeGuardField_report = "reporter";
            closeGuardField_enable = "stackAndTrackingEnabled";
        } else {
            closeGuardField_report = "REPORTER";
            closeGuardField_enable = "ENABLED";
        }
    }

    private void closeAndroidPDialog(){
        try { Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try { Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * set to true when a certain thread try hook once; even failed.
     */
    public void hook() {
        MatrixLog.i(TAG, "hook sIsTryHook=%b", mIsTryHook);
        if (!mIsTryHook) {
            boolean hookRet = tryHook();
            MatrixLog.i(TAG, "hook hookRet=%b", hookRet);
            mIsTryHook = true;
        }
    }

    public void unHook() {
        boolean unHookRet = tryUnHook();
        MatrixLog.i(TAG, "unHook unHookRet=%b", unHookRet);
        mIsTryHook = false;
    }

    /**
     * TODO comment
     * Use a way of dynamic proxy to hook
     * <p>
     * warn of sth: detectLeakedClosableObjects may be disabled again after this tryHook once called
     *
     * @return
     */
    private boolean tryHook() {
        try {
            /*Class<?> vmPolicyCls = Class.forName("android.os.StrictMode$VmPolicy");
            Field fieldMask = vmPolicyCls.getDeclaredField("mask");
            fieldMask.setAccessible(true);
            StrictMode.VmPolicy policy = StrictMode.getVmPolicy();
            int mask = fieldMask.getInt(policy);
            Log.i(TAG, "tryHook detectLeakedClosableObjects mask:%d enable: %b", mask, (mask&DETECT_VM_CLOSABLE_LEAKS)>0);
            fieldMask.setInt(policy, mask|1040);

            StrictMode.setVmPolicy(policy);*/

            /* Enable the strict mode to detect the closeable leak */
//            sOriginalPolicy = StrictMode.getVmPolicy();
//            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder(sOriginalPolicy);
//            StrictMode.setVmPolicy(builder.detectLeakedClosableObjects().build());

            Class<?> closeGuardCls = Class.forName("dalvik.system.CloseGuard");
            Class<?> closeGuardReporterCls = Class.forName("dalvik.system.CloseGuard$Reporter");
            Field fieldREPORTER = closeGuardCls.getDeclaredField(closeGuardField_report);
            Field fieldENABLED = closeGuardCls.getDeclaredField(closeGuardField_enable);

            fieldREPORTER.setAccessible(true);
            fieldENABLED.setAccessible(true);

            sOriginalReporter = fieldREPORTER.get(null);
            fieldENABLED.set(null, true);
            // open matrix close guard also
            MatrixCloseGuard.setEnabled(true);

            ClassLoader classLoader = closeGuardReporterCls.getClassLoader();
            if (classLoader == null) {
                return false;
            }

            fieldREPORTER.set(null, Proxy.newProxyInstance(classLoader,
                new Class<?>[]{closeGuardReporterCls},
                new IOCloseLeakDetector(issueListener, sOriginalReporter)));

            fieldREPORTER.setAccessible(false);
            return true;
        } catch (Throwable e) {
            MatrixLog.e(TAG, "tryHook exp=%s", e);
        }

        return false;
    }

    private boolean tryUnHook() {
        try {
            Class<?> closeGuardCls = Class.forName("dalvik.system.CloseGuard");
            Field fieldREPORTER = closeGuardCls.getDeclaredField(closeGuardField_report);
            fieldREPORTER.setAccessible(true);
            fieldREPORTER.set(null, sOriginalReporter);

            Field fieldENABLED = closeGuardCls.getDeclaredField(closeGuardField_enable);
            fieldENABLED.setAccessible(true);
            fieldENABLED.set(null, false);

            fieldREPORTER.setAccessible(false);

            // close matrix close guard also
            MatrixCloseGuard.setEnabled(false);

            return true;
        } catch (Throwable e) {
            MatrixLog.e(TAG, "tryHook exp=%s", e);
        }

        return false;
    }
}
