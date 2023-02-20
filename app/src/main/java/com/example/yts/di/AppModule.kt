package com.example.yts.di

import android.util.Log.VERBOSE
import androidx.room.Room
import com.example.yts.BuildConfig
import com.example.yts.data.MoviesRepository
import com.example.yts.data.local.MovieDatabase
import com.example.yts.data.local.OfflineDataSource
import com.example.yts.data.local.RoomOfflineDataSource
import com.example.yts.data.remote.*
import com.example.yts.ui.MoviesViewModel
import com.example.yts.utils.ArrayListConverter
import com.example.yts.utils.NetworkAwareHandler
import com.example.yts.utils.NetworkHandler
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val roomModule = module {
    single {
        Room.databaseBuilder(get(), MovieDatabase::class.java, "MOVIES_DB")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<MovieDatabase>().getNewsDao() }
}

val networkModule = module {
    single {
        Interceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .url(
                    chain.request()
                        .url
                        .newBuilder()
                        .build()
                )
                .build()
            return@Interceptor chain.proceed(request)   //explicitly return a value from whit @ annotation. lambda always returns the value of the last expression implicitly
        }
    }

    single {
        LoggingInterceptor.Builder()
            .setLevel(Level.BODY)
            .log(VERBOSE)
            .build()
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<Interceptor>())
            .addInterceptor(get<LoggingInterceptor>())
            .connectTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
            .build()
    }

    single {
        GsonConverterFactory.create()
    }

    single {
        CoroutineCallAdapterFactory()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(get<GsonConverterFactory>())
            .addCallAdapterFactory(get<CoroutineCallAdapterFactory>())
            .client(get<OkHttpClient>())
            .build()
    }
}

val apiServiceModule = module {
    factory {
        get<Retrofit>().create(ApiService::class.java)
    }
}

val repoModule = module {
    single { MoviesRepository(get() , get() , get() ) }

    factory  <OfflineDataSource>{ RoomOfflineDataSource(get()) }

    factory <OnlineDataSource> { RetrofitOnlineDataSource(get())  }

    single <NetworkAwareHandler> { NetworkHandler(get())  }

    factory <ApiHelper> { ApiHelperImpl(get())  }
}

val viewModelModule = module {
    viewModel { MoviesViewModel(get()) }
}