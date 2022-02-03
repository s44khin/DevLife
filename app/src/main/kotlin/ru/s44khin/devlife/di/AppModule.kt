package ru.s44khin.devlife.di

import dagger.Module

@Module(includes = [NetworkModule::class, LocalModule::class])
object AppModule