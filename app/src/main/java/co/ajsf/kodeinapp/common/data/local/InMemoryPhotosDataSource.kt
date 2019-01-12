package co.ajsf.kodeinapp.common.data.local

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import co.ajsf.kodeinapp.common.data.network.PhotosNotFound
import co.ajsf.kodeinapp.common.domain.error.Error
import co.ajsf.kodeinapp.common.domain.model.Photo
import co.ajsf.kodeinapp.common.domain.repository.PhotosLocalDataSource

class InMemoryPhotosDataSource(private val ttlMillis: Long) : PhotosLocalDataSource {

    private var photos: List<PersistedPhoto> = emptyList()

    override fun getAll(): Either<Error, List<Photo>> = when {
        photos.isEmpty() -> PhotosNotFound().left()
        photos.any { it.isInvalid(ttlMillis) } -> PhotosNotFound().left().also { photos = emptyList(); }
        else -> photos.map { it.photo }.right()
    }

    override fun getPhoto(photoId: String): Either<Error, Photo> {
        val persistedPhoto = photos.find { it.photo.id == photoId }
        return when {
            persistedPhoto == null -> PhotosNotFound().left()
            persistedPhoto.isInvalid(ttlMillis) -> PhotosNotFound().left().also { photos -= persistedPhoto }
            else -> persistedPhoto.photo.right()
        }
    }

    override fun save(photos: List<Photo>) {
        val persistedPhotosToSave = photos.map { PersistedPhoto(System.currentTimeMillis(), it) }
        val photosNotPersistedYet = persistedPhotosToSave - this.photos
        val photosAlreadyPersisted = (persistedPhotosToSave intersect this.photos).map {
            it.copy(storedTimeMillis = System.currentTimeMillis())
        }
        this.photos = photosNotPersistedYet + photosAlreadyPersisted
    }

    override fun save(photo: Photo) {
        val persistedPhoto = PersistedPhoto(System.currentTimeMillis(), photo)
        this.photos = if (photos.contains(persistedPhoto)) {
            photos - persistedPhoto + persistedPhoto
        } else
            photos + persistedPhoto
    }
}

data class PersistedPhoto(val storedTimeMillis: Long, val photo: Photo) {
    override fun equals(other: Any?): Boolean = other is PersistedPhoto && photo == other.photo
    override fun hashCode(): Int {
        var result = storedTimeMillis.hashCode()
        result = 21 * result + photo.hashCode()
        return result
    }
}

fun PersistedPhoto.isInvalid(ttlMillis: Long) = System.currentTimeMillis() - storedTimeMillis >= ttlMillis