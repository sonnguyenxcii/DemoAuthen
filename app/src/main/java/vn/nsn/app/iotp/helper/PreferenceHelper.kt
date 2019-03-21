package vn.nsn.app.iotp.helper

import android.content.Context
import com.google.gson.GsonBuilder
import vn.nsn.app.iotp.api.dto.UserDTO
import vn.nsn.app.iotp.api.dto.UserProgressDTO
import vn.nsn.app.iotp.api.entity.User
import vn.nsn.app.iotp.api.entity.UserProgress
import vn.nsn.app.iotp.api.transformer.toUser
import vn.nsn.app.iotp.api.transformer.toUserDTO
import vn.nsn.app.iotp.api.transformer.toUserProgress
import java.util.*

class PreferenceHelper(context: Context) {

    companion object {
        const val KEY_ACCESS_TOKEN = "access_token"
        const val KEY_REFRESH_TOKEN = "refresh_token"
        const val KEY_USER = "user"
        const val KEY_DEVICE_TOKEN = "device_token"
        const val KEY_DEVICE_TOKEN_UPLOADED = "device_token_uploaded"
        const val KEY_USER_PROGRESS = "user_progress"
        const val KEY_NEED_SHOW_TUTORIAL_STORY = "need_show_tutorial_story"
        const val KEY_NEED_SHOW_ACCEPT_PUSH = "need_show_accept_push"
        const val KEY_NEED_SHOW_TUTORIAL_PROFILE = "need_show_tutorial_profile"
        const val KEY_PIN_CODE = "pincode"
        const val KEY_FIRST_TIME = "is_first"
        const val KEY_LANGUAGE = "language"
        const val KEY_ENTER_PIN_CODE_FAIL = "pincode_fail"
        const val KEY_ENTER_PIN_CODE_FAIL_LAST_TIME = "pincode_fail_last_time"
        const val KEY_BIOMETRIC = "biometric"
        const val KEY_GPS = "gps"
        const val KEY_SESSION = "session"
        const val KEY_NAME = "name"
        const val KEY_IS_NOTIFICATION = "is_notification"
        const val KEY_IS_LOCKED = "is_locked"

    }

    private val preferences by lazy {
        Preferences(context)
    }

    private val gson by lazy {
        GsonBuilder().setPrettyPrinting().create()
    }

    fun getAccessToken(): String? {
        return preferences[KEY_ACCESS_TOKEN]
    }

    fun getRefreshToken(): String? {
        return preferences[KEY_REFRESH_TOKEN]
    }

    fun setUser(user: User) {
        val userDTO = user.toUserDTO()
        user.tokens.accessToken?.let {
            if (it.isNotEmpty()) {
                preferences[KEY_ACCESS_TOKEN] = it
            }
        }
        user.tokens.refreshToken?.let {
            if (it.isNotEmpty()) {
                preferences[KEY_REFRESH_TOKEN] = it
            }
        }
        preferences[KEY_USER] = gson.toJson(userDTO)
    }

    fun getUser(): User? {
        val userString: String? = preferences[KEY_USER]
        userString?.let {
            val userDTO = gson.fromJson(it, UserDTO::class.java)
            return userDTO.toUser()
        }
        return null
    }


    fun needShowTutorialStory(): Boolean? {
        return preferences[KEY_NEED_SHOW_TUTORIAL_STORY, true]
    }

    fun setNoNeedShowTutorialStory() {
        preferences[KEY_NEED_SHOW_TUTORIAL_STORY] = false
    }

    fun setDeviceToken(token: String?) {
        preferences[KEY_DEVICE_TOKEN] = token
    }

    fun getDeviceToken(): String? {
        return preferences[KEY_DEVICE_TOKEN]
    }

    fun setBiometricLogin(token: Boolean) {
        preferences[KEY_BIOMETRIC] = token
    }

    fun getBiometricLogin(): Boolean? {
        return preferences[KEY_BIOMETRIC, false]
    }

    fun setGpsLocation(token: Boolean) {
        preferences[KEY_GPS] = token
    }

    fun getGpsLocation(): Boolean? {
        return preferences[KEY_GPS, false]
    }

    fun setDeviceTokenUploaded(uploaded: Boolean) {
        preferences[KEY_DEVICE_TOKEN_UPLOADED] = uploaded
    }

    fun isDeviceTokenUploaded(): Boolean {
        return preferences[KEY_DEVICE_TOKEN_UPLOADED, false]!!
    }

    fun setUserProgress(userProgress: UserProgressDTO) {
        preferences[KEY_USER_PROGRESS] = gson.toJson(userProgress)
    }

    fun needShowAcceptPush(): Boolean {
        return preferences[KEY_NEED_SHOW_ACCEPT_PUSH, true]!!
    }

    fun setNoNeedShowAcceptPush() {
        preferences[KEY_NEED_SHOW_ACCEPT_PUSH] = false
    }

    fun needShowTutorialProfile(): Boolean {
        return preferences[KEY_NEED_SHOW_TUTORIAL_PROFILE, true]!!
    }

    fun setNoNeedShowTutorialProfile() {
        preferences[KEY_NEED_SHOW_TUTORIAL_PROFILE] = false
    }

    fun getUserProgress(): UserProgress {
        val userProgressString: String? = preferences[KEY_USER_PROGRESS]
        userProgressString?.let {
            val userProgressDTO = gson.fromJson(it, UserProgressDTO::class.java)
            return userProgressDTO.toUserProgress()
        }
        return UserProgress(HashMap())
    }

    fun setFirstTime() {
        preferences[KEY_FIRST_TIME] = 1
    }

    fun isFirstTime(): Boolean {
        return (preferences[KEY_FIRST_TIME, 0] == 0)
    }

    fun setLang(lang: String) {
        preferences[KEY_LANGUAGE] = lang
    }

    fun getLang(): String {
        return preferences[KEY_LANGUAGE, "vi"].toString()
    }

    fun setPincode(pin: String) {
        preferences[KEY_PIN_CODE] = pin
    }

    fun setPincodeFail(time: Int) {
        preferences[KEY_ENTER_PIN_CODE_FAIL] = time
    }

    fun setPincodeFailLastTime(time: Long) {
        preferences[KEY_ENTER_PIN_CODE_FAIL_LAST_TIME] = time
    }

    fun getPincode(): String {
        return preferences[KEY_PIN_CODE, ""]!!

    }

    fun getPincodeFail(): Int {
        return preferences[KEY_ENTER_PIN_CODE_FAIL, 0]!!

    }

    fun getPincodeFailLastTime(): Long {
        return preferences[KEY_ENTER_PIN_CODE_FAIL_LAST_TIME, 0]!!

    }

    fun setSession(time: String) {
        preferences[KEY_SESSION] = time
    }

    fun getSession(): String {
        return preferences[KEY_SESSION, ""]!!

    }

    fun setName(name: String) {
        preferences[KEY_NAME] = name
    }

    fun getName(): String {
        return preferences[KEY_NAME, ""]!!

    }

    fun setIsNotification(name: Boolean) {
        preferences[KEY_IS_NOTIFICATION] = name
    }

    fun getIsNotification(): Boolean {
        return preferences[KEY_IS_NOTIFICATION, false]!!

    }

    fun setLocked(name: Boolean) {
        preferences[KEY_IS_LOCKED] = name
    }

    fun getLocked(): Boolean {
        return preferences[KEY_IS_LOCKED, false]!!

    }
}