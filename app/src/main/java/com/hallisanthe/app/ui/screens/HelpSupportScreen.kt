package com.hallisanthe.app.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hallisanthe.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpSupportScreen(
    onBack: () -> Unit
) {
    val context = LocalContext.current
    
    val faqs = listOf(
        FAQItem("How to upload products?", "Go to Seller Dashboard and click the 'Add Product' button. Fill in the details and upload an image."),
        FAQItem("How to manage orders?", "Click on 'Manage Orders' in your profile or dashboard. You can update the status (Accepted, Preparing, Out for Delivery, etc.) of each order there."),
        FAQItem("How are earnings calculated?", "Earnings are calculated after deducting a platform commission (5%) from the product price. Delivery fees are added separately if applicable."),
        FAQItem("How to update business address?", "Go to Seller Profile > Business Address. You can add, edit, or delete your pickup locations there."),
        FAQItem("Why was my product rejected?", "Products can be rejected if the image is unclear, the description is inappropriate, or the category is incorrect.")
    )

    var reportText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Support Hub", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color(0xFFF9F4EE)
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header Section
            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.SupportAgent, contentDescription = null, tint = Color(0xFF2E7D32), modifier = Modifier.size(64.dp))
                    Spacer(Modifier.height(8.dp))
                    Text("How can we help you?", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color(0xFF1B3A2D))
                    Text("Search FAQs or contact our team directly", fontSize = 14.sp, color = Color.Gray)
                }
            }

            // FAQ Section
            item {
                Text("Frequently Asked Questions", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF1B3A2D))
            }

            items(faqs) { faq ->
                FAQCard(faq)
            }

            // Contact Section
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text("Contact Support", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF1B3A2D))
                Spacer(modifier = Modifier.height(12.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    ContactCard(
                        modifier = Modifier.weight(1f),
                        title = "WhatsApp",
                        icon = Icons.Default.Chat,
                        color = Color(0xFF25D366),
                        onClick = { openWhatsApp(context) }
                    )
                    ContactCard(
                        modifier = Modifier.weight(1f),
                        title = "Call Us",
                        icon = Icons.Default.Call,
                        color = Color(0xFF2E7D32),
                        onClick = { openDialer(context) }
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                ContactCard(
                    modifier = Modifier.fillMaxWidth(),
                    title = "Email Support",
                    icon = Icons.Default.Email,
                    color = Color(0xFFC0533A),
                    onClick = { openEmail(context) }
                )
            }

            // Report Issue Form
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Report an Issue", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF1B3A2D))
                        Spacer(Modifier.height(12.dp))
                        OutlinedTextField(
                            value = reportText,
                            onValueChange = { reportText = it },
                            placeholder = { Text("Describe your problem here...", fontSize = 14.sp) },
                            modifier = Modifier.fillMaxWidth().height(100.dp),
                            shape = RoundedCornerShape(12.dp)
                        )
                        Spacer(Modifier.height(16.dp))
                        Button(
                            onClick = { 
                                // In real app, send to Firebase
                                reportText = ""
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
                        ) {
                            Text("Submit Report")
                        }
                    }
                }
            }

            // Troubleshooting
            item {
                Text("App Troubleshooting", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF1B3A2D))
                Spacer(Modifier.height(8.dp))
                TroubleshootItem("App is slow?", "Try clearing the app cache in your phone settings.")
                TroubleshootItem("Images not loading?", "Check your internet connection or restart the app.")
                TroubleshootItem("Payment failed?", "Ensure your UPI app is updated and has enough balance.")
                Spacer(Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun TroubleshootItem(title: String, description: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(title, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Color(0xFF2E7D32))
        Text(description, fontSize = 13.sp, color = Color.Gray)
    }
}

data class FAQItem(val question: String, val answer: String)

@Composable
fun FAQCard(faq: FAQItem) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier.fillMaxWidth().clickable { expanded = !expanded },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(faq.question, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f), fontSize = 14.sp)
                Icon(
                    if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = Color.Gray
                )
            }
            AnimatedVisibility(visible = expanded) {
                Column {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(faq.answer, fontSize = 13.sp, color = Color.Gray)
                }
            }
        }
    }
}

@Composable
fun ContactCard(modifier: Modifier, title: String, icon: ImageVector, color: Color, onClick: () -> Unit) {
    Card(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.2f))
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.height(4.dp))
            Text(title, fontWeight = FontWeight.Bold, color = color, fontSize = 13.sp)
        }
    }
}

private fun openDialer(context: Context) {
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:+919876543210")
    }
    context.startActivity(intent)
}

private fun openWhatsApp(context: Context) {
    val number = "+919876543210"
    val uri = Uri.parse("https://api.whatsapp.com/send?phone=$number&text=Hello Halli-Santhe Support")
    val intent = Intent(Intent.ACTION_VIEW, uri)
    try {
        context.startActivity(intent)
    } catch (e: Exception) {}
}

private fun openEmail(context: Context) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:support@hallisanthe.com")
        putExtra(Intent.EXTRA_SUBJECT, "Support Request - Halli-Santhe")
    }
    try {
        context.startActivity(intent)
    } catch (e: Exception) {}
}
