package angelini.domotica.repository.network

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import angelini.domotica.MQTT_PWD
import angelini.domotica.MQTT_USERNAME
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.coroutines.suspendCoroutine

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
     * Verifica una connessione reale
     */
    @ExperimentalCoroutinesApi
    @Test
    fun successfulConnect() {
        runTest {
            val connectionResult: Boolean = suspendCoroutine { cont ->
                network.onConnectionSuccess = { cont.resumeWith(Result.success(true)) }
                network.onConnectionFailure = { cont.resumeWith(Result.success(false)) }
                network.connect(MQTT_USERNAME, MQTT_PWD)
            }
            assertEquals(true, connectionResult)
        }
    }
}