package vn.nsn.app.iotp.api.transformer

import vn.nsn.app.iotp.api.dto.NotificationDTO
import vn.nsn.app.iotp.api.entity.Notification

fun NotificationDTO.toNotification(): Notification {
    return Notification(id ?: 0, title ?: "", url, backgroundImageUrl ?: "")
}