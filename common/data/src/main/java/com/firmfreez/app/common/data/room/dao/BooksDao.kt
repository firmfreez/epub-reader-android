package com.firmfreez.app.common.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.firmfreez.app.common.data.room.entities.BookEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BooksDao {

    @Query("SELECT * FROM books ORDER BY addedAtMillis DESC")
    fun observeAll(): Flow<List<BookEntity>>

    @Query("UPDATE books SET lastLocatorJson = :locatorJson, progress = :progress WHERE id = :bookId")
    suspend fun updateReadingPosition(
        bookId: String,
        locatorJson: String?,
        progress: Float?
    )

    @Query("SELECT lastLocatorJson FROM books WHERE id = :bookId")
    suspend fun getLastLocatorJson(bookId: String): String?

    @Query("SELECT EXISTS(SELECT 1 FROM books WHERE fileHash = :fileHash)")
    suspend fun isBookExists(fileHash: String): Boolean

    @Query("SELECT * FROM books WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): BookEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(book: BookEntity)

    @Query("DELETE FROM books WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("UPDATE books SET isAvailable = :isAvailable WHERE id = :id")
    suspend fun updateAvailability(
        id: String,
        isAvailable: Boolean,
    )
}