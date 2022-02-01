package com.test.newsapp.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.test.newsapp.news.model.NewsBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(NewsBase.resultList::class), version = 1, exportSchema = false)
abstract class NewsRoomDatabase : RoomDatabase() {

    abstract fun newsDAO(): NewsDAO

    private class NewsDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

        }
    }

    companion object {
        @Volatile
        private var INSTANCE: NewsRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): NewsRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NewsRoomDatabase::class.java,
                    "room_database"
                )
                    .addCallback(NewsDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}