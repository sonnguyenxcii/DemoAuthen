package vn.nsn.app.ocb.api.transformer

import vn.nsn.app.ocb.api.dto.NotificationDTO
import vn.nsn.app.ocb.api.entity.Notification

fun NotificationDTO.toNotification(): Notification {
    return Notification(id ?: 0, title ?: "", url, backgroundImageUrl ?: "")
}