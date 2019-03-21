package vn.nsn.app.iotp.mvp

interface AndroidPresenter<V : AndroidView> {
    fun attachView(view: V)
    fun detachView()
}