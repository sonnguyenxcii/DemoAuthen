package vn.nsn.app.iotp.util

import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast


private fun isMarshmallow(): Boolean {
    return android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M
}

/**
 * Check permissions in runtime
 *
 * @param activity
 * @param permissions
 * @param reqCode
 * @param notification
 * @return
 */
fun isGranted(activity: AppCompatActivity, permissions: Array<String>, reqCode: Int, notification: String? = null): Boolean {
    var granted = true

    if (isMarshmallow()) {
        for (i in permissions.indices) {
            val permission = permissions[i]

            granted = ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED
            if (!granted) {
                if (notification != null && notification.isNotEmpty()) {
                    Toast.makeText(activity, notification, Toast.LENGTH_SHORT).show()
                }
                break
            }
        }

        // Ask permissions
        if (!granted) {
            ActivityCompat.requestPermissions(activity, permissions, reqCode)
        }
    }

    return granted
}

/**
 * Check permissions in runtime
 *
 * @param context
 * @param fragment
 * @param permissions
 * @param reqCode
 * @param notification
 * @return
 */
fun isGranted(context: Context, fragment: Fragment, permissions: Array<String>, reqCode: Int, notification: String? = null): Boolean {
    var granted = true

    if (isMarshmallow()) {
        for (i in permissions.indices) {
            val permission = permissions[i]

            granted = ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
            if (!granted) {
                if (notification != null && notification.isNotEmpty()) {
                    Toast.makeText(context, notification, Toast.LENGTH_SHORT).show()
                }
                break
            }
        }

        // Ask permissions
        if (!granted) {
            fragment.requestPermissions(permissions, reqCode)
        }
    }

    return granted
}