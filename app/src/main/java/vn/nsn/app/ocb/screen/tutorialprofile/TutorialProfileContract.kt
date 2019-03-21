package vn.nsn.app.ocb.screen.tutorialprofile

import vn.nsn.app.ocb.mvp.AndroidPresenter
import vn.nsn.app.ocb.mvp.AndroidView

interface TutorialProfileViewContract : AndroidView {
    fun onUpdateSuccess()
}

interface TutorialProfilePresenterContract : AndroidPresenter<TutorialProfileViewContract> {
    fun updateProfile(gender: String, age: Int)
    fun onTutorialProfileCompleted()
}
