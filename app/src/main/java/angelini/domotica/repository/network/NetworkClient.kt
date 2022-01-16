package angelini.domotica.repository.network

import android.content.Context
import android.util.Log
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import kotlin.coroutines.suspendCoroutine

const val MQTT_SERVER_URI       = "tcp://io.adafruit.com:1883"
const val MQTT_CLIENT_ID        = ""

/**
 * Classe per la comunicazione con il server MQTT di IO Adafruit
 *
 * Incapsula le funzioni di rete permettendo la comunicazione con il server senza preoccuparsi
 * dei dettagli implementativi fornendo direttamente metodi come Publish o Subscribe.
 * La comunicazione è asincrona e il risultato delle operazioni viene interpretato tramite
 * callbacks assegnabili dall'esterno della classe.
 *
 * @property context contesto Android, necessario per gestire le operazioni asincrone
 */
class NetworkClient(context: Context) {
    private var mqttClient = MqttAndroidClient(context, MQTT_SERVER_URI, MQTT_CLIENT_ID)

    var onConnectionSuccess: () -> Unit = {}
    var onConnectionFailure: () -> Unit = {}
    var onSubscribeSuccess: () -> Unit = {}
    var onSubscribeFailure: () -> Unit = {}
    var onUnsubscribeSuccess: () -> Unit = {}
    var onUnsubscribeFailure: () -> Unit = {}
    var onPublishSuccess: () -> Unit = {}
    var onPublishFailure: () -> Unit = {}
    var onDisconnectSuccess: () -> Unit = {}
    var onDisconnectFailure: () -> Unit = {}
    var onMessageArrived: (topic:String, message:String) -> Unit = { _, _ -> }
    var onConnectionLost: () -> Unit = {}
    var onDeliveryComplete: () -> Unit = {}


    private val subscribeCallbacks = object : IMqttActionListener {
        override fun onSuccess(asyncActionToken: IMqttToken?) {
            Log.i("EMon - NetworkClient", "Subscribed to topic")
            onSubscribeSuccess()
        }

        override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
            Log.i("EMon - NetworkClient", "Failed to subscribe topic: ${exception.toString()}")
            onSubscribeFailure()
        }
    }

    private val unsubscribeCallbacks = object : IMqttActionListener {
        override fun onSuccess(asyncActionToken: IMqttToken?) {
            Log.i("EMon - NetworkClient", "Unsubscribed to topic")
            onUnsubscribeSuccess()
        }

        override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
            Log.i("EMon - NetworkClient", "Failed to unsubscribe topic: ${exception.toString()}")
            onUnsubscribeFailure()
        }
    }

    private val publishCallbacks = object : IMqttActionListener {
        override fun onSuccess(asyncActionToken: IMqttToken?) {
            Log.i("EMon - NetworkClient", "Message published to topic")
            onPublishSuccess()
        }

        override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
            Log.i("EMon - NetworkClient", "Failed to publish message to topic: ${exception.toString()}")
            onPublishFailure()
        }
    }
    private val disconnectCallbacks = object : IMqttActionListener {
        override fun onSuccess(asyncActionToken: IMqttToken?) {
            Log.i("EMon - NetworkClient", "Disconnected")
            onDisconnectSuccess()
        }

        override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
            Log.i("EMon - NetworkClient", "Failed to disconnect: ${exception.toString()}")
            onDisconnectFailure()
        }
    }

    private val clientCallbacks = object : MqttCallback {
        override fun messageArrived(topic: String?, message: MqttMessage?) {
            Log.i("EMon - NetworkClient", "Receive message: ${message.toString()} from topic: $topic")
            onMessageArrived(topic ?: "",message.toString())
        }

        override fun connectionLost(cause: Throwable?) {
            Log.i("EMon - NetworkClient", "Connection lost ${cause.toString()}")
            onConnectionLost()
        }

        override fun deliveryComplete(token: IMqttDeliveryToken?) {
            Log.i("EMon - NetworkClient", "Delivery completed")
            onDeliveryComplete()
        }
    }
    //end callbacks section

    /**
     * Connetti al server MQTT
     *
     * @property username nome utente
     * @property password password dell'utente
     */
    suspend fun connect(username:String,password:String){
        val options = MqttConnectOptions()
        options.userName = username
        options.password = password.toCharArray()
        mqttClient.setCallback(clientCallbacks)

        return suspendCoroutine { cont -> mqttClient.connect(options,null, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                Log.i("EMon - NetworkClient","Connection success")
                onConnectionSuccess()
                cont.resumeWith(Result.success(Unit))
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable) {
                Log.i("EMon - NetworkClient", "Connection failure: $exception")
                onConnectionFailure()
                cont.resumeWith(Result.failure(exception))
            }
        }
        )}
    }

    /**
     * Verifica se si è connessi al server MQTT
     */
    fun isConnected(): Boolean {
        return mqttClient.isConnected
    }

    /**
     * Sottoscrizione a un feed
     *
     * @property topic nome del feed
     */
    fun subscribe(topic:String) {
        try {
            mqttClient.subscribe(topic, 1, null, subscribeCallbacks)
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    /**
     * Rimozione della sottoscrizione a un feed
     *
     * @property topic nome del feed
     */
    fun unsubscribe(topic:String) {
        try {
            mqttClient.unsubscribe(topic, null, unsubscribeCallbacks)
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    /**
     * Pubblica un messaggio sul feed
     *
     * @property topic nome del feed
     * @property msg messaggio pubblicato nel feed
     */
    fun publish(topic:      String,
                msg:        String) {
        try {
            val message = MqttMessage()
            message.payload = msg.toByteArray()
            message.qos = 1
            message.isRetained = true
            mqttClient.publish(topic, message, null, publishCallbacks)
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    /**
     * Disconetti dal server MQTT attualmente collegato
     */
    fun disconnect() {
        try {
            mqttClient.disconnect(null,disconnectCallbacks)
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

}