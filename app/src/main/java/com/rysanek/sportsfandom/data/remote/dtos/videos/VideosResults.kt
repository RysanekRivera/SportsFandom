package com.rysanek.sportsfandom.data.remote.dtos.videos

import com.google.gson.annotations.SerializedName
import com.rysanek.sportsfandom.domain.utils.Constants.VIDEOS_SERIAL_NAME

data class VideosResults(
    @SerializedName(VIDEOS_SERIAL_NAME) val videos: List<VideosDTO>
)