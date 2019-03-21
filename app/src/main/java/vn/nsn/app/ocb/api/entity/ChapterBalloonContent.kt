package vn.nsn.app.ocb.api.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ChapterBalloonContent(
        val full_image_url: String?,
        val size: String?
) : Parcelable
