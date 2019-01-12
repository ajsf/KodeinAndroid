package co.ajsf.kodeinapp.common.data.network.mapper

import co.ajsf.kodeinapp.common.data.network.model.PhotoDto
import co.ajsf.kodeinapp.common.data.network.model.author
import co.ajsf.kodeinapp.common.domain.model.Photo

fun List<PhotoDto>.toDomain(): List<Photo> = this.map { it.toDomain() }

fun PhotoDto.toDomain(): Photo = Photo(
    this.id,
    this.urls.regular,
    this.author,
    this.description,
    this.created_at
)
