package angelini.domotica.repository.db

import androidx.room.*
import angelini.domotica.repository.datatypes.Device
import angelini.domotica.repository.datatypes.Room
import angelini.domotica.repository.datatypes.RoomType
import kotlinx.coroutines.flow.Flow

/**
 * Interfaccia per database SQLite, contenente le query necessarie
 *
 * Si definiscono le funzioni e le query associate per la comunicazione tramite la libreria
 * gestione database di Android,è compito di tale libreria definire in fase di compilazione
 * l'implementazione delle funzioni con il server SQLite
 *
 */
@Dao
interface DeviceDao {

    /**
     * Inserimento Device nel database
     *
     * La specifica onConflict.REPLACE serve per forzare la sovrascrittura
     * di un Device già presente nel database anche se con un valore diverso
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg devices: Device)

    /**
     * Ottiene tutti i Device presenti nel database
     */
    @Query("SELECT * FROM device")
    fun getAllDevices(): Flow<List<Device>>

    /**
     * Ottiene la lista di tutti i Device presenti in una Room
     *
     * @property roomType tipo della Room
     * @property roomNumber numero della Room
     */
    @Query("SELECT * FROM device WHERE roomType = :roomType AND roomNumber = :roomNumber")
    fun getRoomDevices(roomType: RoomType, roomNumber: Int): Flow<List<Device>>

    /**
     * Ottiene tutte le Room presenti nel database
     *
     * Si ottengono a partire dai Device registrati nel database.
     * La clausola DISTINCT serve per non avere Room duplicati nel caso una Room
     * abbia più di un Device
     */
    @Query("SELECT DISTINCT roomType,roomNumber FROM device")
    fun getRoomList(): Flow<List<Room>>

    /**
     * Elimina tutti i Device nel database
     */
    @Query("DELETE FROM device")
    fun deleteAll()
}