package com.hallisanthe.app.ui.screens

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hallisanthe.app.ui.theme.*
import com.hallisanthe.app.utils.UpiApp
import com.hallisanthe.app.utils.UpiPaymentManager
import com.hallisanthe.app.viewmodel.CartViewModel
import com.hallisanthe.app.viewmodel.PaymentState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    cartViewModel: CartViewModel,
    onBack: () -> Unit,
    onPaymentSuccess: (String) -> Unit
) {
    val context = LocalContext.current
    val cartSummary by cartViewModel.cartSummary.collectAsState()
    val paymentState by cartViewModel.paymentState.collectAsState()
    
    var selectedPayment by remember { mutableStateOf(PaymentMethod.UPI) }
    var selectedUpiApp  by remember { mutableStateOf<UpiApp?>(null) }
    
    val upiLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val response = result.data?.getStringExtra("response") ?: ""
            handleUpiResponse(response, cartViewModel, context)
        } else {
            Toast.makeText(context, "Payment cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(paymentState) {
        if (paymentState is PaymentState.Success) {
            onPaymentSuccess((paymentState as PaymentState.Success).orderId)
            cartViewModel.resetPaymentState()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select Payment", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            Surface(tonalElevation = 8.dp, shadowElevation = 8.dp) {
                Button(
                    onClick = {
                        if (selectedPayment == PaymentMethod.COD) {
                            cartViewModel.checkoutAndCreateOrder(paymentMethod = "COD")
                        } else {
                            if (selectedUpiApp == null) {
                                Toast.makeText(context, "Please select a UPI app", Toast.LENGTH_SHORT).show()
                            } else {
                                val intent = UpiPaymentManager.createUpiIntent(
                                    upiId = "hallisanthe@upi",
                                    name = "Halli Santhe",
                                    transactionId = "TXN${System.currentTimeMillis()}",
                                    note = "Marketplace Payment",
                                    amount = String.format("%.2f", cartSummary.finalTotal),
                                    packageName = selectedUpiApp?.packageName
                                )
                                upiLauncher.launch(intent)
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().padding(16.dp).height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                    enabled = paymentState !is PaymentState.Processing
                ) {
                    if (paymentState is PaymentState.Processing) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White, strokeWidth = 2.dp)
                    } else {
                        Text("Pay ₹${String.format("%.2f", cartSummary.finalTotal)}", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            item {
                SectionTitle("Choose Payment Method")
                PaymentMethod.entries.forEach { method ->
                    PaymentMethodCard(
                        method = method,
                        isSelected = selectedPayment == method,
                        onClick = { selectedPayment = method }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            if (selectedPayment == PaymentMethod.UPI) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    SectionTitle("Select UPI App")
                    UpiPaymentManager.SUPPORTED_APPS.forEach { upiApp ->
                        val isInstalled = UpiPaymentManager.isAppInstalled(context, upiApp.packageName)
                        UpiAppRow(
                            upiApp = upiApp,
                            isSelected = selectedUpiApp == upiApp,
                            isInstalled = isInstalled,
                            onClick = {
                                if (isInstalled) selectedUpiApp = upiApp
                                else Toast.makeText(context, "App not installed", Toast.LENGTH_SHORT).show()
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text       = title,
        fontWeight = FontWeight.Bold,
        fontSize   = 16.sp,
        color      = PrimaryGreenDark,
        modifier   = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun PaymentMethodCard(method: PaymentMethod, isSelected: Boolean, onClick: () -> Unit) {
    val borderColor = if (isSelected) PrimaryGreen else Color.Transparent
    val backgroundColor = if (isSelected) PrimaryGreen.copy(alpha = 0.05f) else Color.White

    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() }.border(1.dp, borderColor, RoundedCornerShape(12.dp)),
        shape    = RoundedCornerShape(12.dp),
        colors   = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(method.icon, contentDescription = null, tint = if (isSelected) PrimaryGreen else TextSecondary)
            Spacer(modifier = Modifier.width(16.dp))
            Text(method.label, fontWeight = FontWeight.Bold, color = PrimaryGreenDark, modifier = Modifier.weight(1f))
            RadioButton(selected = isSelected, onClick = onClick, colors = RadioButtonDefaults.colors(selectedColor = PrimaryGreen))
        }
    }
}

@Composable
fun UpiAppRow(upiApp: UpiApp, isSelected: Boolean, isInstalled: Boolean, onClick: () -> Unit) {
    val borderColor = when {
        isSelected -> SecondaryOrange
        !isInstalled -> Color.LightGray.copy(alpha = 0.1f)
        else -> Color.LightGray.copy(alpha = 0.3f)
    }
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .border(if (isSelected) 2.dp else 1.dp, borderColor, RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(40.dp).clip(CircleShape).background(if (isInstalled) SurfaceLight else Color.LightGray.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            val emoji = when {
                upiApp.name.contains("Google", true) -> "G"
                upiApp.name.contains("PhonePe", true) -> "P"
                upiApp.name.contains("Paytm", true) -> "Py"
                else -> "U"
            }
            Text(emoji, fontWeight = FontWeight.Black, color = if (isInstalled) PrimaryGreenDark else Color.Gray)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(upiApp.name, fontWeight = FontWeight.SemiBold, color = if (isInstalled) PrimaryGreenDark else Color.Gray)
            if (!isInstalled) {
                Text("Not installed", fontSize = 10.sp, color = Color.Red)
            }
        }
        if (isSelected) {
            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SecondaryOrange)
        }
    }
}

private fun handleUpiResponse(response: String, viewModel: CartViewModel, context: Context) {
    val resMap = response.split("&").associate { 
        val parts = it.split("=")
        (parts.getOrNull(0) ?: "") to (parts.getOrNull(1) ?: "")
    }
    val status = resMap["Status"]?.lowercase() ?: ""
    if (status == "success") {
        viewModel.checkoutAndCreateOrder(paymentMethod = "UPI", transactionId = resMap["txnId"] ?: "", paymentStatus = "PAID")
    } else {
        Toast.makeText(context, "Payment Failed", Toast.LENGTH_SHORT).show()
    }
}
