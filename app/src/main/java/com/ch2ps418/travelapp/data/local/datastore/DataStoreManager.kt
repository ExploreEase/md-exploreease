package com.ch2ps418.travelapp.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(@ApplicationContext private val context: Context) {

	val getStatusOnboarding: Flow<Boolean> = context.dataStore.data.map {
		it[STATUS_ONBOARDING_KEY] ?: false
	}

	// Getter method for the device token
	val getDeviceToken: Flow<String?> = context.dataStore.data.map {
		it[DEVICE_TOKEN_KEY]
	}

	// Setter method for the device token
	suspend fun setDeviceToken(deviceToken: String) {
		context.dataStore.edit {
			it[DEVICE_TOKEN_KEY] = deviceToken
		}
	}

	suspend fun setStatusOnboarding(status: Boolean) {
		context.dataStore.edit {
			it[STATUS_ONBOARDING_KEY] = status
		}
	}

	suspend fun clear() {
		context.dataStore.edit {
			it.clear()
		}
	}

	companion object {
		private const val DATASTORE_NAME = "datastore_preferences"
		private val STATUS_ONBOARDING_KEY = booleanPreferencesKey("status_onboarding_key")
		private val DEVICE_TOKEN_KEY = stringPreferencesKey("device_token_key")
		private val Context.dataStore by preferencesDataStore(
			name = DATASTORE_NAME
		)
	}
}
