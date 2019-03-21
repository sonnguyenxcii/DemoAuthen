package vn.nsn.app.ocb.api.transformer

import vn.nsn.app.ocb.api.dto.CarouselDTO
import vn.nsn.app.ocb.api.entity.Carousel

fun ArrayList<CarouselDTO>.toCarousels(): ArrayList<Carousel> {
    return ArrayList(map {
        it.toCarousel()
    })
}

fun CarouselDTO.toCarousel(): Carousel {
    return Carousel().apply {
        category = this@toCarousel.category
        story = this@toCarousel.storyDTO?.toStory()
        notification = this@toCarousel.notificationDTO?.toNotification()
        displayOrder = this@toCarousel.displayOrder
    }
}