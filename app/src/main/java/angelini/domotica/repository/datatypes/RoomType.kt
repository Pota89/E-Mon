package angelini.domotica.repository.datatypes

/**
 * Enum che definisce i tipi di Room disponibili nell'App
 *
 * In ottica di espandibilit√† dell'App, per aggiungere nuovi tipi di
 * Room al Repository √® necessario solo aggiungere nuovi nomi alla enum DeviceType.
 */
enum class RoomType {
    UNKNOWN, BATHROOM, BEDROOM, KITCHEN, LOUNGE, STUDY, GARAGE, HALL, DINING, HALLWAY
}