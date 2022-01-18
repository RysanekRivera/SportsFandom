package com.rysanek.sportsfandom.data.repositories.scores

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import androidx.lifecycle.LiveData
import com.rysanek.sportsfandom.data.local.entities.ScoresEntity
import com.rysanek.sportsfandom.data.remote.dtos.scores.ScoreResults
import kotlinx.coroutines.flow.Flow

interface ScoresRepository {

    // Local
    suspend fun fetchScores(): Flow<ScoreResults>

    suspend fun insertScoresToDb(scores: List<ScoresEntity>)

    suspend fun deleteAllScoresFromDb()

    suspend fun updateScores(scores: List<ScoresEntity>)

    fun getScoresFromDbLiveData(): LiveData<List<ScoresEntity>>

    fun getScoresFromDb(): List<ScoresEntity>

    // Remote
    fun hasInternetConnection(context: Context): Boolean

    fun loadImagesToView(imageUrl: String, imageView: ImageView)

}