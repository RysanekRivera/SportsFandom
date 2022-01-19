package com.rysanek.sportsfandom.data.repositories.search

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import androidx.lifecycle.LiveData
import com.rysanek.sportsfandom.data.local.entities.SearchEntity
import com.rysanek.sportsfandom.data.remote.dtos.search.SearchResults
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    // Local
    suspend fun fetchTeams(teamName: String): Flow<SearchResults>

    suspend fun insertTeamToDb(teams: List<SearchEntity>)

    fun getTeamData(teamName: String): List<SearchEntity>

    suspend fun deleteAllTeamsFromDb()

    suspend fun updateTeams(teams: List<SearchEntity>)

    fun getSearchResultsFromDbLiveData(): LiveData<List<SearchEntity>>

    fun getSearchResults(): List<SearchEntity>

    // Remote
    fun hasInternetConnection(context: Context): Boolean

    fun loadImagesToView(imageUrl: String, imageView: ImageView)

}