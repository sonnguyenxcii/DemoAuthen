package vn.nsn.app.iotp.api.transformer

import vn.nsn.app.iotp.api.dto.MetaDTO
import vn.nsn.app.iotp.api.entity.Meta

fun MetaDTO.toMeta(): Meta {
    return Meta(newlyCreated ?: false)
}