package com.nanoshkin.fishermanspocket.di

import android.content.Context
import androidx.room.Room
import com.nanoshkin.fishermanspocket.data.db.AppDb
import com.nanoshkin.fishermanspocket.data.db.LureDao
import com.nanoshkin.fishermanspocket.data.repository.LureRepositoryImpl
import com.nanoshkin.fishermanspocket.domain.repository.LureRepository
import com.nanoshkin.fishermanspocket.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {
    @Provides
    @Singleton
    fun provideLureRepository(dao: LureDao): LureRepository = LureRepositoryImpl(dao = dao)

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
    fun provideLureDao(db: AppDb): LureDao = db.getLureDao()

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context): AppDb {
        return Room.databaseBuilder(context, AppDb::class.java, "app.db")
            .build()
    }
}