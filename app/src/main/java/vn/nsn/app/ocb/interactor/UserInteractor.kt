package vn.nsn.app.ocb.interactor

import android.content.Context
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import vn.nsn.app.ocb.Constant
import vn.nsn.app.ocb.api.dto.*
import vn.nsn.app.ocb.api.entity.LoginResponse
import vn.nsn.app.ocb.api.entity.User
import vn.nsn.app.ocb.api.entity.UserCode
import vn.nsn.app.ocb.api.transformer.toLoginResponse
import vn.nsn.app.ocb.api.transformer.toUser
import vn.nsn.app.ocb.api.transformer.toUserCode
import java.io.File

class UserInteractor(context: Context) : BaseInteractor(context) {

    fun needShowTutorialStory(): Boolean {
        return preferenceHelper.needShowTutorialStory()!!
    }

    fun setNoNeedShowTutorialStory() {
        preferenceHelper.setNoNeedShowTutorialStory()
    }


    fun isLoggedIn(): Boolean {
        return preferenceHelper.getRefreshToken() != null
    }

    fun createUser(): Single<User> {
        return apiEndPoint.createUser().map {
            it.userDTO!!.toUser()
        }
    }

    fun getCurrentUser(): Single<User> {
        return apiEndPoint.getCurrentUser(token = Constant.AUTHORIZATION_PREFIX + preferenceHelper.getAccessToken()).map {
            it.userDTO!!.toUser()
        }
    }

    fun updateUser(email: String?, password: String?): Single<LoginResponse> {
        val login = LoginDTO(EmailAndPassDTO(email, password))
        return apiEndPoint.updateUser(token = Constant.AUTHORIZATION_PREFIX + preferenceHelper.getAccessToken(), login = login).map {
            it.toLoginResponse()
        }
    }

    fun setUser(user: User) {
        preferenceHelper.setUser(user)
    }

    fun getLocalUser(): User? {
        return preferenceHelper.getUser()
    }

    fun setDeviceToken(token: String?) {
        preferenceHelper.setDeviceToken(token)
    }

    fun getDeviceToken(): String? {
        return preferenceHelper.getDeviceToken()
    }

    fun uploadDeviceToken(token: String?): Single<Any> {
        return apiEndPoint.updateDeviceToken(token = Constant.AUTHORIZATION_PREFIX + preferenceHelper.getAccessToken(), deviceToken = UpdateDeviceTokenDTO(DeviceTokenDTO(token)))
    }

    fun isDeviceTokenUploaded(): Boolean {
        return preferenceHelper.isDeviceTokenUploaded()
    }

    fun setDeviceTokenUploaded(uploaded: Boolean) {
        preferenceHelper.setDeviceTokenUploaded(uploaded)
    }

    fun needShowAcceptPush(): Boolean {
        return preferenceHelper.needShowAcceptPush()
    }

    fun setNoNeedShowAcceptPush() {
        preferenceHelper.setNoNeedShowAcceptPush()
    }

    fun needShowTutorialProfile(): Boolean {
        return preferenceHelper.needShowTutorialProfile()
    }

    fun setNoNeedShowTutorialProfile() {
        preferenceHelper.setNoNeedShowTutorialProfile()
    }

    fun updateProfile(name: String? = null, gender: String? = null, age: Int? = null, icon: File? = null): Single<UserDTO> {
        var nameRequestBody: RequestBody? = null
        name?.let {
            nameRequestBody = RequestBody.create(MediaType.parse("text/plain"), it)
        }
        var genderRequestBody: RequestBody? = null
        gender?.let {
            genderRequestBody = RequestBody.create(MediaType.parse("text/plain"), it)
        }
        var ageRequestBody: RequestBody? = null
        age?.let {
            ageRequestBody = RequestBody.create(MediaType.parse("text/plain"), it.toString())
        }
        var iconRequestBody: MultipartBody.Part? = null
        icon?.let {
            iconRequestBody = MultipartBody.Part.createFormData("profile[icon]", it.name, RequestBody.create(MediaType.parse("icon/jpg"), it))
        }
        return apiEndPoint.updateProfile(
                token = Constant.AUTHORIZATION_PREFIX + preferenceHelper.getAccessToken(),
                name = nameRequestBody,
                gender = genderRequestBody,
                age = ageRequestBody,
                icon = iconRequestBody)
    }

    fun getUserCode(): Single<UserCode> {
        return apiEndPoint.getUserCode(token = Constant.AUTHORIZATION_PREFIX + preferenceHelper.getAccessToken()).map {
            it.toUserCode()
        }
    }

    fun pushInvitationCode(inviteCode: String): Single<User> {
        val inviteCodeDTO = InputInvitationCodeDTO(BodyDTO(inviteCode))
        return apiEndPoint.inputInvitationCode(
                token = Constant.AUTHORIZATION_PREFIX + preferenceHelper.getAccessToken(),
                inviteCode = inviteCodeDTO
        ).map {
            it.toUser()
        }
    }

    fun putStoryMarker(storyId: Int?, chapterId: Int?, position: Int?): Single<User> {

        val storyMarker = StoryMarkerDTO(chapterId, position)
        return apiEndPoint.putStoryMarker(token = Constant.AUTHORIZATION_PREFIX + preferenceHelper.getAccessToken(), storyId = storyId!!,
                marker = storyMarker).map {
            it.userDTO?.toUser()
        }

    }
}