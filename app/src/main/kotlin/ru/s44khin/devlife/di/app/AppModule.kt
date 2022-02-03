package ru.s44khin.devlife.di.app

import dagger.Module

@Module(includes = [NetworkModule::class, LocalModule::class])
object AppModule