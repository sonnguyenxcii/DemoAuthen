package vn.nsn.app.iotp.api.dto

import com.google.gson.annotations.SerializedName

class CarouselDTO {
    @SerializedName("category")
    var category: String? = ""
    @SerializedName("disp_order")
    var displayOrder: Int = 0
    @SerializedName("story")
    var storyDTO: StoryDTO? = null
    @SerializedName("notification")
    var notificationDTO: NotificationDTO? = null
}