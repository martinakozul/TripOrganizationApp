package com.camunda.triporganization.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.camunda.triporganization.model.Trip
import kotlinx.coroutines.flow.Flow

@Dao
interface ProcessDao {
    @Query("SELECT * FROM trips")
    fun getAll(): Flow<List<Trip>>

    @Query("SELECT * FROM trips WHERE id = :id")
    fun getTripDetails(id: Long): Flow<Trip>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(trip: Trip)
}