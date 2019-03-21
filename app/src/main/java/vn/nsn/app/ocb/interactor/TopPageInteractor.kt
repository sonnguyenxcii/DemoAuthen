package vn.nsn.app.ocb.interactor

import android.content.Context
import io.reactivex.Single
import vn.nsn.app.ocb.Constant
import vn.nsn.app.ocb.api.dto.HeadlineDTO
import vn.nsn.app.ocb.api.dto.ReadHistoryDTO
import vn.nsn.app.ocb.api.dto.UserProgressDTO
import vn.nsn.app.ocb.api.entity.User
import vn.nsn.app.ocb.api.transformer.toUser

class TopPageInteractor(context: Context) : BaseInteractor(context) {
    fun getHeadline(): Single<HeadlineDTO> {
        return apiEndPoint.getHeadlines(token = Constant.AUTHORIZATION_PREFIX + preferenceHelper.getAccessToken())
    }

    fun getReadHistories(): Single<ReadHistoryDTO> {
        return apiEndPoint.getReadStories(token = Constant.AUTHORIZATION_PREFIX + preferenceHelper.getAccessToken())
    }

    fun getUserProgresses(): Single<UserProgressDTO> {
        return apiEndPoint.getUserProgresses(token = Constant.AUTHORIZATION_PREFIX + preferenceHelper.getAccessToken())
    }

    fun setUserProgress(userProgressDTO: UserProgressDTO) {
        preferenceHelper.setUserProgress(userProgressDTO)
    }

    fun login(): Single<User> {
        return apiEndPoint.login(token = Constant.AUTHORIZATION_PREFIX + preferenceHelper.getAccessToken()).map {
            it.toUser()
        }
    }
}