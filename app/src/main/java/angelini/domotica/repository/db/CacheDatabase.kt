package angelini.domotica.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import angelini.domotica.repository.datatypes.Device

@Database(entities = [Device::class], version = 1, exportSchema = false)
abstract class CacheDatabase : RoomDatabase() {
    abstract fun deviceDao(): DeviceDao
}