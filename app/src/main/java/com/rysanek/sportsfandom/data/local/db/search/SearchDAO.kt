package com.rysanek.sportsfandom.data.local.db.search

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rysanek.sportsfandom.data.local.entities.SearchEntity
import com.rysanek.sportsfandom.domain.utils.Constants.SEARCH_TABLE


@Dao
interface SearchDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeamsListIntoDb(teams: List<SearchEntity>)

    @Query("DELETE FROM $SEARCH_TABLE")
    suspend fun deleteAllTeamsFromDb()

    @Query("SELECT * FROM $SEARCH_TABLE")
    fun getTeamsFromDbLiveData(): LiveData<List<SearchEntity>>

    @Query("SELECT * FROM $SEARCH_TABLE WHERE strTeam LIKE '%' || :team || '%'")
    fun getTeamsInfo(team: String): List<SearchEntity>

    @Update
    suspend fun updateTeams(teams: List<SearchEntity>)

}