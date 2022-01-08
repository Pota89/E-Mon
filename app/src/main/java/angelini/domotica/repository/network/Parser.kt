package angelini.domotica.repository.network

import androidx.core.text.isDigitsOnly
import angelini.domotica.repository.datatypes.Device
import angelini.domotica.repository.datatypes.DeviceType
import angelini.domotica.repository.datatypes.Room
import angelini.domotica.repository.datatypes.RoomType

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
                    RoomType.valueOf(itemElements[0].uppercase())//matching room type from string to enum
                } catch(e: IllegalArgumentException) {
                    RoomType.UNKNOWN
                }

                val roomNumber = itemElements[1].toInt()

                val deviceType = try {
                    DeviceType.valueOf(itemElements[2].uppercase())//matching device type from string to enum
                } catch(e: IllegalArgumentException) {
                    DeviceType.UNKNOWN
                }

                val deviceNumber=itemElements[3].toInt()

                var deviceValue=0
                if (itemElements[4].isDigitsOnly())//check if is a valid value
                    deviceValue=itemElements[4].toInt()

                returnList.add(Device(Room(roomType,roomNumber),deviceType,deviceNumber,deviceValue))
            }
        }
        return returnList
    }
}