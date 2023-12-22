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

	suspend fun setStatusOnboarding(status: Boolean) {
		context.dataStore.edit {
			it[STATUS_ONBOARDING_KEY] = status
		}
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

	// Getter method for the device token
	val getLatUser: Flow<Double> = context.dataStore.data.map { preferences ->
		// Retrieve the stored string value or use a default value if it doesn't exist
		preferences[LAT_KEY]?.toDoubleOrNull() ?: -7.052945994551127
	}

	// Setter method for the device token
	suspend fun setLatUser(lat: Double) {
		context.dataStore.edit { preferences ->
			// Store the double value as a string
			preferences[LAT_KEY] = lat.toString()
		}
	}

	val getLonUser: Flow<Double> = context.dataStore.data.map { preferences ->
		// Retrieve the stored string value or use a default value if it doesn't exist
		preferences[LON_KEY]?.toDoubleOrNull() ?: 	110.44020676422383

	}

	// Setter method for the device token
	suspend fun setLonUser(lat: Double) {
		context.dataStore.edit { preferences ->
			// Store the double value as a string
			preferences[LON_KEY] = lat.toString()
		}
	}


	suspend fun clear() {
		context.dataStore.edit {
			it.clear()
		}
	}

	val getIsLogin: Flow<Boolean> = context.dataStore.data.map {
		it[ISLOGIN_KEY] ?: false
	}

	suspend fun setIsLogin(status: Boolean) {
		context.dataStore.edit {
			it[ISLOGIN_KEY] = status
		}
	}


	val getName: Flow<String?> = context.dataStore.data.map {
		it[NAME_KEY]
	}

	suspend fun setName(name: String) {
		context.dataStore.edit {
			it[NAME_KEY] = name
		}
	}

	val getEmail: Flow<String?> = context.dataStore.data.map {
		it[EMAIL_KEY]
	}

	suspend fun setEmail(email: String) {
		context.dataStore.edit {
			it[EMAIL_KEY] = email
		}
	}

	val getPassword: Flow<String?> = context.dataStore.data.map {
		it[PASSWORD_KEY]
	}

	suspend fun setPassword(password: String) {
		context.dataStore.edit {
			it[PASSWORD_KEY] = password
		}
	}

	companion object {
		private const val DATASTORE_NAME = "datastore_preferences"
		private val STATUS_ONBOARDING_KEY = booleanPreferencesKey("status_onboarding_key")
		private val DEVICE_TOKEN_KEY = stringPreferencesKey("device_token_key")

		private val ISLOGIN_KEY = booleanPreferencesKey("islogin_key")
		private val NAME_KEY = stringPreferencesKey("name_key")
		private val EMAIL_KEY = stringPreferencesKey("email_key")
		private val PASSWORD_KEY = stringPreferencesKey("password_key")


		private val LAT_KEY = stringPreferencesKey("lat_key")
		private val LON_KEY = stringPreferencesKey("lon_key")

		private val Context.dataStore by preferencesDataStore(
			name = DATASTORE_NAME
		)
	}
}
