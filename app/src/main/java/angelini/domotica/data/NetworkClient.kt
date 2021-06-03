package angelini.domotica.data

import android.content.Context
import android.util.Log
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

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

    //region Callbacks section
    private val connectCallbacks = object : IMqttActionListener {
        override fun onSuccess(asyncActionToken: IMqttToken?) {
            Log.i("NetworkClient","Connection success")
            onConnectionSuccess()
        }

        override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
            Log.i("NetworkClient", "Connection failure: ${exception.toString()}")
            onConnectionFailure()
        }
    }

    private val subscribeCallbacks = object : IMqttActionListener {
        override fun onSuccess(asyncActionToken: IMqttToken?) {
            Log.i("NetworkClient", "Subscribed to topic")
            onSubscribeSuccess()
        }

        override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
            Log.i("NetworkClient", "Failed to subscribe topic: ${exception.toString()}")
            onSubscribeFailure()
        }
    }

    private val unsubscribeCallbacks = object : IMqttActionListener {
        override fun onSuccess(asyncActionToken: IMqttToken?) {
            Log.i("NetworkClient", "Unsubscribed to topic")
            onUnsubscribeSuccess()
        }

        override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
            Log.i("NetworkClient", "Failed to unsubscribe topic: ${exception.toString()}")
            onUnsubscribeFailure()
        }
    }

    private val publishCallbacks = object : IMqttActionListener {
        override fun onSuccess(asyncActionToken: IMqttToken?) {
            Log.i("NetworkClient", "Message published to topic")
            onPublishSuccess()
        }

        override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
            Log.i("NetworkClient", "Failed to publish message to topic: ${exception.toString()}")
            onPublishFailure()
        }
    }
    private val disconnectCallbacks = object : IMqttActionListener {
        override fun onSuccess(asyncActionToken: IMqttToken?) {
            Log.i("NetworkClient", "Disconnected")
            onDisconnectSuccess()
        }

        override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
            Log.i("NetworkClient", "Failed to disconnect: ${exception.toString()}")
            onDisconnectFailure()
        }
    }
    //endregion

    fun connect():Boolean{
        val options = MqttConnectOptions()
        options.userName = MQTT_USERNAME
        options.password = MQTT_PWD.toCharArray()

        try {
            mqttClient.connect(options,null, connectCallbacks)
        } catch (e: MqttException) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun isConnected(): Boolean {
        return mqttClient.isConnected
    }

    fun subscribe(topic:String) {
        try {
            mqttClient.subscribe(topic, 1, null, subscribeCallbacks)
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun unsubscribe(topic:String) {
        try {
            mqttClient.unsubscribe(topic, null, unsubscribeCallbacks)
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

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

    fun disconnect() {
        try {
            mqttClient.disconnect(null,disconnectCallbacks)
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

}