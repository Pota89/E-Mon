package angelini.domotica.repository.network

import org.junit.After
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class ParserTest {

    private lateinit var parser:Parser

    @Before
    fun setUp() {
        parser=Parser("testparser")
    }

    @After
    fun tearDown() {
    }

    /**
     * Verifica se la stringa di sottoscrizione a tutti i feeds si formi correttamente anche cambiando l'utente
     */
    @Test
    fun subscribeAllFeeds() {
        val parameterName=parser.subscribeAllFeeds()
        assertEquals("testparser/groups/home",parameterName)

        parser.rootname="changedname"
        val changedName=parser.subscribeAllFeeds()
        assertEquals("changedname/groups/home",changedName)
    }

    /**
     * Verifica se la stringa di richiesta dei feeds si formi correttamente anche cambiando l'utente
     */
    @Test
    fun requestAllFeedsData() {
        val parameterName=parser.requestAllFeedsData()
        assertEquals("testparser/groups/home/get",parameterName)

        parser.rootname="changedname"
        val changedName=parser.requestAllFeedsData()
        assertEquals("changedname/groups/home/get",changedName)
    }

    @Test
    fun decode() {
    }
}