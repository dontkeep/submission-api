package com.loyalty.mysubmissionapi.model.LocalDB.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.loyalty.mysubmissionapi.model.LocalDB.entity.FavouriteEntity
import com.loyalty.mysubmissionapi.model.LocalDB.room.dao.FavouriteDao

@Database(entities = [FavouriteEntity::class], version = 1)
abstract class FavouriteDB : RoomDatabase() {
   abstract fun favouriteDao(): FavouriteDao

   companion object {
      @Volatile
      private var inst: FavouriteDB ?= null

      @JvmStatic
      fun conDatabase(context: Context) : FavouriteDB =
         inst ?: synchronized(FavouriteDB::class.java) {
            inst ?: Room.databaseBuilder(
               context.applicationContext,
               FavouriteDB::class.java, "database"
            ).build()
         }

   }
}