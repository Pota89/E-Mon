package angelini.domotica.repository.network
/*
/**
 * Interfaccia per la comunicazione con il server MQTT di IO Adafruit
 *
 * Fornisce una interfaccia comune sia per la reale implementazione del client di rete
 * che per il relativo mock
 */
interface INetworkClient {
    var onConnectionSuccess: () -> Unit
    var onConnectionFailure: () -> Unit
    var onSubscribeSuccess: () -> Unit
    var onSubscribeFailure: () -> Unit
    var onUnsubscribeSuccess: () -> Unit
    var onUnsubscribeFailure: () -> Unit
    var onPublishSuccess: () -> Unit
    var onPublishFailure: () -> Unit
    var onDisconnectSuccess: () -> Unit
    var onDisconnectFailure: () -> Unit
    var onMessageArrived: (topic: String, message: String) -> Unit
    var onConnectionLost: () -> Unit
    var onDeliveryComplete: () -> Unit

    /**
     * Connetti al server MQTT
     *
     * @property username nome utente
     * @property password password dell'utente
     */
    fun connect(username: String, password: String): Boolean

    /**
     * Verifica se si è connessi al server MQTT
     */
    fun isConnected(): Boolean

    /**
     * Sottoscrizione a un feed
     *
     * @property topic nome del feed
     */
    fun subscribe(topic: String)

    /**
     * Rimozione della sottoscrizione a un feed
     *
     * @property topic nome del feed
     */
    fun unsubscribe(topic: String)

    /**
     * Pubblica un messaggio sul feed
     *
     * @property topic nome del feed
     * @property msg messaggio pubblicato nel feed
     */
    fun publish(
        topic: String,
        msg: String
    )

    /**
     * Disconetti dal server MQTT attualmente collegato
     */
    fun disconnect()
}*/