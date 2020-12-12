package com.braziusProductions.gogginsmotivation

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import android.provider.Settings


class PermissionHelper(
    private val fragmentOrActivity: Any,
    private val permission: String,
    private val requestCode: Int = getNextRequestCode()
) {

    private companion object {
        private var _nextRequestCode: Int = 22576
        private fun getNextRequestCode() = _nextRequestCode++
    }

    private val targetActivity = when (fragmentOrActivity) {
        is Activity -> fragmentOrActivity
        is Fragment -> fragmentOrActivity.activity
        else -> throw IllegalArgumentException("You should provide Fragment or Activity type argument")
    }

    private fun shouldShowRequestPermissionRationale() =
        if (fragmentOrActivity is Fragment) {
            fragmentOrActivity.shouldShowRequestPermissionRationale(permission)
        } else {
            ActivityCompat.shouldShowRequestPermissionRationale(fragmentOrActivity as Activity, permission)
        }

    /**
     * Checks wheither subject permission is granted or not.
     * @return true, if granted.
     */
    fun isGranted(): Boolean =
        ContextCompat.checkSelfPermission(targetActivity!!, permission) == PackageManager.PERMISSION_GRANTED

    /**
     * Makes permission request.
     */
    fun request() {
        if (fragmentOrActivity is Fragment) {
            fragmentOrActivity.requestPermissions(arrayOf(permission), requestCode)
        } else {
            ActivityCompat.requestPermissions(fragmentOrActivity as Activity, arrayOf(permission), requestCode)
        }
    }

    /**
     * This function should be called from Activity or Fragment onRequestPermissionsResult.
     * @param requestCode Request code received by onRequestPermissionsResult
     * @param grantResults Permission granting results received by onRequestPermissionsResult
     * @param permissionResult [PermissionResult] structure with a bunch of callbacks receiving all events related to permissions.
     *
     * @return true, if permission response relies to this [PermissionHelper]
     *
     * @see PermissionResult
     */
    fun onRequestPermissionResult(requestCode: Int,
                                  grantResults: IntArray,
                                  permissionResult: PermissionResult.() -> Unit): Boolean =
        if (requestCode == this.requestCode) {
            val pr = PermissionResult()
            pr.permissionResult()
            if (grantResults.isNotEmpty()) {
                when (grantResults[0]) {
                    PackageManager.PERMISSION_GRANTED -> pr.grantedCallback?.invoke()
                    PackageManager.PERMISSION_DENIED -> pr.deniedCallback?.
                    invoke(!shouldShowRequestPermissionRationale())
                }
            }
            true
        } else {
            false
        }

    /**
     * Utility function shows application settings system page.
     */
    fun showApplicationDetailsSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.data = Uri.fromParts("package", targetActivity?.packageName, null)
        targetActivity?.startActivity(intent)
    }

    /**
     * This structure provides DSL for permission callbacks.
     */
    class PermissionResult {
        internal var grantedCallback: (() -> Unit)? = null
        internal var deniedCallback: ((isPermanent: Boolean) -> Unit)? = null

        /**
         * Permission granted callback setter.
         * @param callback Callback for permission granted event
         */
        fun onGranted(callback: () -> Unit) {
            grantedCallback = callback
        }

        /**
         * Permission denied callback setter.
         * @param callback Callback for permission denied event
         */
        fun onDenied(callback: (isPermanent: Boolean) -> Unit) {
            deniedCallback = callback
        }
    }

}