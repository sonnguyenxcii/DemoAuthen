package vn.nsn.app.iotp.api.dto

import com.google.gson.annotations.SerializedName

data class SimpleChapterDTO(
        @SerializedName("id")
        val id: Int?,
        @SerializedName("number")
        val number: Int?,
        @SerializedName("sum_consumed_peeps")
        val sumConsumedPeeps: Int?,
        @SerializedName("did_complete")
        val didComplete: Boolean?,
        @SerializedName("creatd_at")
        val createdAt: String?
)