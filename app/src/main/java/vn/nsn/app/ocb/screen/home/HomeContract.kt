package vn.nsn.app.ocb.screen.home

import vn.nsn.app.ocb.mvp.AndroidPresenter
import vn.nsn.app.ocb.mvp.AndroidView

interface HomeViewContract : AndroidView {
    fun showAcceptPush()
    fun showTutorialProfile()
}

interface HomePresenterContract : AndroidPresenter<HomeViewContract> {
    fun setupPush()
    fun checkShowTutorialProfile()
}