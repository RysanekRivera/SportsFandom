package com.rysanek.sportsfandom.data.repositories.teams

import android.widget.ImageView
import com.bumptech.glide.RequestManager
import com.rysanek.sportsfandom.data.local.db.teams.TeamsDAO
import com.rysanek.sportsfandom.data.local.entities.TeamEntity
import javax.inject.Inject

class TeamsRepositoryImpl @Inject constructor(
    private val dao: TeamsDAO,
    private val glide: RequestManager
): TeamsRepository {

    override suspend fun updateTeams(teams: List<TeamEntity>) = dao.updateTeams(teams)

    override suspend fun deleteTeamFromDb(team: TeamEntity) = dao.deleteTeamFromDb(team)

    override suspend fun insertTeamToDb(team: TeamEntity) = dao.insertTeamToDb(team)

    override suspend fun insertTeamsListToDb(teams: List<TeamEntity>) = dao.insertTeamsListIntoDb(teams)

    override suspend fun deleteAllTeamsFromDb() = dao.deleteAllTeamsFromDb()

    override fun getTeamsInfoFromDb(teamName: String) = dao.getTeamsInfo(teamName)

    override fun getTeamsInfoFromDbLiveData() = dao.getTeamsFromDbLiveData()

    override fun loadImagesToView(imageUrl: String, imageView: ImageView) {
        glide.load(imageUrl).into(imageView)
    }
}