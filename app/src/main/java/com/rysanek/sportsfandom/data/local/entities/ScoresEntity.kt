package com.rysanek.sportsfandom.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rysanek.sportsfandom.domain.utils.Constants.SCORES_TABLE

@Entity(tableName = SCORES_TABLE)
data class ScoresEntity(
    val dateEvent: String?,
    val idAwayTeam: String?,
    val idEvent: String?,
    val idHomeTeam: String?,
    val idLeague: String?,
    val idLiveScore: String?,
    val idPlayer: String?,
    val intAwayScore: String?,
    val intEventScore: String?,
    val intEventScoreTotal: String?,
    val intHomeScore: String?,
    val strAwayTeam: String?,
    val strAwayTeamBadge: String?,
    val strEventTime: String?,
    val strHomeTeam: String?,
    val strHomeTeamBadge: String?,
    val strLeague: String?,
    val strPlayer: String?,
    val strProgress: String?,
    val strSport: String?,
    val strStatus: String?,
    val updated: String?
){
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}