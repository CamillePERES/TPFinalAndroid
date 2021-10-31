package com.example.neighborss.models

import com.example.neighborss.dal.room.entitites.NeighborEntity

data class Neighbor(val id: Long,
                    val name: String,
                    val avatarUrl: String,
                    val address: String,
                    val phoneNumber: String,
                    val aboutMe: String,
                    var favorite: Boolean,
                    val webSite: String){

}
