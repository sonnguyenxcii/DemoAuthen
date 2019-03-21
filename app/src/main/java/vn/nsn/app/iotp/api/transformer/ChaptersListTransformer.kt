package vn.nsn.app.iotp.api.transformer

import vn.nsn.app.iotp.api.dto.*
import vn.nsn.app.iotp.api.entity.*

fun ChaptersListItemDTO.toChapterListItem () = ChapterListItem(
        id = -1,
        number = 0,
        title = "",
        sumConsumedPeeps = 0,
        didComplete = false,
        createdAt = ""
)

fun ChapterListDTO.toChaptersList () = ChaptersList(
    chapters.map {
        it.toChapterListItem()
    }
)

