package vn.nsn.app.iotp.screen.splash

import vn.nsn.app.iotp.mvp.AndroidPresenter
import vn.nsn.app.iotp.mvp.AndroidView

interface SplashViewContract : AndroidView {
    fun gotoNext()

}

interface SplashPresenterContract : AndroidPresenter<SplashViewContract> {
}
