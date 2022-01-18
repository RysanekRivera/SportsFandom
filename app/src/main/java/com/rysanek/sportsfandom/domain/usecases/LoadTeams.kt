package com.rysanek.sportsfandom.domain.usecases

import android.widget.ImageView
import com.rysanek.sportsfandom.data.local.entities.TeamEntity
import com.rysanek.sportsfandom.data.repositories.teams.TeamsRepositoryImpl
import javax.inject.Inject

class LoadTeams @Inject constructor(
    private val repository: TeamsRepositoryImpl
) {
    fun getTeamsInfo() = repository.getTeamsInfoFromDbLiveData()

    fun loadImagesToView(url: String, imageView: ImageView) = repository.loadImagesToView(url, imageView)

    suspend fun saveTeamToDb(team: TeamEntity) = repository.insertTeamToDb(team)

    suspend fun deleteTeamFromDb(team: TeamEntity) = repository.deleteTeamFromDb(team)

}