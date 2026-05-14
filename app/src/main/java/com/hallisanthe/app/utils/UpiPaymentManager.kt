package com.hallisanthe.app.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build

data class UpiApp(
    val name: String,
    val packageName: String,
    val icon: Drawable? = null
)

object UpiPaymentManager {

    private val KNOWN_UPI_PACKAGES = mapOf(
        "com.google.android.apps.nbu.paisa.user" to "Google Pay",
        "com.phonepe.app" to "PhonePe",
        "net.one97.paytm" to "Paytm",
        "in.org.npci.upiapp" to "BHIM",
        "com.amazon.mShop.android.shopping" to "Amazon Pay"
    )

    /**
     * Dynamically fetches all apps installed on the device that can handle UPI intents.
     */
    fun getInstalledUpiApps(context: Context): List<UpiApp> {
        val upiIntent = Intent(Intent.ACTION_VIEW)
        upiIntent.data = Uri.parse("upi://pay")
        
        val pm = context.packageManager
        val upiActivities = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            pm.queryIntentActivities(
                upiIntent, 
                PackageManager.ResolveInfoFlags.of(PackageManager.MATCH_DEFAULT_ONLY.toLong())
            )
        } else {
            @Suppress("DEPRECATION")
            pm.queryIntentActivities(upiIntent, PackageManager.MATCH_DEFAULT_ONLY)
        }

        return upiActivities.map { resolveInfo ->
            val packageName = resolveInfo.activityInfo.packageName
            val name = KNOWN_UPI_PACKAGES[packageName] ?: resolveInfo.loadLabel(pm).toString()
            val icon = resolveInfo.loadIcon(pm)
            UpiApp(name, packageName, icon)
        }.distinctBy { it.packageName }
    }

    /**
     * Creates a UPI payment intent with the standard upi://pay format.
     */
    fun createUpiIntent(
        upiId: String,
        name: String,
        transactionId: String,
        note: String,
        amount: String,
        packageName: String? = null
    ): Intent {
        val uri = Uri.Builder()
            .scheme("upi")
            .authority("pay")
            .appendQueryParameter("pa", upiId)
            .appendQueryParameter("pn", name)
            .appendQueryParameter("tr", transactionId)
            .appendQueryParameter("tn", note)
            .appendQueryParameter("am", amount)
            .appendQueryParameter("cu", "INR")
            .build()

        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = uri
        if (packageName != null) {
            intent.setPackage(packageName)
        }
        return intent
    }

    /**
     * Legacy check for a specific app by package name.
     * Note: This is less reliable than queryIntentActivities on Android 11+ without proper <queries>.
     */
    fun isAppInstalled(context: Context, packageName: String): Boolean {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                context.packageManager.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0))
            } else {
                @Suppress("DEPRECATION")
                context.packageManager.getPackageInfo(packageName, 0)
            }
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
}
