package br.com.alex.repository.store.model.base

import com.google.gson.annotations.SerializedName

open class BaseList<T>(
    @SerializedName("returned") val returned: Int,
    @SerializedName("items")val summaryItems: T
)