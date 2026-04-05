package com.nisrmarket.foodingredientrecognizer.di

import com.nisrmarket.foodingredientrecognizer.data.repository.FoodImageRepositoryImpl
import com.nisrmarket.foodingredientrecognizer.domain.repository.FoodImageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindFoodImageRepository(
        impl : FoodImageRepositoryImpl
    ): FoodImageRepository

}