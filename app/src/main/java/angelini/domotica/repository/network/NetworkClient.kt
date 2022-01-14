package angelini.domotica.repository.network

import android.content.Context
import android.util.Log
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

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
class NetworkClient(context: Context) : INetworkClient {
    private var mqttClient = MqttAndroidClient(context, MQTT_SERVER_URI, MQTT_CLIENT_ID)

    override var onConnectionSuccess: () -> Unit = {}
    override var onConnectionFailure: () -> Unit = {}
    override var onSubscribeSuccess: () -> Unit = {}
    override var onSubscribeFailure: () -> Unit = {}
    override var onUnsubscribeSuccess: () -> Unit = {}
    override var onUnsubscribeFailure: () -> Unit = {}
    override var onPublishSuccess: () -> Unit = {}
    override var onPublishFailure: () -> Unit = {}
    override var onDisconnectSuccess: () -> Unit = {}
    override var onDisconnectFailure: () -> Unit = {}
    override var onMessageArrived: (topic:String, message:String) -> Unit = { _, _ -> }
    override var onConnectionLost: () -> Unit = {}
    override var onDeliveryComplete: () -> Unit = {}

    //callbacks section
    private val connectCallbacks = object : IMqttActionListener {
        override fun onSuccess(asyncActionToken: IMqttToken?) {
            Log.i("EMon - NetworkClient","Connection success")
            onConnectionSuccess()
        }

        override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
            Log.i("EMon - NetworkClient", "Connection failure: ${exception.toString()}")
            onConnectionFailure()
        }
    }

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
    override fun connect(username:String,password:String):Boolean{
        val options = MqttConnectOptions()
        options.userName = username
        options.password = password.toCharArray()
        mqttClient.setCallback(clientCallbacks)

        try {
            mqttClient.connect(options,null, connectCallbacks)
        } catch (e: MqttException) {
            e.printStackTrace()
            return false
        }
        return true
    }

    /**
     * Verifica se si è connessi al server MQTT
     */
    override fun isConnected(): Boolean {
        return mqttClient.isConnected
    }

    /**
     * Sottoscrizione a un feed
     *
     * @property topic nome del feed
     */
    override fun subscribe(topic:String) {
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
    override fun unsubscribe(topic:String) {
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
    override fun publish(topic:      String,
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
    override fun disconnect() {
        try {
            mqttClient.disconnect(null,disconnectCallbacks)
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

}