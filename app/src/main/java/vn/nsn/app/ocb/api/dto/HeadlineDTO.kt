package vn.nsn.app.ocb.api.dto

import com.google.gson.annotations.SerializedName

class HeadlineDTO {
    @SerializedName("carousel")
    var carousel: ArrayList<CarouselDTO>? = null
    @SerializedName("sections")
    var sections: ArrayList<GenreDTO>? = null
}