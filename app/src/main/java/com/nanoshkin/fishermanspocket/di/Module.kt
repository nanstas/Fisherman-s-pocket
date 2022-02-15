package com.nanoshkin.fishermanspocket.di

import android.content.Context
import androidx.room.Room
import com.nanoshkin.fishermanspocket.BuildConfig
import com.nanoshkin.fishermanspocket.data.api.WeatherApi
import com.nanoshkin.fishermanspocket.data.db.AppDb
import com.nanoshkin.fishermanspocket.data.db.LureDao
import com.nanoshkin.fishermanspocket.data.repository.LureRepositoryImpl
import com.nanoshkin.fishermanspocket.data.repository.WeatherRepositoryImpl
import com.nanoshkin.fishermanspocket.domain.repository.LureRepository
import com.nanoshkin.fishermanspocket.domain.repository.WeatherRepository
import com.nanoshkin.fishermanspocket.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {
    @Provides
    @Singleton
    fun provideLureRepository(dao: LureDao): LureRepository {
        return LureRepositoryImpl(dao = dao)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(weatherApi: WeatherApi): WeatherRepository {
        return WeatherRepositoryImpl(weatherApi = weatherApi)
    }

    @Provides
    fun provideGetAllLuresUseCase(lureRepository: LureRepository): GetAllLuresUseCase {
        return GetAllLuresUseCase(lureRepository = lureRepository)
    }

    @Provides
    fun provideSaveLureUseCase(lureRepository: LureRepository): SaveLureUseCase {
        return SaveLureUseCase(lureRepository = lureRepository)
    }

    @Provides
    fun provideIncreaseInCaughtFishUseCase(lureRepository: LureRepository): IncreaseInCaughtFishUseCase {
        return IncreaseInCaughtFishUseCase(lureRepository = lureRepository)
    }

    @Provides
    fun provideGetLuresByIdUseCase(lureRepository: LureRepository): GetLuresByIdUseCase {
        return GetLuresByIdUseCase(lureRepository = lureRepository)
    }

    @Provides
    fun provideRemoveLureByIdUseCase(lureRepository: LureRepository): RemoveLureByIdUseCase {
        return RemoveLureByIdUseCase(lureRepository = lureRepository)
    }

    @Provides
    @Singleton
    fun provideSaveNoteUseCase(lureRepository: LureRepository): SaveNoteUseCase {
        return SaveNoteUseCase(lureRepository = lureRepository)
    }

    @Provides
    @Singleton
    fun provideGetCurrentWeatherByCityUseCase(weatherRepository: WeatherRepository): GetCurrentWeatherByCityUseCase {
        return GetCurrentWeatherByCityUseCase(weatherRepository = weatherRepository)
    }

    @Provides
    fun provideLureDao(db: AppDb): LureDao = db.getLureDao()

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context): AppDb {
        return Room.databaseBuilder(context, AppDb::class.java, "app.db")
            .build()
    }

    @Provides
    fun provideLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            if (BuildConfig.DEBUG) {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }

    @Provides
    @Singleton
    fun provideWeatherApi(retrofit: Retrofit): WeatherApi {
        return retrofit
            .create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkhttp(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }
}