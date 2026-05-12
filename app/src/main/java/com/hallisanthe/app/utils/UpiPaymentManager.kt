package com.hallisanthe.app.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri

data class UpiApp(
    val name: String,
    val packageName: String,
    val iconResId: Int? = null // For real icons, use actual drawable IDs
)

object UpiPaymentManager {

    val SUPPORTED_APPS = listOf(
        UpiApp("Google Pay", "com.google.android.apps.nbu.paisa.user"),
        UpiApp("PhonePe", "com.phonepe.app"),
        UpiApp("Paytm", "net.one97.paytm"),
        UpiApp("BHIM UPI", "in.org.npci.upiapp")
    )

    fun isAppInstalled(context: Context, packageName: String): Boolean {
        return try {
            context.packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    fun getInstalledUpiApps(context: Context): List<UpiApp> {
        return SUPPORTED_APPS.filter { isAppInstalled(context, it.packageName) }
    }

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
}
