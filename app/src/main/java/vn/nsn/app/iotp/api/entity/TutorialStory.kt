package vn.nsn.app.iotp.api.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TutorialStory(
        val detailedChapter: DetailedChapter,
        val storyDTO: Story
) : Parcelable