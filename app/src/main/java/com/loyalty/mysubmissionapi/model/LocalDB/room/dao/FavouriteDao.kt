package com.loyalty.mysubmissionapi.model.LocalDB.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.loyalty.mysubmissionapi.model.LocalDB.entity.FavouriteEntity


@Dao
interface FavouriteDao {
   @Insert(onConflict = OnConflictStrategy.IGNORE)
   fun insert(favouriteEntity: FavouriteEntity)

   @Update
   fun update(favouriteEntity: FavouriteEntity)

   @Delete
   fun delete(favouriteEntity: FavouriteEntity)

   @Query("Select * from favourite")
   fun getAll(): LiveData <List<FavouriteEntity>>

   @Query("Select * from favourite where id = :id")
   fun getUsingId(id: String):LiveData<List<FavouriteEntity>>

}