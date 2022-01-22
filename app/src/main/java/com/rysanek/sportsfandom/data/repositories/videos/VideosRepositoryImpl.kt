package com.rysanek.sportsfandom.data.repositories.videos

import com.rysanek.sportsfandom.data.remote.apis.VideosAPI

class VideosRepositoryImpl (
    private val api: VideosAPI): VideosRepository {

    override suspend fun getVideos() = api.fetchVideos()

}