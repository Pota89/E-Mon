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
            val elements=item.removePrefix("home.").split(",")
            when (elements[0]) {
                "bathroom" -> returnList.add(Room(RoomType.BATHROOM))
                "bedroom" -> returnList.add(Room(RoomType.BEDROOM))
                "kitchen" -> returnList.add(Room(RoomType.KITCHEN))
                "lounge" -> returnList.add(Room(RoomType.LOUNGE))
                "study" -> returnList.add(Room(RoomType.STUDY))
                "garage" -> returnList.add(Room(RoomType.GARAGE))
                "hall" -> returnList.add(Room(RoomType.HALL))
                "dining" -> returnList.add(Room(RoomType.DINING))
                "hallway" -> returnList.add(Room(RoomType.HALLWAY))
                "" -> {}//to ignore the last empty item of itemList
                else -> returnList.add(Room())
            }
        }
        return returnList
    }
}