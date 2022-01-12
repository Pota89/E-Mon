package angelini.domotica.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import angelini.domotica.repository.datatypes.Device

/**
 * Classe astratta che definisce la struttura del database
 *
 * L'implementazione è automatizzata in fase di compilazione ed è a
 * carico della libreria Room di Android.
 */
@Database(entities = [Device::class], version = 1, exportSchema = false)
abstract class CacheDatabase : RoomDatabase() {
    abstract fun deviceDao(): DeviceDao
}