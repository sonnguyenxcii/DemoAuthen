package vn.nsn.app.ocb.util;


import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.iid.InstanceIDListenerService;

import vn.nsn.app.ocb.R;

public class GcmIDListenerService extends InstanceIDListenerService {
    @Override
    public void onTokenRefresh() {
//        InstanceID instanceID = InstanceID.getInstance(context);
//        String token = instanceID.getToken(context.getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
        //send token to app server}
    }
}