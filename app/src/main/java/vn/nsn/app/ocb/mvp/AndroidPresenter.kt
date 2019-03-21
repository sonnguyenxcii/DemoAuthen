package vn.nsn.app.ocb.mvp

interface AndroidPresenter<V : AndroidView> {
    fun attachView(view: V)
    fun detachView()
}