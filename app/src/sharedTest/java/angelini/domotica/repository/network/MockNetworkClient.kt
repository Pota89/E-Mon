package angelini.domotica.repository.network

import java.lang.StringBuilder

const val MOCKED_MQTT_USERNAME         = "testuser"
const val MOCKED_MQTT_PWD              = "testpassword"

/**
 * Classe mock che simula la connessione al server MQTT
 *
 * I metodi mockati richiamano direttamente le callback
 */
class MockNetworkClient() : INetworkClient {
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

    private var connected=false

    private data class Item(var subscribed: Boolean, var value: String)
    private var deviceMap = HashMap<String, Item>()

    init {
        deviceMap["home.bedroom-1-temperature-1"]=Item(false,"19")
        deviceMap["home.bedroom-2-temperature-1"]=Item(false,"25")
        deviceMap["home.kitchen-0-lamp-1"]=Item(false,"")
        deviceMap["home.hallway-0-lamp-1"]=Item(false,"1")
        deviceMap["home.lounge-1-lamp-1"]=Item(false,"1")
        deviceMap["home.lounge-2-lamp-0"]=Item(false,"1")
        deviceMap["home.bedroom-1-temperature-2"]=Item(false,"16")
    }

    /**
     * Connetti al server MQTT mocked
     *
     * Usare "testuser" e "testpassword" per ritornare true
     *
     * @property username nome utente
     * @property password password dell'utente
     */
    override fun connect(username:String,password:String){
        return if(username==MOCKED_MQTT_USERNAME && password== MOCKED_MQTT_PWD && !connected){
            connected=true
            onConnectionSuccess()
        }else{
            connected=false
            onConnectionFailure()
        }
    }

    /**
     * Verifica se si è connessi al server MQTT
     */
    override fun isConnected(): Boolean {
        return connected
    }

    /**
     * Sottoscrizione a uno o tutti i feed
     *
     * @property topic nome del feed
     */
    override fun subscribe(topic:String) {
        if(connected){
            if(topic=="$MOCKED_MQTT_USERNAME/groups/home"){
                deviceMap.forEach{item->
                    item.value.subscribed=true
                }
                onSubscribeSuccess()
            }else if(deviceMap.containsKey(topic))
            {
                deviceMap[topic]?.subscribed = true
                onSubscribeSuccess()
            }
            else
                onSubscribeFailure()
        }
        else
            onSubscribeFailure()
    }

    /**
     * Rimozione della sottoscrizione a un feed
     *
     * @property topic nome del feed
     */
    override fun unsubscribe(topic:String) {
        if(connected && deviceMap.containsKey(topic)) {
            deviceMap[topic]?.subscribed=false
            onUnsubscribeSuccess()
        }
        else
            onUnsubscribeFailure()
    }

    /**
     * Pubblica un messaggio sul feed
     *
     * @property topic nome del feed
     * @property msg messaggio pubblicato nel feed
     */
    override fun publish(topic:      String,
                msg:        String) {
        if(connected) {
            onPublishSuccess()

            if (topic == "$MOCKED_MQTT_USERNAME/groups/home/get") {
                val responseMsg=StringBuilder()
                val responseTopic=topic.removeSuffix("/get")
                val iterator=deviceMap.iterator()
                while(iterator.hasNext()){
                    val item=iterator.next()
                    if(item.value.subscribed){
                        responseMsg.append(item.key)
                        responseMsg.append(",")
                        responseMsg.append(item.value.value)
                        responseMsg.append("\n")
                    }
                }
                val responseMsgChar=responseMsg.removeSuffix("\n")//remove endline for the last item

                onMessageArrived(responseTopic, responseMsgChar.toString())
            } else if(topic.startsWith("$MOCKED_MQTT_USERNAME/feeds/")){
                val deviceName=topic.removePrefix("$MOCKED_MQTT_USERNAME/feeds/")
                if(deviceMap.contains(deviceName)) {
                    deviceMap[deviceName]?.value = msg
                    onMessageArrived("$MOCKED_MQTT_USERNAME/groups/home", "{\"feeds\":{\"$deviceName\":\"$msg\"}}")
                }
            }
        }else{
            onPublishFailure()
        }
    }

    /**
     * Disconetti dal server MQTT attualmente collegato
     */
    override fun disconnect() {
        connected=false
        deviceMap.forEach{item->
            item.value.subscribed=false
        }
        onDisconnectSuccess()
    }

}