package dev.jlaguna.data

interface RegionDataSource {
    suspend fun fetchRegion(): String
}

const val DEFAULT_REGION = "US"

class RegionRepository(
    private val regionDataSource: RegionDataSource
) {
    suspend fun fetchRegion(): String {
        return regionDataSource.fetchRegion()
    }
}