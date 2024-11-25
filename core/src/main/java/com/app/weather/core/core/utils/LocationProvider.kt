package com.app.weather.core.core.utils
import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class LocationProvider @Inject constructor(
    private val fusedLocationClient: FusedLocationProviderClient,
    private val appContext: Application
) : LocationService {

    override suspend fun fetchCurrentLocation(): Location? {
        val isFineLocationGranted = ContextCompat.checkSelfPermission(
            appContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val isCoarseLocationGranted = ContextCompat.checkSelfPermission(
            appContext,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val locationService = appContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isLocationEnabled =
            locationService.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                    locationService.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if (!isCoarseLocationGranted || !isFineLocationGranted) {
            throw SecurityException("Location permissions are not granted.")
        } else if (!isLocationEnabled) {
            throw IllegalStateException("GPS or Network location is disabled.")
        }

        return suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation.apply {
                if (isComplete) {
                    if (isSuccessful) continuation.resume(result)
                    else continuation.resume(null)
                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener {
                    continuation.resume(it)
                }
                addOnFailureListener {
                    continuation.cancel(it.cause)
                }
                addOnCanceledListener {
                    continuation.cancel()
                }
            }
        }
    }
}

interface LocationService {
    suspend fun fetchCurrentLocation(): Location?
}
