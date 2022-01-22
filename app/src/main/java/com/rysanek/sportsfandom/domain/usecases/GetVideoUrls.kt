package com.rysanek.sportsfandom.domain.usecases

import com.rysanek.sportsfandom.data.repositories.videos.VideosRepositoryImpl
import javax.inject.Inject

class GetVideoUrls @Inject constructor(
    private val repository: VideosRepositoryImpl
) {

    suspend fun fetchVideoUrls() = repository.getVideos()

}