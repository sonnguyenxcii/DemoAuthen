package vn.nsn.app.iotp.interactor

import android.content.Context
import io.reactivex.Single
import vn.nsn.app.iotp.Constant
import vn.nsn.app.iotp.api.dto.FavoriteStoriesNotificationDTO
import vn.nsn.app.iotp.api.entity.*
import vn.nsn.app.iotp.api.transformer.*

class StoryInteractor(context: Context) : BaseInteractor(context) {

    fun getTutorialStory(): Single<TutorialStory> {
        return apiEndPoint.getTutorialStory().map {
            it.toTutorialStory()
        }
    }

    fun getSubMenus(): Single<List<SubMenu>> {
        return apiEndPoint.getSubMenus(token = Constant.AUTHORIZATION_PREFIX + preferenceHelper.getAccessToken()).map {
            it.components?.map {
                it.toSubMenu()
            } ?: listOf()
        }
    }

    fun getStoriesByGenres(path: String?): Single<List<StoryCollection>> {
        return apiEndPoint.getStoriesByGenre(token = Constant.AUTHORIZATION_PREFIX + preferenceHelper.getAccessToken(), path = path).map {
            it.collections.map {
                it.toStoryCollection()
            }
        }
    }

    fun getLogStories(type: Int, page: Int): Single<List<Story>> {
        return when (type) {
            Constant.LogType.TYPE_HISTORY -> apiEndPoint.getHistoryStories(token = Constant.AUTHORIZATION_PREFIX + preferenceHelper.getAccessToken(), page = page.toString()).map {
                it.stories?.map {
                    it.toStory()
                }
            }
            Constant.LogType.TYPE_FAVOURITE -> apiEndPoint.getFavouriteStories(token = Constant.AUTHORIZATION_PREFIX + preferenceHelper.getAccessToken(), page = page.toString()).map {
                it.stories?.map {
                    it.toStory()
                }
            }
            else -> throw IllegalArgumentException()
        }
    }

    fun addToFavorite(id: Int, notification: Boolean): Single<Story> {
        val favorite = FavoriteStoriesNotificationDTO(notification)
        return apiEndPoint.addFavoriteStories(token = Constant.AUTHORIZATION_PREFIX + preferenceHelper.getAccessToken(), storyId = id,
                option = favorite
        ).map {
            it.toStory()
        }
    }

    fun removeFavorite(id: Int): Single<Any> {

        return apiEndPoint.removeFavoriteStories(token = Constant.AUTHORIZATION_PREFIX + preferenceHelper.getAccessToken(), storyId = id
        )
    }

    fun getChapterDetailReading(chapterId: Int): Single<DetailedChapter> {
        return apiEndPoint.getChapterReading(token = Constant.AUTHORIZATION_PREFIX + preferenceHelper.getAccessToken(), chapterId = chapterId).map {
            it.chapter.toDetailChapter()
        }
    }

    fun getStoryInfo(storyId: Int): Single<Story> {
        return apiEndPoint.getStoryInfo(token = Constant.AUTHORIZATION_PREFIX + preferenceHelper.getAccessToken(), storyId = storyId).map {
            it.storyDTO?.toStory()
        }
    }
}