package angelini.domotica.manual

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import angelini.domotica.data.Repository
import angelini.domotica.data.db.Room
import angelini.domotica.data.RoomType

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val testList=Repository(application.applicationContext).roomList

    private val _rooms = MutableLiveData<List<Room>>()
    val rooms: LiveData<List<Room>>
        get() = _rooms

    init {
        _rooms.value=testList
    }
}


/*
package com.lomza.moviesroom.movie

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.lomza.moviesroom.db.*

/**
 * @author Antonina
 */
class MoviesViewModel(application: Application) : AndroidViewModel(application) {

    private val movieDao: MovieDao = MoviesDatabase.getDatabase(application).movieDao()
    private val directorDao: DirectorDao = MoviesDatabase.getDatabase(application).directorDao()

    val moviesList: LiveData<List<Movie>>
    val directorsList: LiveData<List<Director>>

    init {
        moviesList = movieDao.allMovies
        directorsList = directorDao.allDirectors
    }

    suspend fun insert(vararg movies: Movie) {
        movieDao.insert(*movies)
    }

    suspend fun update(movie: Movie) {
        movieDao.update(movie)
    }

    suspend fun deleteAll() {
        movieDao.deleteAll()
    }
}*/