package co.ajsf.kodeinapp.common.data.network

import arrow.core.Either
import arrow.core.Try
import arrow.core.left
import arrow.core.right
import co.ajsf.kodeinapp.common.data.network.mapper.toDomain
import co.ajsf.kodeinapp.common.domain.error.Error
import co.ajsf.kodeinapp.common.domain.model.Photo
import co.ajsf.kodeinapp.common.domain.repository.PhotosNetworkDataSource

class PhotosNotFound : Error.FeatureError()

class UnsplashPhotosDataSource(private val service: UnsplashService) : PhotosNetworkDataSource {
    override fun getAll(): Either<Error, List<Photo>> =
        Try {
            service.getPhotos().execute()
        }.fold(ifFailure = {
            Error.ServerError().left()
        }, ifSuccess = { response ->
            if (response.isSuccessful) {
                val body = response.body()!!
                body.toDomain().right()
            } else PhotosNotFound().left()
        })

    override fun getPhoto(photoId: String): Either<Error, Photo> =
        Try {
            service.getPhoto(photoId).execute()
        }.fold(ifFailure = {
            Error.ServerError().left()
        }, ifSuccess = { response ->
            if (response.isSuccessful) {
                val body = response.body()!!
                body.toDomain().right()
            } else PhotosNotFound().left()
        })
}