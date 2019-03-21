package vn.nsn.app.iotp.screen.tutorialprofile

import android.os.SystemClock
import android.view.View
import android.widget.*
import butterknife.BindView
import butterknife.OnClick
import vn.nsn.app.iotp.Constant
import vn.nsn.app.iotp.Constant.Gender.FEMALE
import vn.nsn.app.iotp.Constant.Gender.MALE
import vn.nsn.app.iotp.R
import vn.nsn.app.iotp.mvp.MvpActivity
import vn.nsn.app.iotp.view.loading.LoadingView


class TutorialProfileActivity : MvpActivity<TutorialProfilePresenterContract>(), TutorialProfileViewContract {

    @BindView(R.id.img_male)
    lateinit var imgMale: ImageView

    @BindView(R.id.img_female)
    lateinit var imgFemale: ImageView

    @BindView(R.id.spn_age)
    lateinit var spnAge: Spinner

    @BindView(R.id.v_loading)
    lateinit var vLoading: LoadingView

    private var gender: String = Constant.GenderType.OTHER
    lateinit var adapter: SpinnerAdapter

    private var lastClickTime: Long = 0
    private var timeCheckClick: Long = 1000

    override val layoutResId: Int
        get() = R.layout.activity_tutorial_profile

    override fun initPresenter() {
        presenter = TutorialProfilePresenter(this)
    }

    override fun attachViewToPresenter() {
        presenter.attachView(this)
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onUpdateSuccess() {
//        showAlert(context = this, textMessage = R.string.msg_completed_thanks, textOk = R.string.close, dismissListener = {
//            finishWithPopupAnimation()
//        })
    }

    override fun showLoading() {
        vLoading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        vLoading.visibility = View.GONE
    }

    override fun setupViews() {
        val listOfItems: ArrayList<String> = ArrayList()
        for (age in 0..100) {
            if (age == 0) {
//                listOfItems.add(getString(R.string.tutorial_select_age_hint))
            } else
                listOfItems.add(age.toString())
        }
        adapter = ArrayAdapter<String>(this, R.layout.item_spinner_tutorial_profile, listOfItems)
        spnAge.adapter = adapter
    }

    fun disableButtonTwoSecs(): Boolean {
        if (SystemClock.elapsedRealtime() - lastClickTime < timeCheckClick) {
            return true
        }
        lastClickTime = SystemClock.elapsedRealtime()
        return false
    }

    @OnClick(R.id.tv_cancel)
    fun onCancelClick() {
        finishWithPopupAnimation()
    }

    @OnClick(R.id.tv_ok)
    fun onOkClick() {
        disableButtonTwoSecs()
        if (spnAge.selectedItemPosition == 0) {
            return
        }
        presenter.updateProfile(gender, spnAge.selectedItemPosition)
    }

    @OnClick(R.id.btn_male)
    fun onMaleClick() {
        changeSex(MALE)
    }

    @OnClick(R.id.btn_female)
    fun onFemaleClick() {
        changeSex(FEMALE)
    }

    fun changeSex(gender: Int) {
        this.gender = if (gender == MALE) {
            Constant.GenderType.MALE
        } else {
            Constant.GenderType.FEMALE
        }
        imgMale.setImageResource(if (gender == MALE) {
            R.drawable.btn_check_on
        } else {
            R.drawable.btn_check_off
        })

        imgFemale.setImageResource(if (gender == FEMALE) {
            R.drawable.btn_check_on
        } else {
            R.drawable.btn_check_off
        })
    }

    override fun onBackPressed() {
        finishWithPopupAnimation()
    }

    override fun finish() {
        presenter.onTutorialProfileCompleted()
        super.finish()
    }
}