package com.rysanek.sportsfandom.data.repositories.teams

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import androidx.lifecycle.LiveData
import com.rysanek.sportsfandom.data.local.entities.SearchEntity
import com.rysanek.sportsfandom.data.local.entities.TeamEntity
import com.rysanek.sportsfandom.data.remote.dtos.search.SearchResults
import kotlinx.coroutines.flow.Flow

interface TeamsRepository {

    suspend fun insertTeamToDb(team: TeamEntity)

    suspend fun deleteTeamFromDb(team: TeamEntity)

    suspend fun insertTeamsListToDb(teams: List<TeamEntity>)

    suspend fun deleteAllTeamsFromDb()

    suspend fun updateTeams(teams: List<TeamEntity>)

    fun getTeamsInfoFromDb(teamName: String): List<TeamEntity>

    fun getTeamsInfoFromDbLiveData(): LiveData<List<TeamEntity>>

    fun loadImagesToView(imageUrl: String, imageView: ImageView)

}