package com.firmfreez.epubreader.di

import com.firmfreez.epubreader.MainActivityViewModel
import org.koin.dsl.module
import org.koin.plugin.module.dsl.viewModel

val mainActivityModule = module {
    viewModel<MainActivityViewModel>()
}
