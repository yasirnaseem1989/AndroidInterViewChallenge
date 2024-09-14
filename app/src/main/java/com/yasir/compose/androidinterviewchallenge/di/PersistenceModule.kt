package com.yasir.compose.androidinterviewchallenge.di

import com.yasir.compose.androidinterviewchallenge.data.provider.EncryptionHelper
import com.yasir.compose.androidinterviewchallenge.persistence.sharedpreference.UserKeyValueStore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val persistenceModule = module {

    single { UserKeyValueStore(androidContext()) }

    single { EncryptionHelper() }
}
