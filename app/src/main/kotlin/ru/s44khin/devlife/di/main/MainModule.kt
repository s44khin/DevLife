package ru.s44khin.devlife.di.main

import dagger.Module

@Module(
    includes = [
        TopModule::class,
        LatestModule::class,
        FavoritesModule::class,
        PostModule::class]
)
object MainModule