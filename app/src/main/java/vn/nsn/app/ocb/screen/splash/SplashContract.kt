package vn.nsn.app.ocb.screen.splash

import vn.nsn.app.ocb.mvp.AndroidPresenter
import vn.nsn.app.ocb.mvp.AndroidView

interface SplashViewContract : AndroidView {
    fun gotoNext()

}

interface SplashPresenterContract : AndroidPresenter<SplashViewContract> {
}
