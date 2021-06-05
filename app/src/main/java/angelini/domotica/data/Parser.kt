package angelini.domotica.data

import angelini.domotica.data.db.Room

class Parser(username:String) {

    private var rootname=username

    fun subscribeAllFeeds():String{
        return "${rootname}/groups/home"
    }
    fun requestAllFeeds():String{
        return "${rootname}/groups/home/get"
    }

    fun decode(topic:String, message:String):List<Room>{
        val returnList= mutableListOf<Room>()
        val itemList=message.lines()//split message by Carriage Return
        for (item in itemList){
            if(item!="") {
                val itemElements = item.removePrefix("home.")
                    .split(",", "-") //split room, roomNumber, (device), (numberDevice), value
                val roomType = itemElements[0]
                val roomNumber = itemElements[1].toInt()
                when (roomType) {
                    "bathroom" -> returnList.add(Room(RoomType.BATHROOM, roomNumber))
                    "bedroom" -> returnList.add(Room(RoomType.BEDROOM, roomNumber))
                    "kitchen" -> returnList.add(Room(RoomType.KITCHEN, roomNumber))
                    "lounge" -> returnList.add(Room(RoomType.LOUNGE, roomNumber))
                    "study" -> returnList.add(Room(RoomType.STUDY, roomNumber))
                    "garage" -> returnList.add(Room(RoomType.GARAGE, roomNumber))
                    "hall" -> returnList.add(Room(RoomType.HALL, roomNumber))
                    "dining" -> returnList.add(Room(RoomType.DINING, roomNumber))
                    "hallway" -> returnList.add(Room(RoomType.HALLWAY, roomNumber))
                    else -> returnList.add(Room())
                }
            }
        }
        return returnList
    }
}