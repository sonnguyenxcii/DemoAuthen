package vn.nsn.app.iotp.api.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StoryMarker(
        val chapterId: Int,
        val position: Int
) : Parcelable