package com.rysanek.sportsfandom.data.local.db.teams

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rysanek.sportsfandom.data.local.entities.SearchEntity
import com.rysanek.sportsfandom.data.local.entities.TeamEntity
import com.rysanek.sportsfandom.domain.utils.Constants.SEARCH_TABLE
import com.rysanek.sportsfandom.domain.utils.Constants.TEAMS_TABLE


@Dao
interface TeamsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeamToDb(team: TeamEntity)

    @Delete
    suspend fun deleteTeamFromDb(team: TeamEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeamsListIntoDb(teams: List<TeamEntity>)

    @Query("DELETE FROM $TEAMS_TABLE")
    suspend fun deleteAllTeamsFromDb()

    @Query("SELECT * FROM $TEAMS_TABLE")
    fun getTeamsFromDbLiveData(): LiveData<List<TeamEntity>>

    @Query("SELECT * FROM $TEAMS_TABLE WHERE strTeam LIKE '%' || :team || '%'")
    fun getTeamsInfo(team: String): List<TeamEntity>

    @Update
    suspend fun updateTeams(teams: List<TeamEntity>)

}