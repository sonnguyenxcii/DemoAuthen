package vn.nsn.app.ocb.interactor

import android.content.Context
import vn.nsn.app.ocb.PeepApp
import vn.nsn.app.ocb.helper.PreferenceHelper

open class BaseInteractor(context: Context) {

    protected val apiEndPoint by lazy {
        PeepApp.getApiEndPoint()
    }

    protected val preferenceHelper by lazy {
        PreferenceHelper(context)
    }
}