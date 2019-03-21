package vn.nsn.app.iotp.api.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ChapterBalloon(
        val character: StoryCharacter?,
        val body: String?,
        val backgroundImageId: Int?,
        val position: String?,
        val content_type: String?,
        var animation_type: String?,
        var content: ChapterBalloonContent?
) : Parcelable