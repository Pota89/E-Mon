package angelini.domotica.repository.datatypes

/**
 * Enum che definisce i tipi di Device disponibili nell'App
 *
 * In ottica di espandibilit√† dell'App, per aggiungere nuovi Tipi di
 * device al Repository √® necessario solo aggiungere nuovi nomi
 * alla enum DeviceType.
 */
enum class
DeviceType {
    UNKNOWN, TEMPERATURE, LAMP, MOVEMENT, SHUTTER
}