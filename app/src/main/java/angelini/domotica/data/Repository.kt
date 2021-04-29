package angelini.domotica.data

import android.content.Context
import angelini.domotica.data.db.CacheDatabase
import angelini.domotica.data.db.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
        //load database on not UI thread
        GlobalScope.launch {
            val userDao = db.userDao()
            userDao.deleteAll()
            userDao.insert(Room(RoomType.LOUNGE), Room(RoomType.BATHROOM), Room(RoomType.KITCHEN),Room(RoomType.GARAGE))
            _roomList.addAll(userDao.getAll())
            }
    }
    fun connect() {

        //networkClient.connect()
    }

    fun disconnect() {
        //networkClient.disconnect()
    }
}