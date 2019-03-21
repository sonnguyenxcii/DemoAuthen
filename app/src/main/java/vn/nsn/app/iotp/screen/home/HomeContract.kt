package vn.nsn.app.iotp.screen.home

import vn.nsn.app.iotp.mvp.AndroidPresenter
import vn.nsn.app.iotp.mvp.AndroidView

interface HomeViewContract : AndroidView {
    fun showAcceptPush()
    fun showTutorialProfile()
}

interface HomePresenterContract : AndroidPresenter<HomeViewContract> {
    fun setupPush()
    fun checkShowTutorialProfile()
}