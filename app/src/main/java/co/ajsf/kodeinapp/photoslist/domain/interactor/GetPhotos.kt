package co.ajsf.kodeinapp.photoslist.domain.interactor

import arrow.core.Either
import co.ajsf.kodeinapp.common.domain.error.Error
import co.ajsf.kodeinapp.common.domain.interactor.UseCase
import co.ajsf.kodeinapp.common.domain.model.Photo
import co.ajsf.kodeinapp.photoslist.domain.repository.PhotosRepository

class GetPhotos(private val photosRepository: PhotosRepository) :
    UseCase<GetPhotos.Params, List<Photo>>() {

    override fun run(params: Params): Either<Error, List<Photo>> = photosRepository.getAll()

    data class Params(val page: Int, val query: String)
}