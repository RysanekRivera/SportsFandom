package com.rysanek.sportsfandom.data.local.db.teams

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rysanek.sportsfandom.data.local.db.search.SearchDAO
import com.rysanek.sportsfandom.data.local.entities.SearchEntity
import com.rysanek.sportsfandom.data.local.entities.TeamEntity

@Database(
    entities = [TeamEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TeamsDatabase: RoomDatabase(){

    abstract val teamsDAO: TeamsDAO
}