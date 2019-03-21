package vn.nsn.app.ocb.api.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ChapterReaction(
        val love: Int?,
        val laugh: Int?,
        val fear: Int?,
        val sad: Int?,
        val anger: Int?
) : Parcelable