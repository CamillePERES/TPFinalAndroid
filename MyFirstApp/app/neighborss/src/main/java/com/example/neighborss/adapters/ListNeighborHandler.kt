package com.example.neighborss.adapters

import com.example.neighborss.dto.NeighborDTO
import com.example.neighborss.models.Neighbor

interface ListNeighborHandler {

    fun onDeleteNeibor(neighbor: Neighbor)

    fun onCreateNeibor(neighbor: NeighborDTO)

    fun onItemClick(neighbor: Neighbor)

    fun onUpdateNeibor(model: NeighborDTO)

    fun favoriteNeibor(neighbor:Neighbor)

}