package com.rysanek.sportsfandom.data.remote.dtos.scores

import com.google.gson.annotations.SerializedName
import com.rysanek.sportsfandom.domain.utils.Constants.SCORES_SERIAL_NAME

data class ScoreResults(
    @SerializedName(SCORES_SERIAL_NAME) val scores: List<ScoreDTO>
)