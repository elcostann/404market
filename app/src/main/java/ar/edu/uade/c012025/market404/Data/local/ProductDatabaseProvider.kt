package ar.edu.uade.c012025.market404.Data.local

import android.content.Context
import androidx.room.Room

object ProductDatabaseProvider {
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "market404_db"
        ).build()
    }
}

