package com.yasir.compose.androidinterviewchallenge.di

import com.yasir.compose.androidinterviewchallenge.data.mapper.LoginMapper
import com.yasir.compose.androidinterviewchallenge.data.provider.FakeUserProvider
import com.yasir.compose.androidinterviewchallenge.data.remote.auth.AuthDataSource
import com.yasir.compose.androidinterviewchallenge.data.remote.auth.RemoteAuthDataSource
import com.yasir.compose.androidinterviewchallenge.data.repository.AuthRepository
import com.yasir.compose.androidinterviewchallenge.domain.LoginUseCase
import com.yasir.compose.androidinterviewchallenge.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val REPOSITORY_AUTH = "REPOSITORY_AUTH"

private const val DATA_SOURCE_AUTH_REMOTE = "DATA_SOURCE_AUTH_REMOTE"

private const val USECASE_AUTH_LOGIN = "USECASE_AUTH_LOGIN"

val authModule = module {

    factory<AuthDataSource>(named(DATA_SOURCE_AUTH_REMOTE)) {
        RemoteAuthDataSource(
            get(),
            get(),
            get(),
            get()
        )
    }

    factory { FakeUserProvider() }

    single(named(REPOSITORY_AUTH)) {
        AuthRepository(get(named(DATA_SOURCE_AUTH_REMOTE)))
    }

    factory { LoginMapper() }



    factory(named(USECASE_AUTH_LOGIN)) {
        LoginUseCase(get(named(REPOSITORY_AUTH)))
    }

    viewModel { LoginViewModel(get(named(USECASE_AUTH_LOGIN)), get()) }
}
