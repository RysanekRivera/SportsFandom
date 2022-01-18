package com.rysanek.sportsfandom.domain.usecases

import android.widget.ImageView
import com.rysanek.sportsfandom.data.local.entities.ScoresEntity
import com.rysanek.sportsfandom.data.repositories.scores.ScoresRepositoryImpl
import javax.inject.Inject

class FetchScores @Inject constructor(
    private val repository: ScoresRepositoryImpl
) {
    suspend fun startFetch() =  repository.fetchScores()

    suspend fun insertScores(scores: List<ScoresEntity>) = repository.insertScoresToDb(scores)

    suspend fun deleteAllScores() = repository.deleteAllScoresFromDb()

    suspend fun updateScores(scores: List<ScoresEntity>) = repository.updateScores(scores)

    fun getScoresListSize() = repository.getScoresFromDb().size

    fun getScoresLiveData() = repository.getScoresFromDbLiveData()

    fun loadImages(url: String, imageView: ImageView) = repository.loadImagesToView(url, imageView)

}