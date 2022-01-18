package com.rysanek.sportsfandom.data.local.db.scores

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rysanek.sportsfandom.data.local.entities.ScoresEntity

@Database(
    entities = [ScoresEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ScoresDatabase: RoomDatabase(){

    abstract val scoresDAO: ScoresDAO
}