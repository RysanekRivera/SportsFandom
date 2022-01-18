package com.rysanek.sportsfandom.data.repositories.scores

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.RequestManager
import com.rysanek.sportsfandom.data.local.db.scores.ScoresDAO
import com.rysanek.sportsfandom.data.local.entities.ScoresEntity
import com.rysanek.sportsfandom.data.remote.apis.ScoresAPI
import com.rysanek.sportsfandom.domain.utils.hasInternetConnection
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ScoresRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val api: ScoresAPI,
    private val dao: ScoresDAO,
    private val glide: RequestManager
): ScoresRepository {
    
    override suspend fun fetchScores() = flow { emit(api.fetchScores()) }

    override suspend fun updateScores(scores: List<ScoresEntity>) = dao.updateScores(scores)

    override suspend fun insertScoresToDb(scores: List<ScoresEntity>) = dao.insertScoresListIntoDb(scores)
    
    override suspend fun deleteAllScoresFromDb() = dao.deleteAllScoresFromDb()

    override fun getScoresFromDbLiveData() = dao.getScoresFromDbLiveData()

    override fun getScoresFromDb() = dao.getScoresFromDb()

    override fun hasInternetConnection(context: Context) = context.hasInternetConnection()

    override fun loadImagesToView(imageUrl: String, imageView: ImageView) {
        glide.load(imageUrl).into(imageView)
    }
}