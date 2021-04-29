package angelini.domotica.data

import android.content.Context
import angelini.domotica.data.db.CacheDatabase
import angelini.domotica.data.db.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Repository(context:Context){

    //private val networkClient:NetworkClient = NetworkClient(context)

    val db = androidx.room.Room.databaseBuilder(
        context,
        CacheDatabase::class.java, "cache"
    ).build()

    private val _roomList = mutableListOf<Room>()
    val roomList: List<Room>
        get() = _roomList

    //TODO si può fare meglio?
    init {
        runBlocking {
            //load database on IO thread pool (avoid main/UI thread)
            launch(Dispatchers.IO) {
                val userDao = db.userDao()
                userDao.deleteAll()
                userDao.insert(Room(RoomType.LOUNGE), Room(RoomType.BATHROOM), Room(RoomType.KITCHEN), Room(RoomType.GARAGE))
                _roomList.addAll(userDao.getAll())
            }
        }
    }
    fun connect() {

        //networkClient.connect()
    }

    fun disconnect() {
        //networkClient.disconnect()
    }
}