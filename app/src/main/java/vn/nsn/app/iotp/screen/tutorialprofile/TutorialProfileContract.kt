package vn.nsn.app.iotp.screen.tutorialprofile

import vn.nsn.app.iotp.mvp.AndroidPresenter
import vn.nsn.app.iotp.mvp.AndroidView

interface TutorialProfileViewContract : AndroidView {
    fun onUpdateSuccess()
}

interface TutorialProfilePresenterContract : AndroidPresenter<TutorialProfileViewContract> {
    fun updateProfile(gender: String, age: Int)
    fun onTutorialProfileCompleted()
}
