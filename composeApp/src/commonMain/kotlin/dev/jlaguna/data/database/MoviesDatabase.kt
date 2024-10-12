package dev.jlaguna.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.jlaguna.data.Movie

const val DATABASE_NAME = "movies.db"

interface DB {
    fun clearAllTables()
}

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MoviesDatabase: RoomDatabase(), DB {
    abstract fun moviesDao(): MoviesDao

    override fun clearAllTables() {
        // Empty
    }
}