package com.example.neighborss.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.neighborss.di.DI
import com.example.neighborss.dto.NeighborDTO
import com.example.neighborss.models.Neighbor
import com.example.neighborss.repositories.NeighborRepository

class RepositoryViewModel: ViewModel() {

    private val repository: NeighborRepository = DI.repository

    val neighbors: LiveData<LiveData<List<Neighbor>>>
        get() = repository.neighborSource


    fun delete(neighbor:Neighbor){
        repository.deleteNeighbours(neighbor)
    }

    fun create(neighborDTO: NeighborDTO){
        repository.createNeighbour(neighborDTO)
    }

    fun update(neighborDTO: NeighborDTO){
        repository.updateNeighbor(neighborDTO)
    }

    fun favorite(neighbor: Neighbor){
        repository.favoriteStatusNeighbor(neighbor)
    }

}