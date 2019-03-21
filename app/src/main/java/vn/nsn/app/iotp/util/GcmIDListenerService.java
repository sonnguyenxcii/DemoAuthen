package vn.nsn.app.iotp.util;


import com.google.android.gms.iid.InstanceIDListenerService;

import vn.nsn.app.iotp.R;

public class GcmIDListenerService extends InstanceIDListenerService {
    @Override
    public void onTokenRefresh() {
//        InstanceID instanceID = InstanceID.getInstance(context);
//        String token = instanceID.getToken(context.getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
        //send token to app server}
    }
}