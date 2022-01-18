package com.rysanek.sportsfandom.data.repositories.search

import android.content.Context
import android.widget.ImageView
import androidx.lifecycle.LiveData
import com.bumptech.glide.RequestManager
import com.rysanek.sportsfandom.data.local.db.search.SearchDAO
import com.rysanek.sportsfandom.data.local.entities.SearchEntity
import com.rysanek.sportsfandom.data.remote.apis.SearchAPI
import com.rysanek.sportsfandom.domain.utils.hasInternetConnection
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val api: SearchAPI,
    private val dao: SearchDAO,
    private val glide: RequestManager
): SearchRepository {
    
    override suspend fun fetchTeams(teamName: String) = flow { emit(api.fetchTeams(team = teamName)) }

    override suspend fun updateTeams(teams: List<SearchEntity>) = dao.updateTeams(teams)

    override suspend fun insertTeamToDb(teams: List<SearchEntity>) = dao.insertTeamsListIntoDb(teams)
    
    override suspend fun deleteAllTeamsFromDb() = dao.deleteAllTeamsFromDb()
    
    override fun getSearchResultsFromDbLiveData(): LiveData<List<SearchEntity>> = dao.getTeamsFromDbLiveData()

    override fun getSearchResults(): List<SearchEntity> = dao.getTeamsFromDb()

    override fun hasInternetConnection(context: Context) = context.hasInternetConnection()

    override fun loadImagesToView(imageUrl: String, imageView: ImageView) {
        glide.load(imageUrl).into(imageView)
    }
}