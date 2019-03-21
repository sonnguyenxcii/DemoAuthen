package vn.nsn.app.ocb.screen.home

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.ViewGroup
import butterknife.BindView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.contentView
import vn.nsn.app.ocb.Constant
import vn.nsn.app.ocb.R
import vn.nsn.app.ocb.extension.disableShifting
import vn.nsn.app.ocb.mvp.MvpActivity
import vn.nsn.app.ocb.screen.tutorialprofile.TutorialProfileActivity
import vn.nsn.app.ocb.view.LockedViewPager
import java.util.concurrent.TimeUnit

class HomeActivity : MvpActivity<HomePresenterContract>(), HomeViewContract {
    override val layoutResId: Int
        get() = R.layout.activity_home

    companion object {
        const val REQUEST_CODE_ACCEPT_PUSH = 0
    }

    @BindView(R.id.vp_home)
    lateinit var vHomePager: LockedViewPager
    lateinit var adapter: HomeAdapter

    override fun initPresenter() {
        presenter = HomePresenter(this)
    }

    override fun attachViewToPresenter() {
        presenter.attachView(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        compositeDisposable.add(Observable.timer(resources.getInteger(android.R.integer.config_mediumAnimTime).toLong(), TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    presenter.setupPush()
                })
    }

    override fun setupViews() {
        val rootView = contentView
        adapter = HomeAdapter(supportFragmentManager)
        vHomePager.offscreenPageLimit = 4
        vHomePager.adapter = adapter
    }

    override fun showAcceptPush() {

    }

    override fun showTutorialProfile() {
        val intent = Intent(this, TutorialProfileActivity::class.java)
        startActivityWithPopupAnimation(intent, null)
    }

    override fun onBackPressed() {
        val fragment = adapter.getFragment(vHomePager.currentItem)
        if (!fragment.onBackPressed()) {
            super.onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ACCEPT_PUSH) {
            compositeDisposable.add(Observable.timer(resources.getInteger(android.R.integer.config_mediumAnimTime).toLong(), TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        presenter.checkShowTutorialProfile()
                    })
        }
    }
}