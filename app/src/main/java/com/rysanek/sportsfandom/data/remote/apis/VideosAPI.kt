package com.rysanek.sportsfandom.data.remote.apis

import com.rysanek.sportsfandom.data.remote.dtos.videos.VideosResults
import com.rysanek.sportsfandom.domain.utils.MetaData
import retrofit2.http.GET
import retrofit2.http.Path

interface VideosAPI {

    @GET("{apiKey}/eventshighlights.php")
    suspend fun fetchVideos(
        @Path("apiKey", encoded = true) apiKey: String = MetaData.SPORTS
    ): VideosResults
}