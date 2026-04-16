package com.firmfreez.app.common.data.room.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.firmfreez.app.common.data.room.dao.BooksDao
import com.firmfreez.app.common.data.room.entities.BookEntity

@Database(
    entities = [BookEntity::class],
    version = 4,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3),
        AutoMigration(from = 3, to = 4),
    ]
)
abstract class BooksDatabase : RoomDatabase() {
    abstract fun booksDao(): BooksDao
}