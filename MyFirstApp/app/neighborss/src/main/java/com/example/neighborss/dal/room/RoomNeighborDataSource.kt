package com.example.neighborss.dal.room

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.neighborss.dal.room.daos.NeighborDao
import com.example.neighborss.dal.utils.toNeighbor
import com.example.neighborss.dal.utils.toNeighborEntity
import com.example.neighborss.models.Neighbor

class RoomNeighborDataSource(application: Application) : NeighborDatasource {

    private val database: NeighborDatabase = NeighborDatabase.getDatabase(application)
    private val dao: NeighborDao = database.neighborDao()

    private val _neighors = MediatorLiveData<List<Neighbor>>()

    init {

        _neighors.addSource(dao.getNeighbors()) { entities ->
            _neighors.value = entities.map { entity ->
                entity.toNeighbor()
            }
        }
    }

    override val neighbours: LiveData<List<Neighbor>>
        get() = _neighors

    override fun deleteNeighbour(neighbor: Neighbor) {
        dao.delete(neighbor.toNeighborEntity())
    }

    override fun createNeighbour(neighbor: Neighbor) {
        dao.add(neighbor.toNeighborEntity())
    }

    override fun updateFavoriteStatus(neighbor: Neighbor) {
        var id = neighbor.id
        dao.updateStatus(neighbor.toNeighborEntity().favorite, id)
    }

    override fun updateDataNeighbour(neighbor: Neighbor) {
        dao.update(neighbor.toNeighborEntity())
    }






}