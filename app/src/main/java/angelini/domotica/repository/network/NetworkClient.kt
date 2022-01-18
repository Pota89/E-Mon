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

    /**
     * Connetti al server MQTT
     *
     * @property username nome utente
     * @property password password dell'utente
     */
    override fun connect(username:String, password:String){
        val options = MqttConnectOptions()
        options.userName = username
        options.password = password.toCharArray()
        mqttClient.setCallback(clientCallbacks)

        mqttClient.connect(options,null, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                Log.i("EMon - NetworkClient","Connection success")
                onConnectionSuccess()
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable) {
                Log.i("EMon - NetworkClient", "Connection failure: $exception")
                onConnectionFailure()
            }
        })
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
        mqttClient.subscribe(topic, 1, null, object : IMqttActionListener{
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                Log.i("EMon - NetworkClient", "Subscribed to topic")
                onSubscribeSuccess()
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable) {
                Log.i("EMon - NetworkClient", "Failed to subscribe topic: $exception")
                onSubscribeFailure()
            }
        })
    }

    /**
     * Rimozione della sottoscrizione a un feed
     *
     * @property topic nome del feed
     */
    override fun unsubscribe(topic:String) {
        mqttClient.unsubscribe(topic, null, object : IMqttActionListener{
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                Log.i("EMon - NetworkClient", "Unsubscribed to topic")
                onUnsubscribeSuccess()
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable) {
                Log.i("EMon - NetworkClient", "Failed to unsubscribe topic: $exception")
                onUnsubscribeFailure()
            }
        })
    }

    /**
     * Pubblica un messaggio sul feed
     *
     * @property topic nome del feed
     * @property msg messaggio pubblicato nel feed
     */
    override fun publish(topic:      String,
                msg:        String) {
        val message = MqttMessage()
        message.payload = msg.toByteArray()
        message.qos = 1
        message.isRetained = true
        mqttClient.publish(topic, message, null, object : IMqttActionListener{
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                Log.i("EMon - NetworkClient", "Message published to topic")
                onPublishSuccess()
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable) {
                Log.i("EMon - NetworkClient", "Failed to publish message to topic: $exception")
                onPublishFailure()
            }
        })
    }

    /**
     * Disconetti dal server MQTT attualmente collegato
     */
    override fun disconnect() {
        mqttClient.disconnect(null, object : IMqttActionListener{
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                Log.i("EMon - NetworkClient", "Disconnected")
                onDisconnectSuccess()
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable) {
                Log.i("EMon - NetworkClient", "Failed to disconnect: $exception")
                onDisconnectFailure()
            }
        })
    }
}