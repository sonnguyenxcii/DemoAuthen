package vn.nsn.app.iotp.mvp

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