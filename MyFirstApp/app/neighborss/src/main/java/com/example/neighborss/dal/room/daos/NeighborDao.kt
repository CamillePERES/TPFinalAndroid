package com.example.neighborss.dal.room.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.neighborss.dal.room.entitites.NeighborEntity

@Dao
interface NeighborDao {

    /***
     * recupere la liste des voisins
     * interface qui sera notifiee quand la liste de voisins change dans la base
     */
    @Query("SELECT * from neighbors")
    fun getNeighbors(): LiveData<List<NeighborEntity>>

    @Insert
    fun add(neighborEntity: NeighborEntity)

    @Delete
    fun delete(neighborEntity: NeighborEntity)

    @Update
    fun update(neighborEntity: NeighborEntity)

    @Query("UPDATE neighbors SET favorite = :favorite WHERE id = :id")
    fun updateStatus(favorite : Boolean, id : Long)

}