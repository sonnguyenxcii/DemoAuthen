package vn.nsn.app.iotp.util;


import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GcmListenerService;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import vn.nsn.app.iotp.R;
import vn.nsn.app.iotp.helper.PreferenceHelper;
import vn.nsn.app.iotp.screen.LoginFragment;
import vn.nsn.app.iotp.screen.main.PushEvent;

public class MyIntentService extends GcmListenerService {

    //    @Override
    public void onMessageReceived(String from, Bundle data) {

        String dataObj = data.getString("message");
        System.out.println("------------------------" + dataObj);

        if (isAppIsInBackground(getApplicationContext())) {
            setupNotification(dataObj);
        } else {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(dataObj);
                setupNotification(dataObj);

                PreferenceHelper preferenceHelper = new PreferenceHelper(getApplicationContext());
                preferenceHelper.setSession(jsonObject.getString("sessioncode"));
                preferenceHelper.setName(jsonObject.getString("message"));
                preferenceHelper.setIsNotification(true);

                EventBus.getDefault().post(new PushEvent());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        LogUtils.printLog();
    }

    private void setupNotification(String dataObj) {
        try {
            JSONObject jsonObject = new JSONObject(dataObj);
            String message = jsonObject.getString("message");
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            String channel_id = createNotificationChannel(getApplicationContext());


            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, channel_id)
                    .setSmallIcon(R.drawable.ic_ocb_logo_rounded)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(message)
                    .setAutoCancel(true);
            Intent notificationIntent = LoginFragment.Companion.newIntent(getApplicationContext(), "transaction_detail");

            PreferenceHelper preferenceHelper = new PreferenceHelper(getApplicationContext());
            preferenceHelper.setSession(jsonObject.getString("sessioncode"));

            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            PendingIntent intent = PendingIntent.getActivity(getApplicationContext(), 0,
                    notificationIntent, 0);

            mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);

            mBuilder.setContentIntent(intent);
            if (notificationManager != null) {
                notificationManager.notify(1, mBuilder.build());
            }

        } catch (Exception e) {
            e.printStackTrace();

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            String channel_id = createNotificationChannel(getApplicationContext());

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, channel_id)
                    .setSmallIcon(R.drawable.ic_ocb_logo_rounded)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(dataObj)
                    .setAutoCancel(true);
            Intent notificationIntent = LoginFragment.Companion.newIntent(getApplicationContext(), "transaction_detail");
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            PendingIntent intent = PendingIntent.getActivity(getApplicationContext(), 0,
                    notificationIntent, 0);
            mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
            mBuilder.setContentIntent(intent);
            notificationManager.notify(1, mBuilder.build());
        }
    }

    private boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                for (String activeProcess : processInfo.pkgList) {
                    if (activeProcess.equals(context.getPackageName())) {
                        isInBackground = false;
                    }
                }
            }
        }

        return isInBackground;
    }

    public static String createNotificationChannel(Context context) {

        // NotificationChannels are required for Notifications on O (API 26) and above.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // The id of the channel.
            String channelId = "Channel_id";

            // The user-visible name of the channel.
            CharSequence channelName = "OCB_iOTP";
            // The user-visible description of the channel.
            String channelDescription = "OCB iOTP Alert";
            int channelImportance = NotificationManager.IMPORTANCE_DEFAULT;

            // Initializes NotificationChannel.
            NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, channelImportance);
            notificationChannel.setDescription(channelDescription);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);

            return channelId;
        } else {
            // Returns null for pre-O (26) devices.
            return "OCB";
        }
    }
}