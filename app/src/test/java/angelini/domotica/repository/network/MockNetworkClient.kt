package angelini.domotica.repository.network

import angelini.domotica.repository.datatypes.Device

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

    private val deviceList: MutableList<Device> = mutableListOf()

    init {
        deviceList
        /*"home.bedroom-1-temperature-1,19\n" +
                "home.bedroom-2-temperature-1,25\n" +
                "home.kitchen-0-lamp-1,\"\"\n" +
                "home.hallway-0-lamp-1,1\n" +
                "home.lounge-1-lamp-1,1\n" +
                "home.lounge-2-lamp-0,1\n" +
                "home.bedroom-1-temperature-2,16"*/
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
        return if(username==MOCKED_MQTT_USERNAME && password== MOCKED_MQTT_PWD){
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
     * Sottoscrizione a un feed
     *
     * @property topic nome del feed
     */
    override fun subscribe(topic:String) {
        if(connected) {
            //topicSet.add(topic)
            onSubscribeSuccess()
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
        if(connected) {
            //topicSet.remove(topic)
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

            if (msg == "testuser/groups/home/get") {
                val responseMsg= "home.bedroom-1-temperature-1,19\n" +
                        "home.bedroom-2-temperature-1,25\n" +
                        "home.kitchen-0-lamp-1,\"\"\n" +
                        "home.hallway-0-lamp-1,1\n" +
                        "home.lounge-1-lamp-1,1\n" +
                        "home.lounge-2-lamp-0,1\n" +
                        "home.bedroom-1-temperature-2,16"

                onMessageArrived(topic, responseMsg)
            } else if(/*topicSet.contains(topic)*/ true){
                onMessageArrived(topic, msg)
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
        //topicSet.clear()
        onDisconnectSuccess()
    }

}