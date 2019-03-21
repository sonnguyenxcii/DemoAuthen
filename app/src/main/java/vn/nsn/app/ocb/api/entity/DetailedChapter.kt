package vn.nsn.app.ocb.api.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailedChapter(
        val id: Int,
        val number: Int,
        val title: String,
        val numComments: Int,
        val backgroundImages: List<Image>,
        val reactedWith: String,
        val reactions: ChapterReaction,
        val numOpenedBalloons: Int,
        val balloons: List<ChapterBalloon>,
        val previousChapterId: Int,
        val nextChapterId: Int
) : Parcelable