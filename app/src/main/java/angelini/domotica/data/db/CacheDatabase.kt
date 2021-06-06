package angelini.domotica.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Device::class], version = 1)
abstract class CacheDatabase : RoomDatabase() {
    abstract fun userDao(): DeviceDao
}