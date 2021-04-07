package angelini.domotica.data

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

const val MQTT_SERVER_URI       = "tcp://io.adafruit.com:1883"
const val MQTT_CLIENT_ID        = ""
const val MQTT_USERNAME         = "ExamToGo"
const val MQTT_PWD              = "aio_QhVU8878VItl07ohHG13IrtQ2EAc"

class NetworkClient(context: Context) {
    //private var mqttClient = MqttAndroidClient(context, MQTT_SERVER_URI, MQTT_CLIENT_ID)

    private lateinit var mqttClient : MQTTClient

    fun connect(context: Context){
        // Open MQTT Broker communication
        mqttClient = MQTTClient(context, MQTT_SERVER_URI, MQTT_CLIENT_ID)

        // Connect and login to MQTT Broker
        mqttClient.connect( MQTT_USERNAME,
                MQTT_PWD,
                object : IMqttActionListener {
                    override fun onSuccess(asyncActionToken: IMqttToken?) {
                        Log.d(this.javaClass.name, "Connection success")

                        Toast.makeText(context, "MQTT Connection success", Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                        Log.d(this.javaClass.name, "Connection failure: ${exception.toString()}")

                        Toast.makeText(context, "MQTT Connection fails: ${exception.toString()}", Toast.LENGTH_SHORT).show()

                    }
                },
                object : MqttCallback {
                    override fun messageArrived(topic: String?, message: MqttMessage?) {
                        val msg = "Receive message: ${message.toString()} from topic: $topic"
                        Log.d(this.javaClass.name, msg)

                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    }

                    override fun connectionLost(cause: Throwable?) {
                        Log.d(this.javaClass.name, "Connection lost ${cause.toString()}")
                    }

                    override fun deliveryComplete(token: IMqttDeliveryToken?) {
                        Log.d(this.javaClass.name, "Delivery complete")
                    }
                })
    }
/*
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
*/
    fun isConnected(): Boolean {
        return mqttClient.isConnected()
    }
/*
    fun disconnect() {
        try {
            mqttClient.disconnect()
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }*/
}