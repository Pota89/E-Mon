package angelini.domotica.repository.network

import angelini.domotica.repository.datatypes.Device
import angelini.domotica.repository.datatypes.DeviceType
import angelini.domotica.repository.datatypes.Room
import angelini.domotica.repository.datatypes.RoomType
import org.junit.After
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

/**
 * Test dedicati alla verifica della classe Parser
 */
class ParserTest {

    private lateinit var parser:Parser

    @Before
    fun setUp() {
        parser=Parser("testparser")
    }

    @After
    fun tearDown() {
    }

    /**
     * Verifica se la stringa di sottoscrizione a tutti i feeds si formi correttamente anche cambiando l'utente
     */
    @Test
    fun subscribeAllFeeds() {
        val parameterName=parser.subscribeAllFeeds()
        assertEquals("testparser/groups/home",parameterName)

        parser.rootname="changedname"
        val changedName=parser.subscribeAllFeeds()
        assertEquals("changedname/groups/home",changedName)
    }

    /**
     * Verifica se la stringa di richiesta dei feeds si formi correttamente anche cambiando l'utente
     */
    @Test
    fun requestAllFeedsData() {
        val parameterName=parser.requestAllFeedsData()
        assertEquals("testparser/groups/home/get",parameterName)

        parser.rootname="changedname"
        val changedName=parser.requestAllFeedsData()
        assertEquals("changedname/groups/home/get",changedName)
    }

    /**
     * Generico test di decodifica stringa IO Adafruit senza valore assegnato
     */
    @Test
    fun genericWellFormedDecode() {
        val decodeString="home.bedroom-1-temperature-1,\"\"\n" +
                "home.hallway-0-lamp-1,\"\"\n" +
                "home.lounge-1-lamp-1,\"\"\n" +
                "home.lounge-2-lamp-0,\"\""
        val bedroomOneTemperatureOne= Device(Room(RoomType.BEDROOM,1),DeviceType.TEMPERATURE,1)
        val hallwayLampOne= Device(Room(RoomType.HALLWAY,0),DeviceType.LAMP,1)
        val loungeOneLampOne= Device(Room(RoomType.LOUNGE,1),DeviceType.LAMP,1)
        val loungeTwoLamp= Device(Room(RoomType.LOUNGE,2),DeviceType.LAMP,0)

        val decodeListResult=parser.decode(decodeString)

        assertEquals(4,decodeListResult.count())
        assertTrue(decodeListResult.contains(bedroomOneTemperatureOne))
        assertTrue(decodeListResult.contains(hallwayLampOne))
        assertTrue(decodeListResult.contains(loungeOneLampOne))
        assertTrue(decodeListResult.contains(loungeTwoLamp))

    }

    /**
     * Test di decodifica stringa IO Adafruit con valori assegnati
     */
    @Test
    fun assignedWellFormedDecode() {
        val decodeString="home.hallway-1-lamp-1,1\n" +
                "home.bedroom-1-temperature-1,20\n" +
                "home.kitchen-1-temperature-1,20"
        val hallway= Device(Room(RoomType.HALLWAY,1),DeviceType.LAMP,1,1)
        val bedroom= Device(Room(RoomType.BEDROOM,1),DeviceType.TEMPERATURE,1,40)
        val kitchen= Device(Room(RoomType.KITCHEN,1),DeviceType.TEMPERATURE,1,20)


        val decodeListResult=parser.decode(decodeString)

        assertEquals(3,decodeListResult.count())
        assertTrue(decodeListResult.contains(hallway))
        assertFalse(decodeListResult.contains(bedroom))//assigned 40 and we expect 20
        assertTrue(decodeListResult.contains(kitchen))
    }

    /**
     * Test di decodifica stringa IO Adafruit con valori di temperatura negativi
     */
    @Test
    fun negativeTemperatureDecode() {
        val decodeString="home.bedroom-1-temperature-1,-10\n" +
                "home.hallway-1-lamp-1,1\n" +
                "home.kitchen-1-temperature-1,-5"
        val bedroom= Device(Room(RoomType.BEDROOM,1),DeviceType.TEMPERATURE,1,-10)
        val hallway= Device(Room(RoomType.HALLWAY,1),DeviceType.LAMP,1,1)
        val kitchen= Device(Room(RoomType.KITCHEN,1),DeviceType.TEMPERATURE,1,-5)


        val decodeListResult=parser.decode(decodeString)

        assertEquals(3,decodeListResult.count())
        assertTrue(decodeListResult.contains(bedroom))
        assertTrue(decodeListResult.contains(hallway))
        assertTrue(decodeListResult.contains(kitchen))
    }

    /**
     * Test di decodifica stringa con testo casuale
     */
    @Test
    fun badFormedDecode() {
        val decodeString="home.hallway-1-lamp-1,1\n" +
                "im a dirty string\n" +
                "home.kitchen-1-temperature-1,20"
        val hallway= Device(Room(RoomType.HALLWAY,1),DeviceType.LAMP,1,1)
        val kitchen= Device(Room(RoomType.KITCHEN,1),DeviceType.TEMPERATURE,1,20)

        val decodeListResult=parser.decode(decodeString)

        assertEquals(2,decodeListResult.count())
        assertTrue(decodeListResult.contains(hallway))
        assertTrue(decodeListResult.contains(kitchen))
    }

    /**
     * Verifica la corretta formazione della stringa topic MQTT a partire da un Device
     */
    @Test
    fun encodeTopic(){
        val testDevice=Device(Room(RoomType.BEDROOM,1),DeviceType.TEMPERATURE,1,20)
        val expectedReturnString="testparser/feeds/home.bedroom-1-temperature-1"

        val testReturnString=parser.encodeTopic(testDevice)
        assertEquals(expectedReturnString,testReturnString)
    }

    /**
     * Test di decodifica stringa di ritorno a seguito di una publish
     */
    @Test
    fun publishDecode() {
        val decodeString="{\"feeds\":{\"bedroom-1-temperature-1\":\"30\"}}"
        val bedroomCompare= Device(Room(RoomType.BEDROOM,1),DeviceType.TEMPERATURE,1,30)
        val decodeListResult=parser.decode(decodeString)

        assertEquals(1,decodeListResult.count())
        assertTrue(decodeListResult.contains(bedroomCompare))
    }
}