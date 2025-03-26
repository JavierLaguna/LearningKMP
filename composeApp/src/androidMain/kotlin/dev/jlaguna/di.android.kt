package dev.jlaguna

import android.location.Geocoder
import com.google.android.gms.location.LocationServices
import dev.jlaguna.data.AndroidRegionDatasource
import dev.jlaguna.data.RegionDataSource
import dev.jlaguna.data.database.getDatabaseBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val nativeModule: Module = module {
    single { getDatabaseBuilder(get()) }
    factory { Geocoder(get()) }
    factory { LocationServices.getFusedLocationProviderClient(androidContext()) }
    factoryOf(::AndroidRegionDatasource) bind RegionDataSource::class
}