package com.rysanek.sportsfandom.data.remote.apis

import com.rysanek.sportsfandom.data.remote.dtos.scores.ScoreResults
import com.rysanek.sportsfandom.domain.utils.MetaData
import retrofit2.http.GET
import retrofit2.http.Path

interface ScoresAPI {

    @GET("{apiKey}/livescore.php")
    suspend fun fetchScores(
        @Path("apiKey", encoded = true) apiKey: String = MetaData.SPORTS
    ): ScoreResults
}