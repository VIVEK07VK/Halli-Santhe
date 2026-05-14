package com.hallisanthe.app.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hallisanthe.app.ui.theme.*

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    leadingIcon: ImageVector? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isPassword: Boolean = false,
    visualTransformation: androidx.compose.ui.text.input.VisualTransformation = androidx.compose.ui.text.input.VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    error: String? = null,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    readOnly: Boolean = false
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            label = { 
                Text(
                    text = label,
                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                ) 
            },
            placeholder = placeholder?.let { 
                { 
                    Text(
                        text = it,
                        style = TextStyle(
                            color = TextFieldPlaceholder,
                            fontSize = 14.sp
                        )
                    ) 
                } 
            },
            leadingIcon = leadingIcon?.let {
                { Icon(it, contentDescription = null, tint = PrimaryGreen) }
            },
            trailingIcon = trailingIcon,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            isError = error != null,
            singleLine = singleLine,
            enabled = enabled,
            readOnly = readOnly,
            shape = RoundedCornerShape(12.dp),
            textStyle = TextStyle(
                color = TextFieldText,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = TextFieldText,
                unfocusedTextColor = TextFieldText,
                disabledTextColor = TextFieldText.copy(alpha = 0.6f),
                errorTextColor = Color.Red,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                cursorColor = CursorColor,
                focusedBorderColor = TextFieldFocusedBorder,
                unfocusedBorderColor = TextFieldUnfocusedBorder,
                disabledBorderColor = TextFieldUnfocusedBorder.copy(alpha = 0.5f),
                errorBorderColor = Color.Red,
                focusedLabelColor = TextFieldFocusedBorder,
                unfocusedLabelColor = TextFieldPlaceholder,
                disabledLabelColor = TextFieldPlaceholder.copy(alpha = 0.5f),
                errorLabelColor = Color.Red,
                focusedPlaceholderColor = TextFieldPlaceholder,
                unfocusedPlaceholderColor = TextFieldPlaceholder,
                disabledPlaceholderColor = TextFieldPlaceholder.copy(alpha = 0.5f),
                errorPlaceholderColor = TextFieldPlaceholder,
                focusedLeadingIconColor = PrimaryGreen,
                unfocusedLeadingIconColor = TextFieldPlaceholder,
                focusedTrailingIconColor = PrimaryGreen,
                unfocusedTrailingIconColor = TextFieldPlaceholder
            )
        )
        if (error != null) {
            Text(
                text = error,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}
