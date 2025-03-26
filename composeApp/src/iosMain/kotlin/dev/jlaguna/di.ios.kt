package dev.jlaguna

import dev.jlaguna.data.RegionDataSource
import dev.jlaguna.data.database.getDatabaseBuilder
import dev.jlaguna.data.IosRegionDatasource
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val nativeModule: Module = module {
    single { getDatabaseBuilder() }
    factoryOf(::IosRegionDatasource) bind RegionDataSource::class
}