package vn.nsn.app.ocb.api.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Story(
        val id: Int,
        val title: String,
        val description: String,
        val coverUrl: String,
        val authorName: String,
        val numChapters: Int,
        val numBalloons: Int,
        val sumConsumedPeeps: Int,
        val badge: Badge,
        val isUpdatedString: String,
        val isFinishedString: String,
        val updateType: String,
        val updateWdayString: String,
        val genre: String,
        var didFavorite: Boolean,
        val marker: StoryMarker,
        val shareUrl: String,
        val videoPageUrl: String,
        var readingProgress: Int
) : Parcelable