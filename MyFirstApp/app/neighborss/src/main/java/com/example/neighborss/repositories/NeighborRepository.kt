package com.example.neighborss.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.neighborss.dal.memory.InMemoryNeighborDataSource
import com.example.neighborss.dal.room.NeighborDatasource
import com.example.neighborss.dal.room.RoomNeighborDataSource
import com.example.neighborss.dal.utils.toNeighborDTO
import com.example.neighborss.dto.NeighborDTO
import com.example.neighborss.models.Neighbor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class NeighborRepository private constructor (private val application: Application){

    private var dataSource: NeighborDatasource

    //private val apiService: NeighborDatasource

    private val executor : ExecutorService = Executors.newFixedThreadPool(2)
    private var observe : MutableLiveData<LiveData<List<Neighbor>>> = MutableLiveData()

    //on cree une variable LiveData qui est la meme que la MutableLiveData mais on ne veut
    //pas qu'elle puisse etre modifiee  (MutableLiveData peut etre modifiee)
    //pour recup sa valeur, on cree son getter.
    val neighborSource : LiveData<LiveData<List<Neighbor>>>
        get() = observe

    private fun getNeighbours(): LiveData<List<Neighbor>> = dataSource.neighbours

    init {
        //apiService = InMemoryNeighborDataSource()
        dataSource = RoomNeighborDataSource(application)
        observe.value = getNeighbours()
    }

    fun setSource(source : Boolean){
        dataSource = if(source)
            RoomNeighborDataSource(application)
         else
            InMemoryNeighborDataSource()
        observe.value = getNeighbours()
    }

    fun deleteNeighbours(neighbor : Neighbor){
        executor.execute {
            dataSource.deleteNeighbour(neighbor);
        }
    }

    fun createNeighbour(neighbor: NeighborDTO){
        executor.execute {
            val id = (getNeighbours().value?.last()?.id ?: -1) + 1
            val neigh = Neighbor(id, neighbor.name, neighbor.avatarUrl, neighbor.address, neighbor.phoneNumber, neighbor.aboutMe, false, neighbor.webSite)
            dataSource.createNeighbour(neigh);
        }
    }

    fun updateNeighbor(neighbor: NeighborDTO){
        executor.execute {
            val neigh = neighbor.id?.let { Neighbor(it, neighbor.name, neighbor.avatarUrl, neighbor.address, neighbor.phoneNumber, neighbor.aboutMe, false, neighbor.webSite) }
            if (neigh != null) {
                dataSource.updateDataNeighbour(neigh)
            }
        }
    }

    fun favoriteStatusNeighbor(neighbor: Neighbor){
        executor.execute{
            if(neighbor.favorite){
                neighbor.favorite = false
                dataSource.updateFavoriteStatus(neighbor)
            }
            else{
                neighbor.favorite = true
                dataSource.updateFavoriteStatus(neighbor)
            }

        }
    }

    companion object {
        private var instance: NeighborRepository? = null

        // On crée un méthode qui retourne l'instance courante du repository si elle existe ou en crée une nouvelle sinon
        fun getInstance(application: Application): NeighborRepository {
            if (instance == null) {
                instance = NeighborRepository(application)
            }
            return instance!!
        }

    }
}