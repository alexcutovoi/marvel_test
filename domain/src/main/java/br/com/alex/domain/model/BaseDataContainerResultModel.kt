package br.com.alex.domain.model

class BaseDataContainerResultModel<T> (
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<T>
)

//CharacterResultModel