package vn.nsn.app.iotp.interactor

import android.content.Context
import vn.nsn.app.iotp.PeepApp
import vn.nsn.app.iotp.helper.PreferenceHelper

open class BaseInteractor(context: Context) {

    protected val apiEndPoint by lazy {
        PeepApp.getApiEndPoint()
    }

    protected val preferenceHelper by lazy {
        PreferenceHelper(context)
    }
}