package angelini.domotica.data

import angelini.domotica.data.db.Device
import angelini.domotica.data.db.Room
import java.util.*

class Parser(username:String) {

    private var rootname=username

    fun subscribeAllFeeds():String{
        return "${rootname}/groups/home"
    }
    fun requestAllFeeds():String{
        return "${rootname}/groups/home/get"
    }

    fun decode(topic:String, message:String):List<Device>{
        val returnList= mutableListOf<Device>()
        val itemList=message.lines()//split message by Carriage Return
        for (item in itemList){
            if(item!="") {
                val itemElements = item.removePrefix("home.")
                    .split(",", "-") //split room, roomNumber, device, numberDevice, (value)

                val roomType = try {
                    RoomType.valueOf(itemElements[0].toUpperCase(Locale.ROOT))//matching room type from string to enum
                } catch(e: IllegalArgumentException) {
                    RoomType.UNKNOWN
                }

                val roomNumber = itemElements[1].toInt()

                val deviceType = try {
                    DeviceType.valueOf(itemElements[2].toUpperCase(Locale.ROOT))//matching device type from string to enum
                } catch(e: IllegalArgumentException) {
                    DeviceType.UNKNOWN
                }

                val deviceNumber=itemElements[3].toInt()

                val deviceValue=itemElements[4].toInt()

                returnList.add(Device(Room(roomType,roomNumber),deviceType,deviceNumber,deviceValue))
            }
        }
        return returnList
    }
}