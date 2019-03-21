package vn.nsn.app.iotp.api.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ChapterListItem (
        val id: Int,
        val number: Int,
        val title: String,
        val sumConsumedPeeps: Int,
        val didComplete: Boolean,
        val createdAt: String
): Parcelable
