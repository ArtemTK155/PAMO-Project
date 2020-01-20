package edu.ufp.pamfinalproject.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note_Entity::class], version = 10, exportSchema = false)
abstract class AppDB : RoomDatabase() {

    abstract fun noteDao(): Note_Dao

    companion object {
        @Volatile
        private var instance: AppDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            AppDB::class.java, "app.db"
        ).build()
    }
}