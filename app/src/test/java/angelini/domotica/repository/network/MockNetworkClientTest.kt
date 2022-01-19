package angelini.domotica.repository.network

import angelini.domotica.repository.datatypes.Device
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.Assert.*
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Classe testing mockup di rete
 *
 * La classe mockup di rete è necessaria per testare il repository, non è possibile testare
 * il repository con la vera classe di rete perché non è possibile abilitare il relativo
 * servizio registrato nel Manifest durante gli InstrumentedTest
 */
class MockNetworkClientTest {
    lateinit var network:MockNetworkClient

    @Before
    fun setUp() {
        network= MockNetworkClient()
    }

    @After
    fun tearDown() {
        if(network.isConnected())
            network.disconnect()
    }

    /**
     * Test connessione con successo classe mocked con credenziali corrette
     */
    @Test
    fun successfulConnect() {
        var triggerConnectionSuccess=false
        var triggerConnectionFailure=false
        var triggerDisconnectionSuccess=false
        var triggerDisconnectionFailure=false
        network.onConnectionSuccess={triggerConnectionSuccess=true}
        network.onConnectionFailure={triggerConnectionFailure=true}
        network.onDisconnectSuccess={triggerDisconnectionSuccess=true}
        network.onDisconnectFailure={triggerDisconnectionFailure=true}

        assertFalse(network.isConnected())
        //connection test
        network.connect(MOCKED_MQTT_USERNAME, MOCKED_MQTT_PWD)
        assertTrue(triggerConnectionSuccess)
        assertFalse(triggerConnectionFailure)
        assertTrue(network.isConnected())

        //disconnection test
        network.disconnect()
        assertTrue(triggerDisconnectionSuccess)
        assertFalse(triggerDisconnectionFailure)
        assertFalse(network.isConnected())
    }

    /**
     * Tentativo di connessione con le credenziali sbagliate
     */
    @Test
    fun wrongCredentialConnect() {
        var triggerConnectionSuccess=false
        var triggerConnectionFailure=false
        network.onConnectionSuccess={triggerConnectionSuccess=true}
        network.onConnectionFailure={triggerConnectionFailure=true}

        assertFalse(network.isConnected())
        //connection test
        network.connect("WrongUser", "WrongPassword")
        assertFalse(triggerConnectionSuccess)
        assertTrue(triggerConnectionFailure)
        assertFalse(network.isConnected())
    }

    /**
     * Ottiene la lista dei dispositivi registrati
     *
     * La classe di rete mocked contiene al suo interno un insieme di
     * Device alla sua creazione
     */
    @Test
    fun getDeviceList(){
        val parser=Parser(MOCKED_MQTT_USERNAME)
        var triggerPublishSuccess=false
        var list: List<Device> = listOf()

        network.onPublishSuccess={triggerPublishSuccess=true}
        network.onMessageArrived={_, message ->
                list=parser.decode(message)
           }

        network.connect(MOCKED_MQTT_USERNAME, MOCKED_MQTT_PWD)
        network.publish(parser.requestAllFeedsData(),"")

        assertTrue(triggerPublishSuccess)
        assertNotEquals(0,list.size)
    }

}