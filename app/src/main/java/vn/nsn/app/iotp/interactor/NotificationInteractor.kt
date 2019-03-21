package vn.nsn.app.iotp.interactor

import android.content.Context
import io.reactivex.Single
import vn.nsn.app.iotp.Constant
import vn.nsn.app.iotp.api.dto.PushSettingDTO
import vn.nsn.app.iotp.api.dto.SettingPushDTO

class NotificationInteractor(context: Context) : BaseInteractor(context) {

    fun updatePushSetting(admin: Boolean?, peepRecovery: Boolean?, storyUpdates: Boolean?): Single<SettingPushDTO> {

        val settings = SettingPushDTO(PushSettingDTO(admin, peepRecovery, storyUpdates))
        return apiEndPoint.updatePushSetting(token = Constant.AUTHORIZATION_PREFIX + preferenceHelper.getAccessToken(),
                settings = settings).map {
            it
        }

    }

    fun getPushSetting(): Single<SettingPushDTO> {
        return apiEndPoint.getPushSetting(token = Constant.AUTHORIZATION_PREFIX + preferenceHelper.getAccessToken()).map {
            it
        }
    }

}