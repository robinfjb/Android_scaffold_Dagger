package robin.scaffold.dagger.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import robin.scaffold.dagger.db.AppDatabase
import robin.scaffold.dagger.repo.PreferenceObject
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class,NetworkModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideContext(app: Application): Context {
        return app
    }

    @Singleton
    @Provides
    fun provideDb(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "database-robin")
                .fallbackToDestructiveMigration().build()
    }


    @Provides
    @Singleton
    internal fun providePreferenceObject(context: Context): PreferenceObject {
        return PreferenceObject(context)
    }

}