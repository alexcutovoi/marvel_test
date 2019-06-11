package br.com.alex.repository.mapper

import br.com.alex.domain.model.ImageResultModel
import br.com.alex.repository.store.model.remote.ImageResultEntity


class ImageEntityMapper: EntityMapper<ImageResultEntity, ImageResultModel> {
    override fun fromEntity(e: ImageResultEntity): ImageResultModel {
        return ImageResultModel(
            e.path,
            e.extension
        )
    }

    override fun toEntity(m: ImageResultModel): ImageResultEntity {
        return ImageResultEntity(
            m.path,
            m.extension
        )
    }
}