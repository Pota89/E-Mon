package angelini.domotica.data

import android.content.Context
import android.util.Log
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

class NetworkClient(context: Context) {
    private var mqttClient = MqttAndroidClient(context, MQTT_SERVER_URI, MQTT_CLIENT_ID)

    var onConnectionSuccess: () -> Unit = {}
    var onConnectionFailure: () -> Unit = {}

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

    fun disconnect() {
        try {
            Log.i("NetworkClient","Disconnect")
            mqttClient.disconnect()
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }
}