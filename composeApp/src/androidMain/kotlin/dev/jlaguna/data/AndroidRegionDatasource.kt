package dev.jlaguna.data

import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import androidx.annotation.FloatRange
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume

class AndroidRegionDatasource(
    private val geocoder: Geocoder,
    private val fusedLocationClient: FusedLocationProviderClient
): RegionDataSource {

    override suspend fun fetchRegion(): String {
        return fusedLocationClient.lastLocation()?.toRegion() ?: DEFAULT_REGION
    }

    private suspend fun Location.toRegion(): String {
        val address = geocoder.getFromLocationCompat(latitude, longitude, 1)
        return address.firstOrNull()?.countryCode ?: DEFAULT_REGION
    }
}

@SuppressLint("MissingPermission")
private suspend fun FusedLocationProviderClient.lastLocation(): Location? {
    return suspendCancellableCoroutine { continuation ->
        lastLocation.addOnSuccessListener {
            continuation.resume(it)
        }.addOnFailureListener {
            continuation.resume(null)
        }
    }
}

@Suppress("DEPRECATION")
suspend fun Geocoder.getFromLocationCompat(
    @FloatRange(from = -90.0, to = 90.0) latitude: Double,
    @FloatRange(from = -180.0, to = 180.0) longitude: Double,
    // @IntRange maxResults: Int
    maxResults: Int
): List<Address> = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    suspendCancellableCoroutine { continuation ->
        getFromLocation(latitude, longitude, maxResults) {
            continuation.resume(it)
        }
    }
} else {
    withContext(Dispatchers.IO) {
        getFromLocation(latitude, longitude, maxResults) ?: emptyList()
    }
}