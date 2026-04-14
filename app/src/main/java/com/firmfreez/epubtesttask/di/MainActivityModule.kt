package com.firmfreez.epubtesttask.di

import com.firmfreez.epubtesttask.MainActivityViewModel
import org.koin.dsl.module
import org.koin.plugin.module.dsl.viewModel

val mainActivityModule = module {
    viewModel<MainActivityViewModel>()
}
