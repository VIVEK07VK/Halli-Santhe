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
import com.hallisanthe.app.ui.components.UpiAppSelector
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
    
    val installedUpiApps = remember { UpiPaymentManager.getInstalledUpiApps(context) }
    
    // Auto-select first available app if none selected
    LaunchedEffect(installedUpiApps) {
        if (selectedUpiApp == null && installedUpiApps.isNotEmpty()) {
            selectedUpiApp = installedUpiApps.first()
        }
    }
    
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
                title = { Text("Select Payment", fontWeight = FontWeight.Bold, color = Color(0xFF1B3A2D)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color(0xFF1B3A2D))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFF9F4EE))
            )
        },
        bottomBar = {
            Surface(
                color = Color.White,
                tonalElevation = 8.dp,
                shadowElevation = 16.dp,
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
            ) {
                Column(
                    modifier = Modifier
                        .navigationBarsPadding() // CRITICAL: Prevents hiding behind system bar
                        .padding(24.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Total Amount", color = TextSecondary, fontSize = 14.sp)
                        Text(
                            "₹${String.format("%.2f", cartSummary.finalTotal)}",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 22.sp,
                            color = PrimaryGreenDark
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
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
                        modifier = Modifier.fillMaxWidth().height(60.dp),
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen), // Theme consistent
                        enabled = paymentState !is PaymentState.Processing
                    ) {
                        if (paymentState is PaymentState.Processing) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White, strokeWidth = 2.dp)
                        } else {
                            Text(
                                if (selectedPayment == PaymentMethod.COD) "PLACE ORDER" else "PAY NOW",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        },
        containerColor = Color(0xFFF9F4EE)
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp)
        ) {
            item {
                SectionTitle("Payment Methods")
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(PaymentMethod.entries.size) { index ->
                val method = PaymentMethod.entries[index]
                PaymentMethodCard(
                    method = method,
                    isSelected = selectedPayment == method,
                    onClick = { selectedPayment = method }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            if (selectedPayment == PaymentMethod.UPI) {
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    SectionTitle("Select UPI Application")
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    UpiAppSelector(
                        installedApps = installedUpiApps,
                        selectedApp = selectedUpiApp,
                        onAppSelected = { selectedUpiApp = it }
                    )
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
        fontSize   = 15.sp,
        color      = Color(0xFF1B3A2D),
        modifier   = Modifier.padding(start = 4.dp)
    )
}

@Composable
fun PaymentMethodCard(method: PaymentMethod, isSelected: Boolean, onClick: () -> Unit) {
    val borderColor = if (isSelected) Color(0xFF2E7D32) else Color.Transparent
    val backgroundColor = if (isSelected) Color(0xFF2E7D32).copy(alpha = 0.05f) else Color.White

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .border(if (isSelected) 1.5.dp else 1.dp, if (isSelected) Color(0xFF2E7D32) else Color.White, RoundedCornerShape(16.dp)),
        shape    = RoundedCornerShape(16.dp),
        colors   = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(if (isSelected) 0.dp else 2.dp)
    ) {
        Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(40.dp).background(Color(0xFFF9F4EE), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(method.icon, contentDescription = null, tint = Color(0xFF2E7D32), modifier = Modifier.size(24.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(method.label, fontWeight = FontWeight.Bold, color = Color(0xFF1B3A2D), fontSize = 15.sp)
                Text(if (method == PaymentMethod.UPI) "Pay securely via your favorite apps" else "Pay when you receive the order", fontSize = 11.sp, color = Color.Gray)
            }
            RadioButton(
                selected = isSelected,
                onClick = onClick,
                colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF2E7D32))
            )
        }
    }
}

private fun handleUpiResponse(response: String, viewModel: CartViewModel, context: Context) {
    // Standard UPI response parsing
    // Example: txnId=ABCD123&responseCode=00&Status=SUCCESS&txnRef=123456
    val resMap = response.split("&").associate { 
        val parts = it.split("=")
        (parts.getOrNull(0) ?: "") to (parts.getOrNull(1) ?: "")
    }
    
    val status = resMap["Status"]?.uppercase() ?: ""
    val responseCode = resMap["responseCode"] ?: ""
    
    when {
        status == "SUCCESS" || responseCode == "00" -> {
            val txnId = resMap["txnId"] ?: resMap["tr"] ?: "TXN${System.currentTimeMillis()}"
            viewModel.checkoutAndCreateOrder(
                paymentMethod = "UPI", 
                transactionId = txnId, 
                paymentStatus = "PAID"
            )
            Toast.makeText(context, "Payment Successful", Toast.LENGTH_SHORT).show()
        }
        status == "FAILURE" -> {
            Toast.makeText(context, "Payment Failed", Toast.LENGTH_SHORT).show()
        }
        status == "CANCEL" || status == "CANCELLED" -> {
            Toast.makeText(context, "Payment Cancelled", Toast.LENGTH_SHORT).show()
        }
        else -> {
            // Some apps might not return "SUCCESS" but still be successful if responseCode is 00
            if (responseCode == "00") {
                viewModel.checkoutAndCreateOrder(
                    paymentMethod = "UPI", 
                    transactionId = resMap["txnId"] ?: "TXN${System.currentTimeMillis()}", 
                    paymentStatus = "PAID"
                )
            } else {
                Toast.makeText(context, "Payment status: $status", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
