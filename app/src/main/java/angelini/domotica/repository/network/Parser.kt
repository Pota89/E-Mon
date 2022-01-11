package angelini.domotica.repository.network

import angelini.domotica.repository.datatypes.Device
import angelini.domotica.repository.datatypes.DeviceType
import angelini.domotica.repository.datatypes.Room
import angelini.domotica.repository.datatypes.RoomType
import java.lang.NumberFormatException

class Parser(username: String) {

    var rootname = username

    fun subscribeAllFeeds(): String {
        return "${rootname}/groups/home"
    }

    fun requestAllFeedsData(): String {
        return "${rootname}/groups/home/get"
    }

    fun decode(message: String): List<Device> {
        val returnList = mutableListOf<Device>()
        val deviceStringList = message.lines()//split messages by Carriage Return
        for (deviceString in deviceStringList) {
            val noPrefixString=deviceString.removePrefix("home.")
            val deviceElementsAndValuePair=noPrefixString.split(",")//split device info and value
            val deviceElements = deviceElementsAndValuePair[0].split("-") //split room, roomNumber, device, numberDevice

            if(deviceElements.size==4)//if we have a different size, we have a bad formed string
            {
                val roomType = try {
                    RoomType.valueOf(deviceElements[0].uppercase())//matching room type from string to enum
                } catch (e: IllegalArgumentException) {
                    RoomType.UNKNOWN
                }

                val roomNumber = try {
                    deviceElements[1].toInt()
                } catch (e: NumberFormatException) {
                    0
                }

                val deviceType = try {
                    DeviceType.valueOf(deviceElements[2].uppercase())//matching device type from string to enum
                } catch (e: IllegalArgumentException) {
                    DeviceType.UNKNOWN
                }

                val deviceNumber = try {
                    deviceElements[3].toInt()
                } catch (e: NumberFormatException) {
                    0
                }

                val deviceValue = try {
                    deviceElementsAndValuePair[1].toInt()
                } catch (e: NumberFormatException) {
                    0
                }

                returnList.add(
                    Device(
                        Room(roomType, roomNumber),
                        deviceType,
                        deviceNumber,
                        deviceValue
                    )
                )
            }

        }
        return returnList
    }
}