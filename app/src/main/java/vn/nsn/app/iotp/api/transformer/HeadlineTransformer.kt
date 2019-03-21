package vn.nsn.app.iotp.api.transformer

import vn.nsn.app.iotp.api.dto.HeadlineDTO
import vn.nsn.app.iotp.api.entity.Headline

fun HeadlineDTO.toHeadline(): Headline {
    return Headline(carousel?.toCarousels()
            ?: ArrayList(), sections?.toGenres() ?: ArrayList())
}