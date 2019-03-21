package vn.nsn.app.iotp.interactor

import android.content.Context
import io.reactivex.Single
import vn.nsn.app.iotp.Constant
import vn.nsn.app.iotp.api.entity.ChapterListItem
import vn.nsn.app.iotp.api.transformer.toChaptersList
class ChaptersListInteractor(context: Context) : BaseInteractor(context) {
    fun getChapters(storyID: Int): Single<List<ChapterListItem>> {
        return apiEndPoint.getChapters(token = Constant.AUTHORIZATION_PREFIX + preferenceHelper.getAccessToken(), storyID = storyID).map {
            it.toChaptersList().chapters
        }
    }
}