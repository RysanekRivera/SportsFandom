package com.rysanek.sportsfandom.data.remote.apis

import com.rysanek.sportsfandom.data.remote.dtos.search.SearchResults
import com.rysanek.sportsfandom.domain.utils.MetaData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchAPI {

    @GET("{apiKey}/searchteams.php")
    suspend fun fetchTeams(
        @Path("apiKey", encoded = true) apiKey: String = MetaData.SPORTS,
        @Query("t", encoded = true) team: String,
    ): SearchResults
}