package com.firmfreez.app.common.data.di

import android.content.Context
import androidx.room.Room
import com.firmfreez.app.common.data.room.db.BooksDatabase
import org.koin.core.annotation.Module
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single

@Module
class DatabaseModule {

    @Single
    fun provideBooksDatabase(@Provided context: Context): BooksDatabase {
        return Room.databaseBuilder(
            context,
            BooksDatabase::class.java,
            "books_database"
        ).build()
    }
}
