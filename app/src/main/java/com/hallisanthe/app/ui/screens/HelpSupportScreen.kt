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
        FAQItem("How to upload products?", "Go to Seller Dashboard and click the '+' or 'Add Product' button. Fill in the details and upload an image."),
        FAQItem("How to manage orders?", "Click on 'My Orders' in your profile or dashboard. You can update the status of each order there."),
        FAQItem("How are earnings calculated?", "Earnings are calculated after deducting a small platform commission (5%) from the product price."),
        FAQItem("How to update stock?", "Go to your product list on the dashboard, click the inventory icon on a product card, and enter the new quantity.")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Help & Support", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text("Frequently Asked Questions", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = PrimaryGreenDark)
            }

            items(faqs) { faq ->
                FAQCard(faq)
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Contact Us", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = PrimaryGreenDark)
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
                        title = "Email Support",
                        icon = Icons.Default.Email,
                        color = PrimaryGreen,
                        onClick = { openEmail(context) }
                    )
                }
            }
        }
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
                Text(faq.question, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                Icon(
                    if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null
                )
            }
            AnimatedVisibility(visible = expanded) {
                Column {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(faq.answer, fontSize = 14.sp, color = TextSecondary)
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
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(title, fontWeight = FontWeight.Bold, color = color, fontSize = 14.sp)
        }
    }
}

private fun openWhatsApp(context: Context) {
    val number = "+919876543210" // Example support number
    val uri = Uri.parse("https://api.whatsapp.com/send?phone=$number&text=Hello Halli-Santhe Support")
    val intent = Intent(Intent.ACTION_VIEW, uri)
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        // WhatsApp not installed
    }
}

private fun openEmail(context: Context) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:support@hallisanthe.com")
        putExtra(Intent.EXTRA_SUBJECT, "Support Request - Halli-Santhe")
    }
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        // No email app
    }
}
