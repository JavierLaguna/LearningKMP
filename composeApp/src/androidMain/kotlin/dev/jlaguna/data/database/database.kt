package dev.jlaguna.data.database

import androidx.room.Room
import androidx.room.RoomDatabase

fun getDatabaseBuilder(context: android.content.Context): RoomDatabase.Builder<MoviesDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath(DATABASE_NAME)
    return Room.databaseBuilder(
        context = appContext,
        name = dbFile.absolutePath
    )
}