package vn.nsn.app.ocb.util;


import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GcmListenerService;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import vn.nsn.app.ocb.R;
import vn.nsn.app.ocb.helper.PreferenceHelper;
import vn.nsn.app.ocb.screen.LoginFragment;
import vn.nsn.app.ocb.screen.main.PushEvent;

public class MyIntentService extends GcmListenerService {

    //    @Override
    public void onMessageReceived(String from, Bundle data) {

        String dataObj = data.getString("message");
        System.out.println("------------------------" + dataObj);

        if (isAppIsInBackground(getApplicationContext())) {
            try {
                JSONObject jsonObject = new JSONObject(dataObj);
                String message = jsonObject.getString("message");
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "OCB_CN_ID")
                        .setSmallIcon(R.drawable.ic_ocb_logo_rounded)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(message)
//                    .setContentText(getString(R.string.notif_content))
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
                notificationManager.notify(1, mBuilder.build());
            } catch (Exception e) {

                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "OCB_CN_ID")
                        .setSmallIcon(R.drawable.ic_ocb_logo_rounded)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(dataObj)
//                    .setContentText(getString(R.string.notif_content))
                        .setAutoCancel(true);
                Intent notificationIntent = LoginFragment.Companion.newIntent(getApplicationContext(), "transaction_detail");
//                notificationIntent.putExtra("data", "transaction_detail");
//            PreferenceHelper preferenceHelper = new PreferenceHelper(getApplicationContext());
//            preferenceHelper.setSession(jsonObject.getString("sessioncode"));
                notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                PendingIntent intent = PendingIntent.getActivity(getApplicationContext(), 0,
                        notificationIntent, 0);
                mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
                mBuilder.setContentIntent(intent);
                notificationManager.notify(1, mBuilder.build());
            }
        } else {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(dataObj);
                PreferenceHelper preferenceHelper = new PreferenceHelper(getApplicationContext());
                preferenceHelper.setSession(jsonObject.getString("sessioncode"));
                preferenceHelper.setName(jsonObject.getString("username"));

                EventBus.getDefault().post(new PushEvent());
            } catch (JSONException e) {
//                e.printStackTrace();
            }

        }


//        System.out.println("------------------------" + message);
////        System.out.println("------------------------" + data.toString());
////        System.out.println("------------------------" + from);
//
//        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.activity_custom_notification);
//
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "OCB_CN_ID")
//                .setSmallIcon(R.drawable.ic_ocb_logo_rounded)
//                .setContentTitle(getString(R.string.app_name))
//                .setContentText(getString(R.string.notif_content))
//                .setAutoCancel(true);
//
//// Creates an explicit intent for an Activity in your app
////        Intent resultIntent = new Intent(getApplicationContext(), TransactionDetailActivity.class);
////
////        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
////// Adds the back stack for the Intent (but not the Intent itself)
//////        stackBuilder.addParentStack(TransactionDetailActivity.class);
////// Adds the Intent that starts the Activity to the top of the stack
////        stackBuilder.addNextIntent(resultIntent);
////
////
////        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
////                0,
////                PendingIntent.FLAG_UPDATE_CURRENT
////        );
//
//        Intent notificationIntent = new Intent(getApplicationContext(), TransactionDetailActivity.class);
//
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
//                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//
//        PendingIntent intent = PendingIntent.getActivity(getApplicationContext(), 0,
//                notificationIntent, 0);
//
//        mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
//        mBuilder.setContentIntent(intent);
//        mBuilder.setCustomBigContentView(remoteViews);
//        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
////        as NotificationManager;
//// mId allows you to update the notification later on.
//        mNotificationManager.notify(1000, mBuilder.build());

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
}