package angelini.domotica.repository

import angelini.domotica.repository.db.CacheDatabase
import angelini.domotica.repository.datatypes.Device
import angelini.domotica.repository.datatypes.Room
import angelini.domotica.repository.datatypes.RoomType
import angelini.domotica.repository.network.INetworkClient
import angelini.domotica.repository.network.Parser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Repository(database:CacheDatabase, network: INetworkClient) {

    private val db=database
    private val networkClient=network
    private val parser= Parser("")

    val devicesList: Flow<List<Device>> =db.deviceDao().getAllDevices()
    val roomsList: Flow<List<Room>> =db.deviceDao().getRoomList()

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
            //Log.i("EMon - Repository", "Connection success receipt")
            networkClient.subscribe(parser.subscribeAllFeeds())
            networkClient.publish(parser.requestAllFeedsData(),"")
        }

        networkClient.onConnectionFailure={
            //Log.i("EMon - Repository", "Connection failure receipt")
        }

        networkClient.onMessageArrived={ topic, message ->
            //Log.i("EMon - Repository", "Topic $topic and message $message")
            val list=parser.decode(message)
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

    fun connect(username:String,password:String) {
        parser.rootname=username
        networkClient.connect(username,password)
    }

    fun disconnect() {
        networkClient.disconnect()
    }
}