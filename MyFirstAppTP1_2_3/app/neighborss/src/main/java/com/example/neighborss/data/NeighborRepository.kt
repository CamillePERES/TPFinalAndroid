package com.example.neighborss.data

import com.example.neighborss.data.service.DummyNeighborApiService
import com.example.neighborss.data.service.NeighborApiService
import com.example.neighborss.dto.NeighborDTO
import com.example.neighborss.models.Neighbor

class NeighborRepository {

    private val apiService: NeighborApiService

    init {
        apiService = DummyNeighborApiService()
    }

    fun getNeighbours(): List<Neighbor> = apiService.neighbours


    fun deleteNeighbours(neighbor : Neighbor){
        apiService.deleteNeighbour(neighbor);
    }

    fun createNeighbour(neighbor: NeighborDTO){
        val id = getNeighbours().last().id + 1
        val neigh = Neighbor(id, neighbor.name, neighbor.avatarUrl, neighbor.address, neighbor.phoneNumber, neighbor.aboutMe, false, neighbor.webSite)
        apiService.createNeighbour(neigh);
    }

    fun updateNeighbor(neighbor: NeighborDTO){
        val neigh = neighbor.id?.let { Neighbor(it, neighbor.name, neighbor.avatarUrl, neighbor.address, neighbor.phoneNumber, neighbor.aboutMe, false, neighbor.webSite) }
        if (neigh != null) {
            apiService.updateDataNeighbour(neigh)
        }
    }

    companion object {
        private var instance: NeighborRepository? = null
        fun getInstance(): NeighborRepository {
            if (instance == null) {
                instance = NeighborRepository()
            }
            return instance!!
        }
    }
}