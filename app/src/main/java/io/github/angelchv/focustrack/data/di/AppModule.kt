package io.github.angelchv.focustrack.data.di

import io.github.angelchv.focustrack.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.angelchv.focustrack.data.remote.auth.AuthService
import io.github.angelchv.focustrack.data.remote.auth.FirebaseAuthServiceImpl
import io.github.angelchv.focustrack.data.remote.movie.MovieService
import io.github.angelchv.focustrack.data.remote.movie.TMDBMovieService
import io.github.angelchv.focustrack.data.remote.tmdb.TMDBApi
import io.github.angelchv.focustrack.data.repository.AuthRepository
import io.github.angelchv.focustrack.data.repository.MovieRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val request = chain.request().newBuilder()
                            .addHeader("Authorization", "Bearer ${BuildConfig.TMDB_ACCESS_TOKEN}")
                            .addHeader("accept", "application/json")
                            .build()
                        chain.proceed(request)
                    }
                    .build()
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideTMDbApi(retrofit: Retrofit): TMDBApi = retrofit.create(TMDBApi::class.java)

    @Provides
    @Singleton
    fun provideMovieService(tmdbApi: TMDBApi): MovieService = TMDBMovieService(tmdbApi)

    @Provides
    @Singleton
    fun provideMovieRepository(movieService: MovieService): MovieRepository =
        MovieRepository(movieService)
}