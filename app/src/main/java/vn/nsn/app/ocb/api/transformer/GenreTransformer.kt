package vn.nsn.app.ocb.api.transformer

import vn.nsn.app.ocb.api.dto.GenreDTO
import vn.nsn.app.ocb.api.entity.Genre

fun ArrayList<GenreDTO>.toGenres(): ArrayList<Genre> {
    return ArrayList(map {
        it.toGenre()
    })
}

fun GenreDTO.toGenre(): Genre {
    return Genre(title ?: "", stories?.toStories() ?: ArrayList())
}