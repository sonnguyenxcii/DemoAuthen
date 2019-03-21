package vn.nsn.app.ocb.api.transformer

import vn.nsn.app.ocb.api.dto.MetaDTO
import vn.nsn.app.ocb.api.entity.Meta

fun MetaDTO.toMeta(): Meta {
    return Meta(newlyCreated ?: false)
}