package angelini.domotica.data

import angelini.domotica.data.db.Device

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
                    .split(",", "-") //split room, roomNumber, (device), (numberDevice), value
                val roomTypeString = itemElements[0]
                var roomType=RoomType.UNKNOWN
                val roomNumber = itemElements[1].toInt()
                when (roomTypeString) {
                    "bathroom" -> roomType=RoomType.BATHROOM
                    "bedroom" -> roomType=RoomType.BEDROOM
                    "kitchen" -> roomType=RoomType.KITCHEN
                    "lounge" -> roomType=RoomType.LOUNGE
                    "study" -> roomType=RoomType.STUDY
                    "garage" -> roomType=RoomType.GARAGE
                    "hall" -> roomType=RoomType.HALL
                    "dining" -> roomType=RoomType.DINING
                    "hallway" -> roomType=RoomType.HALLWAY
                }

                returnList.add(Device(roomType,roomNumber,DeviceType.UNKNOWN,0))//TODO terminate device setup
            }
        }
        return returnList
    }
}