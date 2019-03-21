package vn.nsn.app.iotp.api.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SubMenu(
        val title: String,
        val badges: List<String>,
        val key: String,
        val showRank: Boolean,
        val showProgress: Boolean
) : Parcelable