package br.com.alex.repository.store.model.remote

import br.com.alex.repository.store.model.base.BaseSummary
import com.google.gson.annotations.SerializedName

class StorySummaryResultEntity(
    resourceUri: String,
    name: String,
    @SerializedName("type")val type: String): BaseSummary<String>(resourceUri, name)