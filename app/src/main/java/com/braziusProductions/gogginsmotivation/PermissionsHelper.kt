package com.braziusProductions.gogginsmotivation

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionHelper(
    private val activity: Activity,
    private val permission: String
) {
    companion object {
        private const val REQUEST_CODE = 1234
    }

    fun isGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            activity,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun request() {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(permission),
            REQUEST_CODE
        )
    }



    fun onRequestPermissionResult(
        requestCode: Int,
        grantResults: IntArray,
        callback: PermissionCallback.() -> Unit
    ) {
        if (requestCode == REQUEST_CODE) {
            val permissionCallback = PermissionCallback()
            callback(permissionCallback)

            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                permissionCallback.onGrantedCallback?.invoke()
            } else {
                val isPermanentlyDenied = !ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    permission
                )
                permissionCallback.onDeniedCallback?.invoke(isPermanentlyDenied)
            }
        }
    }

    fun showApplicationDetailsSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", activity.packageName, null)
        intent.data = uri
        activity.startActivity(intent)
    }

    class PermissionCallback {
        internal var onGrantedCallback: (() -> Unit)? = null
        internal var onDeniedCallback: ((isPermanent: Boolean) -> Unit)? = null

        fun onGranted(callback: () -> Unit) {
            onGrantedCallback = callback
        }

        fun onDenied(callback: (isPermanent: Boolean) -> Unit) {
            onDeniedCallback = callback
        }
    }
}