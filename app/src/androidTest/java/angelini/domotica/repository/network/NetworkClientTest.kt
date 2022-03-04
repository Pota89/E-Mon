package angelini.domotica.repository.network

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import angelini.domotica.repository.MQTT_PWD
import angelini.domotica.repository.MQTT_USERNAME
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Classe per la verifica della reale connessione di rete, richiede un vero server MQTT
 * con dei Device già definiti per funzionare
 */
@RunWith(AndroidJUnit4::class)
class NetworkClientTest {
    private lateinit var network: NetworkClient

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        network= NetworkClient(context)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        if(network.isConnected())
            runTest {
                network.disconnect()
            }
    }

    /**
     * Verifica una connessione reale con credenziali corrette
     */
    @ExperimentalCoroutinesApi
    @Test
    fun successfulConnect() {
        var connectFlag=false
        network.onConnectionSuccess={connectFlag=true}
        network
        runTest {
            network.connect(MQTT_USERNAME, MQTT_PWD)
        }
        assertEquals(true,connectFlag)
    }

    /**
     * Verifica una connessione reale con credenziali errate
     */
    @ExperimentalCoroutinesApi
    @Test
    fun unsuccessfulConnect() {
        var connectFlag=false
        network.onConnectionSuccess={connectFlag=true}
        network
        runTest {
            network.connect("utentefalso","passwordsconosciuta")
        }
        assertEquals(false,connectFlag)
    }
}