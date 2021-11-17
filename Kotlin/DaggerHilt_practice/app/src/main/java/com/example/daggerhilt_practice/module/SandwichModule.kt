package com.example.daggerhilt_practice.module


import com.example.daggerhilt_practice.Bread
import com.example.daggerhilt_practice.Cheeze
import com.example.daggerhilt_practice.Sandwich
import com.example.daggerhilt_practice.Vegatable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Inject

@Module
@InstallIn(ActivityComponent::class)
object SandwichModule {

    @Provides
    fun provideBread() : Bread = Bread("wheat", 50)
    @Provides
    fun provideCheeze() : Cheeze = Cheeze("Blue Cheeze", 500)
    @Provides
    fun provideVegatable() : Vegatable = Vegatable("cabbage", 10)

    @Provides
    fun provideSandwich(bread: Bread, cheeze: Cheeze, vegatable: Vegatable) : Sandwich = Sandwich(bread, cheeze, vegatable)

}