package vn.nsn.app.ocb.interactor

import android.content.Context
import io.reactivex.Single
import vn.nsn.app.ocb.Constant
import vn.nsn.app.ocb.api.dto.CodeInviteDTO

class FriendInviteInteractor(context: Context) : BaseInteractor(context) {
    fun getInviteCode(): Single<CodeInviteDTO> {
        return apiEndPoint.getInviteCode(token = Constant.AUTHORIZATION_PREFIX + preferenceHelper.getAccessToken())
    }
}