package vn.nsn.app.ocb.mvp

interface AndroidView : MvpView {
    val layoutResId: Int
    fun initLocale() {}
    fun initParams() {}
    fun initViews() {}
    fun setupViews() {}
    fun showLoading() {}
    fun hideLoading() {}
    fun showError(message: String) {}
}