package robin.scaffold.dagger.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
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

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        return GsonBuilder().create()
    }

}