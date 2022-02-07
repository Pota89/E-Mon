package angelini.domotica.repository.network

import angelini.domotica.repository.datatypes.Device
import angelini.domotica.repository.datatypes.DeviceType
import angelini.domotica.repository.datatypes.Room
import angelini.domotica.repository.datatypes.RoomType
import java.lang.NumberFormatException

/**
 * Fornisce medodi per richieste e intepretazione risultati
 *
 * Classe ausilare per la comunicazione di rete, fornisce stringhe formate appositamente
 * per fare richieste al server MQTT. Inoltre fornisce in output oggetti a partire dalle
 * strighe dei feed sottoscritti.
 *
 * @property username nome utente che effettua le richieste
 * @constructor instanzia un oggetto parser con l'utente passato come parametro
 */
class Parser(username: String) {

    var rootname = username

    /**
     * Fornisce la stringa per sottoscrivere tutti i feed disponibili nel server MQTT
     */
    fun subscribeAllFeeds(): String {
        return "${rootname}/groups/home"
    }

    /**
     * Fornisce la stringa per ritornare tutti i feed attualmente registrati con il loro valore
     */
    fun requestAllFeedsData(): String {
        return "${rootname}/groups/home/get"
    }

    /**
     * Interpreta i feeds che vengono inviati dal server MQTT verso il client
     *
     * Il server MQTT fornisce i feeds nel formato:
     * nomeDashboard.tipoRoom-numeroRoom-tipoDevice-numeroDevice,valore
     * quando si tratta di una richesta di tutti i feed
     * ad es. home.bedroom-1-temperature-1,10
     *
     * Se riguarda la conferma di una singola publish il formato è
     * {"feeds":{"tipoRoom-numeroRoom-tipoDevice-numeroDevice":"valore"}}
     * ad es. {"feeds":{"bedroom-1-temperature-1":"30"}}
     *
     * La funzione restituisce una lista di Device valorizzata
     * @property message stringa proveniente dal server MQTT che rappresenta i feeds
     */
    fun decode(message: String): List<Device> {
        val returnList = mutableListOf<Device>()

        val deviceStringList: List<String> = if(message.startsWith("{\"feeds\":{\"")){//single feed response
            val item=message.removePrefix("{\"feeds\":{\"").removeSuffix("\"}}").replace("\":\"",",")
            listOf(item)
        } else{//all feeds response
            message.lines()//split messages by Carriage Return
        }

        for (deviceString in deviceStringList) {
            val noPrefixString=deviceString.removePrefix("home.")
            val deviceElementsAndValuePair=noPrefixString.split(",")//split device info and value
            val deviceElements = deviceElementsAndValuePair[0].split("-") //split room, roomNumber, device, numberDevice

            if(deviceElements.size==4)//if we have a different size, we have a bad formed string
            {
                val roomType = try {
                    RoomType.valueOf(deviceElements[0].uppercase())//matching room type from string to enum
                } catch (e: IllegalArgumentException) {
                    RoomType.UNKNOWN
                }

                val roomNumber = try {
                    deviceElements[1].toInt()
                } catch (e: NumberFormatException) {
                    0
                }

                val deviceType = try {
                    DeviceType.valueOf(deviceElements[2].uppercase())//matching device type from string to enum
                } catch (e: IllegalArgumentException) {
                    DeviceType.UNKNOWN
                }

                val deviceNumber = try {
                    deviceElements[3].toInt()
                } catch (e: NumberFormatException) {
                    0
                }

                val deviceValue = try {
                    deviceElementsAndValuePair[1].toInt()
                } catch (e: NumberFormatException) {
                    0
                }

                returnList.add(
                    Device(
                        Room(roomType, roomNumber),
                        deviceType,
                        deviceNumber,
                        deviceValue
                    )
                )
            }

        }
        return returnList
    }

    /**
     * Restituisce una stringa formattata per la pubblicazione di un Device sul server MQTT
     *
     * @property device elemento da formattare
     */
    fun encodeTopic(device:Device): String {
        val builder = StringBuilder()
        builder.append(rootname)
            .append("/feeds/home.")
            .append(device.room.type.name.lowercase())
            .append("-")
            .append(device.room.number)
            .append("-")
            .append(device.type.name.lowercase())
            .append("-")
            .append(device.number)

        return builder.toString()
    }
}