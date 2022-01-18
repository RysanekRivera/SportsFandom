package com.rysanek.sportsfandom.data.remote.dtos.search

import com.google.gson.annotations.SerializedName
import com.rysanek.sportsfandom.domain.utils.Constants.SEARCH_SERIAL_NAME

data class SearchResults(
    @SerializedName(SEARCH_SERIAL_NAME) val searchResults: List<SearchDTO>
)