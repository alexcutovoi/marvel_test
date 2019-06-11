package br.com.alex.domain.model

class BaseDataWrapperResultModel<T> (
    val code: Int,
    val status: String,
    val data: T
)
//BaseDataContainerResultModel