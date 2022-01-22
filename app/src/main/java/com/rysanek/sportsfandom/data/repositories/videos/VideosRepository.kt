package com.rysanek.sportsfandom.data.repositories.videos

import com.rysanek.sportsfandom.data.remote.dtos.videos.VideosResults

interface VideosRepository {

    suspend fun getVideos(): VideosResults
}