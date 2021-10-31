package com.example.neighborss.dal.utils

import com.example.neighborss.dal.room.entitites.NeighborEntity
import com.example.neighborss.dto.NeighborDTO
import com.example.neighborss.models.Neighbor

fun Neighbor.toNeighborDTO() = NeighborDTO(
    id = id,
    name = name,
    avatarUrl = avatarUrl,
    address = address,
    phoneNumber = phoneNumber,
    aboutMe = aboutMe,
    favorite = favorite,
    webSite = webSite ?: ""
)