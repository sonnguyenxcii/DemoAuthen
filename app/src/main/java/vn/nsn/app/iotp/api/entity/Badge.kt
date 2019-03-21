package vn.nsn.app.iotp.api.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import vn.nsn.app.iotp.Constant

@Parcelize
data class Badge(
        val update: String,
        val series: String,
        val numChapters: String,
        val wday: String
) : Parcelable {
    fun containBadge(key: String): Boolean {
        return when (key) {
            Constant.BadgeType.UPDATE -> update.isNotEmpty()
            Constant.BadgeType.SERIES -> series.isNotEmpty()
            Constant.BadgeType.NUM_CHAPTERS -> numChapters.isNotEmpty()
            Constant.BadgeType.WDAY -> wday.isNotEmpty()
            else -> false
        }
    }
}