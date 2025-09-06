package com.devper.app.feature.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PaymentScreen(
    amount: Double,
    onCompleted: (Double, String) -> Unit,
    onError: () -> Unit,
) {
    var typeMode by remember { mutableStateOf("Cash") }
    var number by remember { mutableStateOf("") }
    var change by remember { mutableStateOf(0.0) }
    var isInput by remember { mutableStateOf(false) }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
    ) {
        Column(
            modifier = Modifier.weight(1f),
        ) {
            InputSection(
                title = "ยอดรับเงินรวม",
                amount = getNumberFormat(number),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                AmountSection(
                    title = "ยอดที่ต้องชำระ",
                    amount = "฿${formatDouble(amount)}",
                    modifier = Modifier.weight(1f),
                )
                Spacer(modifier = Modifier.width(16.dp))
                AmountChangeSection(
                    title = "เงินทอน",
                    change = change,
                    modifier = Modifier.weight(1f),
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Column(
                modifier = Modifier.weight(1f),
            ) {
                PaymentOption(
                    title = "เงินสด",
                    iconData = Icons.Default.AttachMoney,
                    isSelected = typeMode == "Cash",
                ) {
                    typeMode = "Cash"
                }
                Spacer(modifier = Modifier.height(16.dp))
                PaymentOption(
                    title = "พร้อมเพย์",
                    iconData = Icons.Default.QrCode2,
                    isSelected = typeMode == "PromptPay",
                ) {
                    typeMode = "PromptPay"
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(2f),
            ) {
                InputNumber(
                    onDone = {
                        if (!isInput) {
                            onCompleted(amount, typeMode)
                        } else if (isInput && change >= 0) {
                            onCompleted(number.toDouble(), typeMode)
                        } else {
                            onError()
                        }
                    },
                    onChange = {
                        calculate(it, amount, onChange = { c -> change = c })
                        isInput = true
                        number = it
                    },
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (!isInput) {
                            onCompleted(amount, typeMode)
                        } else if (isInput && change >= 0) {
                            onCompleted(number.toDouble(), typeMode)
                        } else {
                            onError()
                        }
                    },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(64.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)),
                ) {
                    Text(
                        text = getPaymentBtn(change, isInput),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}

@Composable
fun InputSection(
    title: String,
    amount: String,
) {
    Box(
        modifier =
            Modifier
                .height(100.dp)
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(8.dp))
                .padding(16.dp),
        contentAlignment = Alignment.CenterEnd,
    ) {
        Column(horizontalAlignment = Alignment.End) {
            Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(text = "฿$amount", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun AmountSection(
    title: String,
    amount: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .height(80.dp)
                .background(Color.Blue, RoundedCornerShape(8.dp))
                .padding(vertical = 8.dp, horizontal = 16.dp),
        contentAlignment = Alignment.CenterEnd,
    ) {
        Column(horizontalAlignment = Alignment.End) {
            Text(text = title, fontSize = 20.sp, color = Color.White)
            Text(text = amount, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

@Composable
fun AmountChangeSection(
    title: String,
    change: Double,
    modifier: Modifier = Modifier,
) {
    val changeText = if (change <= 0) "" else "฿${formatDouble(change)}"
    Box(
        modifier =
            modifier
                .height(80.dp)
                .background(Color(0xFFFFA500), RoundedCornerShape(8.dp))
                .padding(vertical = 8.dp, horizontal = 16.dp),
        contentAlignment = Alignment.CenterEnd,
    ) {
        Column(horizontalAlignment = Alignment.End) {
            Text(text = title, fontSize = 20.sp, color = Color.White)
            Text(text = changeText, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

@Composable
fun PaymentOption(
    title: String,
    iconData: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val borderColor = if (isSelected) Color.Green else Color(0xFFEEEEEE)
    val iconColor = if (isSelected) Color.Green else Color.Gray
    val textColor = if (isSelected) Color.Green else Color.Gray

    Box(
        modifier =
            Modifier
                .height(80.dp)
                .background(Color.White, RoundedCornerShape(8.dp))
                .border(2.dp, borderColor, RoundedCornerShape(8.dp))
                .clickable { onClick() }
                .padding(16.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(iconData, contentDescription = null, tint = iconColor)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = title, fontSize = 16.sp, color = textColor, fontWeight = FontWeight.Bold)
            }
            Icon(
                imageVector = if (isSelected) Icons.Default.CheckCircle else Icons.Default.Circle,
                contentDescription = null,
                tint = if (isSelected) Color.Green else Color(0xFFEEEEEE),
            )
        }
    }
}

fun formatDouble(value: Double): String = value.toString()

fun calculate(
    value: String,
    amount: Double,
    onChange: (Double) -> Unit,
) {
    if (value.isEmpty() || value == "." || value == "0") {
        onChange(0 - amount)
    } else {
        onChange(value.toDouble() - amount)
    }
}

fun getNumberFormat(value: String): String {
    if (value.isEmpty()) return ""
    if (value == ".") return "."
    return value.toDoubleOrNull()?.let { formatDouble(it) } ?: value
}

fun getPaymentBtn(
    change: Double,
    isInput: Boolean,
): String =
    when {
        !isInput && change == 0.0 -> "รับชำระพอดี ไม่มีเงินทอน"
        isInput && change == 0.0 -> "รับชำระพอดี ไม่มีเงินทอน"
        change < 0.0 -> "โปรดระบุยอดที่ต้องชำระ"
        else -> "เงินทอน ฿${formatDouble(change)}"
    }

@Composable
fun PreviewPaymentScreen() {
    PaymentScreen(amount = 100.0, onCompleted = { _, _ -> }, onError = {})
}
