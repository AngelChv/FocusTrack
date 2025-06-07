package io.github.angelchv.focustrack.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.angelchv.focustrack.data.remote.auth.AuthService
import io.github.angelchv.focustrack.data.remote.auth.FirebaseAuthServiceImpl
import io.github.angelchv.focustrack.data.repository.AuthRepository
import javax.inject.Singleton

/**
 * Hilt module for providing application-wide singleton dependencies.
 * Provides authentication services and repositories.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    /**
     * Provides the [AuthService] implementation as a singleton.
     * Uses [FirebaseAuthServiceImpl].
     */
    @Provides
    @Singleton
    fun provideAuthService(): AuthService = FirebaseAuthServiceImpl()

    /**
     * Provides the [AuthRepository] as a singleton, injecting [AuthService].
     */
    @Provides
    @Singleton
    fun provideAuthRepository(authService: AuthService): AuthRepository =
        AuthRepository(authService)
}