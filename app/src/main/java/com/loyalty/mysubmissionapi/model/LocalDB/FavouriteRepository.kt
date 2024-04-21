package com.loyalty.mysubmissionapi.model.LocalDB

import android.app.Application
import androidx.lifecycle.LiveData
import com.loyalty.mysubmissionapi.model.LocalDB.entity.FavouriteEntity
import com.loyalty.mysubmissionapi.model.LocalDB.room.dao.FavouriteDao
import com.loyalty.mysubmissionapi.model.LocalDB.room.db.FavouriteDB
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavouriteRepository(application: Application) {
   private val favouriteDao: FavouriteDao
   private val executionerServ:ExecutorService = Executors.newSingleThreadExecutor()

   init {
      val db = FavouriteDB.conDatabase(application)
      favouriteDao = db.favouriteDao()
   }

   fun getAll(): LiveData<List<FavouriteEntity>> = favouriteDao.getAll()

   fun getUsingId(id: String): LiveData<List<FavouriteEntity>> = favouriteDao.getUsingId(id)

   fun insert(favouriteEntity: FavouriteEntity) = executionerServ.execute{ favouriteDao.insert(favouriteEntity)}

   fun delete(favouriteEntity: FavouriteEntity) = executionerServ.execute{ favouriteDao.delete(favouriteEntity)}
}