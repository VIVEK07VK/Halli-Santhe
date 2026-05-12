package com.hallisanthe.app.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hallisanthe.app.models.Category
import com.hallisanthe.app.ui.theme.*

@Composable
fun CategoryFilterRow(
    categories: List<Category>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(categories) { category ->
            val isSelected = category.name == selectedCategory
            
            val elevation by animateDpAsState(if (isSelected) 6.dp else 0.dp, label = "elevation")
            val borderWidth by animateDpAsState(if (isSelected) 1.5.dp else 0.dp, label = "border")

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(72.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { onCategorySelected(category.name) }
                    .padding(vertical = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .shadow(elevation, RoundedCornerShape(16.dp))
                        .background(
                            if (isSelected) PrimaryGreen.copy(alpha = 0.1f) else SurfaceLight,
                            RoundedCornerShape(16.dp)
                        )
                        .border(
                            width = borderWidth,
                            color = if (isSelected) PrimaryGreen else Color.Transparent,
                            shape = RoundedCornerShape(16.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = category.iconResOrUrl,
                        fontSize = 28.sp
                    )
                }
                
                Spacer(modifier = Modifier.height(6.dp))
                
                Text(
                    text = category.name,
                    fontSize = 10.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    color = if (isSelected) PrimaryGreenDark else TextPrimary,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    maxLines = 2,
                    lineHeight = 12.sp
                )
            }
        }
    }
}
