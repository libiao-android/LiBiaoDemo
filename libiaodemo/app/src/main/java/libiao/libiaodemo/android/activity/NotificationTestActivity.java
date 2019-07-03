package libiao.libiaodemo.android.activity;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.view.View;

import libiao.libiaodemo.MainActivity;
import libiao.libiaodemo.R;

/**
 * Description:
 * Data：2019/7/3-下午2:44
 * Author: libiao
 */
public class NotificationTestActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_activity_main);
    }

    public void notification1(View v) {
        //showNotification();
    }

    public void notification2(View v) {

    }

    public void showNotification(String title, String content, int notifyId, String type, String channelId) {
        Context context = this;
        Intent target = new Intent();
        target.putExtra("type", type);
        target.setClass(context, MainActivity.class);
        if (context instanceof Application) {
            target.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        PendingIntent contentIntent = PendingIntent.getActivity(context, notifyId,
                target, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = getNotificationGroup(context, nm, "555555", "Matrix")
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentIntent(contentIntent).build();

        nm.notify(notifyId, notification);
    }


    public final static String notifyGroupId  = "jinguanjia1";
    public final static String notifyGroupName  = "类别";
    public NotificationCompat.Builder getNotificationGroup(Context context, NotificationManager notificationManager, String channelId, String channelName){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && notificationManager != null){
            NotificationChannelGroup group = new NotificationChannelGroup(notifyGroupId, notifyGroupName);
            //创建group
            notificationManager.createNotificationChannelGroup(group);
            int importanceType = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(channelId, channelName, importanceType);
            //补充channel的含义（可选）
//          adChannel.setDescription("测试信息");
            //将渠道添加进组（先创建组才能添加）
            channel.setGroup(notifyGroupId);
            //静音，若有旧包，需要新channelId或者卸载原App才能更新生效
//            adChannel.setSound(null, null);
            //创建channel
            notificationManager.createNotificationChannel(channel);
            return new NotificationCompat.Builder(context, channelId);
        }else{
            return new NotificationCompat.Builder(context);
        }
    }
}
