package angelini.domotica.repository.network

import android.content.Context
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import angelini.domotica.repository.MQTT_PWD
import angelini.domotica.repository.MQTT_USERNAME
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

class NetworkClientTempTest {
    lateinit var network:NetworkClient

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        network= NetworkClient(context)
    }

    @After
    fun tearDown() {
        Log.i("Test","Teardown")
        if(network.isConnected())
            runBlocking {
                Log.i("Test","Teardown disconnect")
                network.disconnect()
            }
    }

    @Test
    fun successfulConnect() {
        val username=MQTT_USERNAME
        val parser=Parser(username)
        Log.i("Test","Pre connect")
        runBlocking { network.connect(username, MQTT_PWD) }
        Log.i("Test","Post connect")



        //-----------
        /*
        var connectionFlag=false
        var subscribeSuccess=false
        var subscribeFailure=false
        var publishSuccess=false
        var publishFailure=false
        val username=MQTT_USERNAME
        //val username="Pippo"
        val parser=Parser(username)

        network.onConnectionSuccess={connectionFlag=network.isConnected()}
        network.onSubscribeSuccess={ subscribeSuccess=true}
        network.onSubscribeFailure={ subscribeFailure=true}
        network.onPublishSuccess={ publishSuccess=true}
        network.onPublishFailure={ publishFailure=true}

        runBlocking { network.connect(username,MQTT_PWD) }
        //runBlocking { network.connect(username,"ASDASD") }
        assertEquals(true,connectionFlag)

        runBlocking {
            network.subscribe(parser.subscribeAllFeeds())
            network.publish(parser.requestAllFeedsData(),"")
        }
        assertEquals(true,subscribeSuccess)
        assertEquals(false,subscribeFailure)
        assertEquals(true,publishSuccess)
        assertEquals(false,publishFailure)*/

    }

    @Test
    fun isConnected() {
    }

    @Test
    fun subscribe() {
    }

    @Test
    fun unsubscribe() {
    }

    @Test
    fun publish() {
    }

    @Test
    fun disconnect() {
    }
}