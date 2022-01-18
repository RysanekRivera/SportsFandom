package com.rysanek.sportsfandom.data.local.db.search

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rysanek.sportsfandom.data.local.entities.SearchEntity

@Database(
    entities = [SearchEntity::class],
    version = 1,
    exportSchema = false
)
abstract class SearchDatabase: RoomDatabase(){

    abstract val searchDAO: SearchDAO
}