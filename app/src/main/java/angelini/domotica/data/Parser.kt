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
                "minni" -> returnList.add(Room(RoomType.KITCHEN))
                "topolino" -> returnList.add(Room(RoomType.BATHROOM))
                "" -> {}//to ignore the last empty item of itemList
                else -> returnList.add(Room())
            }
        }
        return returnList
    }
}