package com.example.neighborss.di

import android.app.Application
import com.example.neighborss.repositories.NeighborRepository

object DI {

    lateinit var repository: NeighborRepository

    fun inject(application: Application) {
        repository = NeighborRepository.getInstance(application)
    }
}