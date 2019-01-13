package co.ajsf.kodeinapp.detail.domain.interactor

import arrow.core.Either
import co.ajsf.kodeinapp.common.domain.error.Error
import co.ajsf.kodeinapp.common.domain.interactor.UseCase
import co.ajsf.kodeinapp.common.domain.model.Photo
import co.ajsf.kodeinapp.photoslist.domain.repository.PhotosRepository

class PhotoNotFound : Error.FeatureError()

class GetPhoto(private val photosRepository: PhotosRepository) : UseCase<String, Photo>() {
    override fun run(params: String): Either<Error, Photo> =
        photosRepository.getPhoto(params)

}