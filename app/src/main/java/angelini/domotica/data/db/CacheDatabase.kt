package angelini.domotica.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Room::class], version = 1)
abstract class CacheDatabase : RoomDatabase() {
    abstract fun userDao(): RoomDao
}