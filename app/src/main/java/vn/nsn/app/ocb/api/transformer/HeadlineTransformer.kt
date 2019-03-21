package vn.nsn.app.ocb.api.transformer

import vn.nsn.app.ocb.api.dto.HeadlineDTO
import vn.nsn.app.ocb.api.entity.Headline

fun HeadlineDTO.toHeadline(): Headline {
    return Headline(carousel?.toCarousels()
            ?: ArrayList(), sections?.toGenres() ?: ArrayList())
}