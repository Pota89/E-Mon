package angelini.domotica.repository

import angelini.domotica.repository.db.CacheDatabase
import angelini.domotica.repository.datatypes.Device
import angelini.domotica.repository.datatypes.Room
import angelini.domotica.repository.datatypes.RoomType
import angelini.domotica.repository.network.INetworkClient
import angelini.domotica.repository.network.Parser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

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
        /*
        CoroutineScope(Dispatchers.IO).launch {
            val userDao = db.deviceDao()
            userDao.deleteAll()
        }
        networkClient.onConnectionSuccess={
            CoroutineScope(Dispatchers.IO).launch {
                networkClient.subscribe(parser.subscribeAllFeeds())
                networkClient.publish(parser.requestAllFeedsData(),"")
            }
        }

        networkClient.onConnectionFailure={
        }

        networkClient.onMessageArrived={ topic, message ->
            val list=parser.decode(message)
            CoroutineScope(Dispatchers.IO).launch {
                val userDao = db.deviceDao()
                for(element in list){
                    userDao.insert(element)
                }
            }
        }
        */
    }

    suspend fun connect(username:String,password:String) {
        if(networkClient.isConnected())
            return


        parser.rootname=username
        networkClient.connect(username,password)

    }

    fun disconnect() {
        CoroutineScope(Dispatchers.IO).launch {
            networkClient.disconnect()
        }
    }

    fun isConnected():Boolean{
        return networkClient.isConnected()
    }
}