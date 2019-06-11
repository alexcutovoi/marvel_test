package br.com.alex.repository.store.model.remote

import com.google.gson.annotations.SerializedName

class BaseDataContainerResultEntity<T> (
    @SerializedName("offset") val offset: Int,
    @SerializedName("limit") val limit: Int,
    @SerializedName("total") val total: Int,
    @SerializedName("count") val count: Int,
    @SerializedName("results") val results: List<T>
)

//CharacterResultEntity