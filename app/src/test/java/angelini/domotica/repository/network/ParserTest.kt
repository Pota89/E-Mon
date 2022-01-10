package angelini.domotica.repository.network

import angelini.domotica.repository.datatypes.Device
import angelini.domotica.repository.datatypes.DeviceType
import angelini.domotica.repository.datatypes.Room
import angelini.domotica.repository.datatypes.RoomType
import org.junit.After
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class ParserTest {

    private lateinit var parser:Parser

    private val bedroomOneTemperatureOne= Device(Room(RoomType.BEDROOM,1),DeviceType.TEMPERATURE,1)
    private val hallwayLampOne= Device(Room(RoomType.HALLWAY,0),DeviceType.LAMP,1)
    private val loungeOneLampOne= Device(Room(RoomType.LOUNGE,1),DeviceType.LAMP,1)
    private val loungeTwoLamp= Device(Room(RoomType.LOUNGE,2),DeviceType.LAMP,0)

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
     * Generico test di decodifica stringa IO Adafruit senza valore
     */
    @Test
    fun firstWellFormedDecode() {
        //val decodeString="""home.bedroom-1-temperature-1,""\nhome.hallway-0-lamp-1,""\nhome.lounge-1-lamp-1,""\nhome.lounge-2-lamp-0,"""""
        val decodeString="home.bedroom-1-temperature-1,\"\"\n" +
                "home.hallway-0-lamp-1,\"\"\n" +
                "home.lounge-1-lamp-1,\"\"\n" +
                "home.lounge-2-lamp-0,\"\""
        val decodeListResult=parser.decode(decodeString)

        assertEquals(4,decodeListResult.count())
        assertTrue(decodeListResult.contains(bedroomOneTemperatureOne))
        assertTrue(decodeListResult.contains(hallwayLampOne))
        assertTrue(decodeListResult.contains(loungeOneLampOne))
        assertTrue(decodeListResult.contains(loungeTwoLamp))

    }
}