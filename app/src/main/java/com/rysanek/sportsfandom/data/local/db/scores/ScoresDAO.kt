package com.rysanek.sportsfandom.data.local.db.scores

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rysanek.sportsfandom.data.local.entities.ScoresEntity
import com.rysanek.sportsfandom.data.local.entities.SearchEntity
import com.rysanek.sportsfandom.domain.utils.Constants.SCORES_TABLE

@Dao
interface ScoresDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScoresListIntoDb(teams: List<ScoresEntity>)

    @Query("DELETE FROM $SCORES_TABLE")
    suspend fun deleteAllScoresFromDb()

    @Query("SELECT * FROM $SCORES_TABLE ORDER BY dateEvent ASC")
    fun getScoresFromDbLiveData(): LiveData<List<ScoresEntity>>

    @Query("SELECT * FROM $SCORES_TABLE")
    fun getScoresFromDb(): List<ScoresEntity>

    @Update
    suspend fun updateScores(teams: List<ScoresEntity>)

}