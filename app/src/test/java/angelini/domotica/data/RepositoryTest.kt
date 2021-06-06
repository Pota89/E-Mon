package angelini.domotica.data

import junit.framework.Assert.assertEquals
import org.junit.Test

class RepositoryTest() {
    @Test
    fun roomListTest() {
        val repo=Repository()
        val roomList= repo.devicesList
        val roomsNumber= roomList.size
        assertEquals(0, roomsNumber)
    }
}