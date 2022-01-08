package angelini.domotica.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * Interfaccia per database SQLite, contenente le query necessarie
 *
 * Si definiscono le funzioni e le query associate per la comunicazione tramite la libreria Room di Android,
 * è compito di tale libreria definire in fase di compilazione l'implementazione delle funzioni
 * con il server SQLite
 *
 */
@Dao
interface DeviceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg devices: Device)

    @Query("SELECT * FROM device")
    fun getAllDevices(): Flow<List<Device>>

    @Query("SELECT * FROM device WHERE roomType = :roomType AND roomNumber = :roomNumber")
    fun getRoomDevices(roomType: RoomType, roomNumber: Int): Flow<List<Device>>

    //DISTINCT clause because to avoid duplicates on list
    @Query("SELECT DISTINCT roomType,roomNumber FROM device")
    fun getRoomList(): Flow<List<Room>>

    @Query("DELETE FROM device")
    fun deleteAll()
}