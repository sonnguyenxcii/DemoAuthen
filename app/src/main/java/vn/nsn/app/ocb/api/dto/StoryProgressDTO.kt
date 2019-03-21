package vn.nsn.app.ocb.api.dto

import com.google.gson.annotations.SerializedName

class StoryProgressDTO(
        @SerializedName("num_opened_balloons")
        val numberOpenedBalloons: HashMap<String, Int>?,
        @SerializedName("sum_balloons")
        val sumBalloons: Int?
)