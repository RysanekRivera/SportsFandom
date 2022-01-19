package com.rysanek.sportsfandom.domain.usecases

import android.widget.ImageView
import com.rysanek.sportsfandom.data.local.entities.SearchEntity
import com.rysanek.sportsfandom.data.repositories.search.SearchRepositoryImpl
import javax.inject.Inject

class SearchTeams @Inject constructor(
    private val repository: SearchRepositoryImpl
) {
    fun getSearchData(teamName: String) = repository.getTeamData(teamName)

    suspend fun startFetch(teamName: String) =  repository.fetchTeams(teamName)

    suspend fun insertTeams(teams: List<SearchEntity>) = repository.insertTeamToDb(teams)

    suspend fun deleteAllTeams() = repository.deleteAllTeamsFromDb()

    suspend fun updateTeams(teams: List<SearchEntity>) = repository.updateTeams(teams)

    fun getSearchesFromDbLiveData() = repository.getSearchResultsFromDbLiveData()

    fun getSearchesFromDb() = repository.getSearchResults()

    fun getSearchListSize() = repository.getSearchResults().size

    fun loadImagesToView(url: String, imageView: ImageView) = repository.loadImagesToView(url, imageView)

}