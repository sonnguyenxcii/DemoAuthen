//package vn.nsn.app.iotp.push
//
//import android.util.Log
//import com.google.firebase.messaging.FirebaseMessagingService
//import com.google.firebase.messaging.RemoteMessage
//import vn.nsn.app.iotp.extension.result
//import vn.nsn.app.iotp.interactor.UserInteractor
//
//class PeepFirebaseMessagingService : FirebaseMessagingService() {
//
//    private var userInteractor: UserInteractor? = null
//
//    override fun onNewToken(token: String?) {
//        if (userInteractor == null) {
//            userInteractor = UserInteractor(baseContext)
//        }
//        userInteractor?.setDeviceToken(token)
//        userInteractor?.setDeviceTokenUploaded(false)
//        uploadDeviceToken(token)
//        Log.d("Peep", token)
//    }
//
//    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
//        Log.d("Peep", remoteMessage.toString())
//    }
//
//    private fun uploadDeviceToken(token: String?) {
//        userInteractor?.uploadDeviceToken(token)?.result {
//            doOnSuccess {
//                userInteractor?.setDeviceTokenUploaded(true)
//            }
//        }
//    }
//
//}