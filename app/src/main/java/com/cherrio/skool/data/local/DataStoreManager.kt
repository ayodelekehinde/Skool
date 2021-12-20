package com.cherrio.skool.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.cherrio.skool.domain.response_entity.Prefs
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * The class that manages local data
 * @see DataStore
 */

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "skool_persistence")
class DataStoreManager @Inject constructor(@ApplicationContext applicationContext: Context) {

    /**
     * Getters
     */

    private val dataStore = applicationContext.dataStore

    val getPref = dataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map { preference ->
            val isSet = preference[keySet] ?: false
            val cat = preference[keyCategory] ?: "Business"
            val price = preference[keyPrice] ?: "price-free"
            val level = preference[keyInstructionalLevel] ?: "beginner"
            val order = preference[keyOrdering] ?: "relevance"
            Prefs(cat, price, level, order, isSet)
        }

    /**
     * Setters
     */

    suspend fun setPref(p: Prefs){
        dataStore.edit { pref ->
            pref[keyCategory] = p.category
            pref[keyPrice] = p.price
            pref[keyInstructionalLevel] = p.learningLevel
            pref[keyOrdering] = p.ordering
            pref[keySet] = p.isSet
        }
    }

    /**
     * Constants
     */
    companion object {
        val keyCategory = stringPreferencesKey("category")
        val keyPrice = stringPreferencesKey("price")
        val keyInstructionalLevel = stringPreferencesKey("instructional_level")
        val keyOrdering = stringPreferencesKey("ordering")
        val keySet = booleanPreferencesKey("isSet")

    }

}