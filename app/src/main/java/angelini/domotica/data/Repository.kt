package angelini.domotica.data

import android.content.Context
import android.util.Log
import angelini.domotica.data.db.CacheDatabase
import angelini.domotica.data.db.Device
import angelini.domotica.data.db.Room
import angelini.domotica.data.db.RoomType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Repository(context:Context) {

    private val networkClient= NetworkClient(context)
    private val parser=Parser("ExamToGo")

    private val db = androidx.room.Room.databaseBuilder(
        context,
        CacheDatabase::class.java, "cache"
    ).build()

    val devicesList: Flow<List<Device>> =db.deviceDao().getAll()
    val roomsList: Flow<List<Room>> =db.deviceDao().getAllRooms()

    fun getRoomDevices(roomType: RoomType, roomNumber: Int): Flow<List<Device>> {
        return db.deviceDao().getRoomDevices(roomType,roomNumber)
    }

    init {
        runBlocking {
            //load database on IO thread pool (avoid main/UI thread)
            launch(Dispatchers.IO) {
                val userDao = db.deviceDao()
                userDao.deleteAll()
            }
        }
        networkClient.onConnectionSuccess={
            Log.i("EMon - Repository", "Connection success receipt")
            networkClient.subscribe(parser.subscribeAllFeeds())
            networkClient.publish(parser.requestAllFeeds(),"")
        }

        networkClient.onConnectionFailure={
            Log.i("EMon - Repository", "Connection failure receipt")
        }

        networkClient.onMessageArrived={ topic, message ->
            Log.i("EMon - Repository", "Topic $topic and message $message")
            val list=parser.decode(topic,message)
            runBlocking {
                //load database on IO thread pool (avoid main/UI thread)
                launch(Dispatchers.IO) {
                    val userDao = db.deviceDao()
                    for(element in list){
                        userDao.insert(element)
                    }
                }
            }
        }

    }
    fun connect() {
        networkClient.connect()
    }

    fun disconnect() {
        networkClient.disconnect()
    }
}