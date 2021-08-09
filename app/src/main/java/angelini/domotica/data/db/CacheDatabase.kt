package angelini.domotica.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Device::class], version = 1, exportSchema = false)
abstract class CacheDatabase : RoomDatabase() {
    abstract fun deviceDao(): DeviceDao
}