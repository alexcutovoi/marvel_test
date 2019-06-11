package br.com.alex.repository.store.model.remote

import com.google.gson.annotations.SerializedName

class BaseDataWrapperResultEntity<T> (
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: String,
    @SerializedName("data") val data: BaseDataContainerResultEntity<T>
)
