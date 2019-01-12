package co.ajsf.kodeinapp.photoslist.domain.repository

import arrow.core.Either
import arrow.core.right
import co.ajsf.kodeinapp.common.domain.error.Error
import co.ajsf.kodeinapp.common.domain.model.Photo
import co.ajsf.kodeinapp.common.domain.repository.CachePolicy
import co.ajsf.kodeinapp.common.domain.repository.CachePolicy.*
import co.ajsf.kodeinapp.common.domain.repository.PhotosLocalDataSource
import co.ajsf.kodeinapp.common.domain.repository.PhotosNetworkDataSource

class PhotosRepository(
    private val localDataSource: PhotosLocalDataSource,
    private val networkDataSource: PhotosNetworkDataSource
) {

    fun getAll(policy: CachePolicy = CachePolicy.LocalFirst): Either<Error, List<Photo>> {
        return when (policy) {
            NetworkFirst -> networkDataSource.getAll().fold(
                { localDataSource.getAll() },
                { localDataSource.save(it); it.right() }
            )
            LocalFirst -> localDataSource.getAll().fold(
                { networkDataSource.getAll().map { localDataSource.save(it); it } },
                { it.right() }
            )
            LocalOnly -> localDataSource.getAll()
            NetworkOnly -> networkDataSource.getAll().map { localDataSource.save(it); it }
        }
    }

    fun getPhoto(photoId: String, policy: CachePolicy = CachePolicy.LocalFirst): Either<Error, Photo> {
        return when (policy) {
            NetworkFirst -> networkDataSource.getPhoto(photoId).fold(
                { localDataSource.getPhoto(photoId) },
                { localDataSource.save(it); it.right() }
            )
            LocalFirst -> localDataSource.getPhoto(photoId).fold(
                { networkDataSource.getPhoto(photoId).map { localDataSource.save(it); it } },
                { it.right() }
            )
            LocalOnly -> localDataSource.getPhoto(photoId)
            NetworkOnly -> networkDataSource.getPhoto(photoId).map { localDataSource.save(it); it }
        }
    }
}