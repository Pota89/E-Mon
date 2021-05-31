package angelini.domotica.data

import android.content.Context
import android.widget.Toast
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

class NetworkClient(context: Context) {
    private var mqttClient = MqttAndroidClient(context, MQTT_SERVER_URI, MQTT_CLIENT_ID)
    private val myContext=context

    fun connect():Boolean{
        val options = MqttConnectOptions()
        options.userName = MQTT_USERNAME
        options.password = MQTT_PWD.toCharArray()

        try {
            mqttClient.connect(options)
            Toast.makeText(myContext, "Connesso", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(myContext, "Disconnesso", Toast.LENGTH_SHORT).show();
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }
}