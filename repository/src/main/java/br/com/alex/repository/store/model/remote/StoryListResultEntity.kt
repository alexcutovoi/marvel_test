package br.com.alex.repository.store.model.remote

import br.com.alex.repository.store.model.base.BaseList

class StoryListResultEntity(
    returned: Int,
    stories: List<StorySummaryResultEntity>) : BaseList<List<StorySummaryResultEntity>>(returned, stories)