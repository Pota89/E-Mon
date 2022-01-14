package angelini.domotica.repository

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import angelini.domotica.repository.db.CacheDatabase
import angelini.domotica.repository.network.MockNetworkClient
import angelini.domotica.repository.network.NetworkClient
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Verifica del repository con network mocked
 */
@RunWith(AndroidJUnit4::class)
class RepositoryTest {

    private lateinit var database: CacheDatabase
    private lateinit var mockNetworkClient: MockNetworkClient
    private lateinit var repository:Repository

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database=androidx.room.Room.inMemoryDatabaseBuilder(context,CacheDatabase::class.java).build()
        mockNetworkClient= MockNetworkClient()
        repository=Repository(database,mockNetworkClient)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getDevicesList() {
    }

    @Test
    fun getRoomsList() {
    }

    @Test
    fun getRoomDevices() {
    }

    @Test
    fun connect() {
    }

    @Test
    fun disconnect() {
    }
}