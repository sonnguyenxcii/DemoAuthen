package vn.nsn.app.iotp.api.transformer

import vn.nsn.app.iotp.api.dto.GenreDTO
import vn.nsn.app.iotp.api.entity.Genre

fun ArrayList<GenreDTO>.toGenres(): ArrayList<Genre> {
    return ArrayList(map {
        it.toGenre()
    })
}

fun GenreDTO.toGenre(): Genre {
    return Genre(title ?: "", stories?.toStories() ?: ArrayList())
}