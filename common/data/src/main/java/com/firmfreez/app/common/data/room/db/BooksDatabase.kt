package com.firmfreez.app.common.data.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.firmfreez.app.common.data.room.dao.BooksDao
import com.firmfreez.app.common.data.room.entities.BookEntity

@Database(
    entities = [BookEntity::class],
    version = 1,
    exportSchema = true,
)
abstract class BooksDatabase : RoomDatabase() {
    abstract fun booksDao(): BooksDao
}