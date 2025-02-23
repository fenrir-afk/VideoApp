package com.example.videoapp.video.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.*
import com.example.videoapp.video.data.local.dao.VideoDao
import com.example.videoapp.video.data.local.entity.VideoEntity

@Database(
    entities = [VideoEntity::class],
    version = 1,
    exportSchema = true
)
abstract class VideoDatabase : RoomDatabase() {

    abstract fun videoDao(): VideoDao

    companion object {

        @Volatile
        private var INSTANCE: VideoDatabase? = null

        fun getDatabase(context: Context): VideoDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            if (INSTANCE == null) {
                synchronized(this) {
                    // Pass the database to the INSTANCE
                    INSTANCE = buildDatabase(context)
                }
            }
            // Return database.
            return INSTANCE!!
        }

        private fun buildDatabase(context: Context): VideoDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                VideoDatabase::class.java,
                "notes_database"
            )
                .build()
        }
    }
}