package angelini.domotica.data

import android.content.Context
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

class NetworkClient(context: Context) {
    private var mqttClient = MqttAndroidClient(context, MQTT_SERVER_URI, MQTT_CLIENT_ID)

    fun connect():Boolean{
        val options = MqttConnectOptions()
        options.userName = MQTT_USERNAME
        options.password = MQTT_PWD.toCharArray()

        try {
            mqttClient.connect(options)
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
            mqttClient.disconnect()
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }
}