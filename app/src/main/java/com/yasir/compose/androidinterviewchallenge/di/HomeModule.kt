package com.yasir.compose.androidinterviewchallenge.di

import com.yasir.compose.androidinterviewchallenge.data.mapper.UriToFileMapper
import com.yasir.compose.androidinterviewchallenge.data.mapper.UserInfoMapper
import com.yasir.compose.androidinterviewchallenge.data.provider.ImageHelper
import com.yasir.compose.androidinterviewchallenge.data.remote.home.HomeDataSource
import com.yasir.compose.androidinterviewchallenge.data.remote.home.RemoteHomeDataSource
import com.yasir.compose.androidinterviewchallenge.data.repository.HomeRepository
import com.yasir.compose.androidinterviewchallenge.domain.CompressImageUseCase
import com.yasir.compose.androidinterviewchallenge.domain.GetUserInfoUseCase
import com.yasir.compose.androidinterviewchallenge.domain.UpdateProfileUseCase
import com.yasir.compose.androidinterviewchallenge.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val REPOSITORY_HOME = "REPOSITORY_HOME"

private const val DATA_SOURCE_HOME_REMOTE = "DATA_SOURCE_HOME_REMOTE"

private const val USECASE_GET_USER_INFO = "USECASE_GET_USER_INFO"
private const val USECASE_IMAGE_COMPRESS = "USECASE_IMAGE_COMPRESS"
private const val USECASE_UPDATE_PROFILE = "USECASE_UPDATE_PROFILE"

val homeModule = module {

    factory<HomeDataSource>(named(DATA_SOURCE_HOME_REMOTE)) {
        RemoteHomeDataSource(
            get(),
            get(),
            get(),
            get()
        )
    }

    single(named(REPOSITORY_HOME)) {
        HomeRepository(get(named(DATA_SOURCE_HOME_REMOTE)))
    }

    factory(named(USECASE_IMAGE_COMPRESS)) {
        CompressImageUseCase(get())
    }

    factory(named(USECASE_GET_USER_INFO)) {
        GetUserInfoUseCase(get(named(REPOSITORY_HOME)))
    }

    factory(named(USECASE_UPDATE_PROFILE)) {
        UpdateProfileUseCase(get(named(REPOSITORY_HOME)))
    }

    viewModel {
        HomeViewModel(
            get(named(USECASE_GET_USER_INFO)),
            get(named(USECASE_IMAGE_COMPRESS)),
            get(named(USECASE_UPDATE_PROFILE)),
            get(),
            get(),
        )
    }

    factory { ImageHelper(get()) }

    factory { UriToFileMapper(get()) }

    factory { UserInfoMapper() }
}
