package vn.nsn.app.iotp.api.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class StoryCharacter(
        val id: Int?,
        val name: String?,
        val color: String?
) : Parcelable