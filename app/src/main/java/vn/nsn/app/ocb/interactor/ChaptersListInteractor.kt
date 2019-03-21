package vn.nsn.app.ocb.interactor

import android.content.Context
import io.reactivex.Single
import vn.nsn.app.ocb.Constant
import vn.nsn.app.ocb.api.entity.ChapterListItem
import vn.nsn.app.ocb.api.transformer.toChaptersList
class ChaptersListInteractor(context: Context) : BaseInteractor(context) {
    fun getChapters(storyID: Int): Single<List<ChapterListItem>> {
        return apiEndPoint.getChapters(token = Constant.AUTHORIZATION_PREFIX + preferenceHelper.getAccessToken(), storyID = storyID).map {
            it.toChaptersList().chapters
        }
    }
}