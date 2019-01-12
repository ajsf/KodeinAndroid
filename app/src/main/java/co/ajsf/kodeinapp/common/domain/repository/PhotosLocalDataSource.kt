package co.ajsf.kodeinapp.common.domain.repository

import arrow.core.Either
import co.ajsf.kodeinapp.common.domain.error.Error
import co.ajsf.kodeinapp.common.domain.model.Photo

interface PhotosLocalDataSource {

    fun getAll(): Either<Error, List<Photo>>

    fun getPhoto(photoId: String): Either<Error, Photo>

    fun save(photos: List<Photo>): Unit

    fun save(photo: Photo): Unit
}