package br.com.alex.repository.store.model.base

import com.google.gson.annotations.SerializedName

open class BaseSummary<T>(
    @SerializedName("resourceURI") val resourceUri: T,
    @SerializedName("name") val name: T
)