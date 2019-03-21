package vn.nsn.app.iotp.api.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Image(
        val id: Int?,
        val url: String?
) : Parcelable