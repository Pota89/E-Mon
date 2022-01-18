package angelini.domotica
/*

    private val cbClient = object : MqttCallback {
        override fun messageArrived(topic: String?, message: MqttMessage?) {
            Log.d(this.javaClass.name, "Receive message: ${message.toString()} from topic: $topic")
        }

        override fun connectionLost(cause: Throwable?) {
            Log.d(this.javaClass.name, "Connection lost ${cause.toString()}")
        }

        override fun deliveryComplete(token: IMqttDeliveryToken?) {
            Log.d(this.javaClass.name, "Delivery completed")
        }
    }





 var mqttClient = MqttAndroidClient(applicationContext, "tcp://io.adafruit.com:1883", "")
        mqttClient.setCallback(cbClient)
        val options = MqttConnectOptions()
        options.userName = MQTT_USERNAME
        //options.userName="Pippo"
        options.password = MQTT_PWD.toCharArray()

        try {
            mqttClient.connect(options, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.d(this.javaClass.name, "(Default) Connection success")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.d(this.javaClass.name, "Connection failure: ${exception.toString()}")
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }
* */