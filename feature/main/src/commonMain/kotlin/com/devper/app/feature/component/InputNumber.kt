package com.devper.app.feature.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Backspace
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InputNumber(
    onChange: (String) -> Unit,
    onDone: (String) -> Unit,
    maxLength: Int = 7,
    maxDigit: Int = 2,
    isShowDot: Boolean = true,
) {
    var number by remember { mutableStateOf("") }

    fun input(num: String) {
        if (number == "0" && num != ".") return
        if (number.contains(".")) {
            val split = number.split(".")
            if (split[1].length == maxDigit || num == ".") return
        } else if (number.length == maxLength && num != ".") {
            return
        }
        number += num
        onChange(number)
    }

    fun delete() {
        if (number.isNotEmpty()) {
            number = number.dropLast(1)
            onChange(number)
        }
    }

    fun handleKey(keyEvent: KeyEvent) {
        if (keyEvent.type == KeyEventType.KeyDown) {
            when (keyEvent.key) {
                Key.Backspace -> delete()
                Key.NumPad1, Key.One -> input("1")
                Key.NumPad2, Key.Two -> input("2")
                Key.NumPad3, Key.Three -> input("3")
                Key.NumPad4, Key.Four -> input("4")
                Key.NumPad5, Key.Five -> input("5")
                Key.NumPad6, Key.Six -> input("6")
                Key.NumPad7, Key.Seven -> input("7")
                Key.NumPad8, Key.Eight -> input("8")
                Key.NumPad9, Key.Nine -> input("9")
                Key.NumPad0, Key.Zero -> input("0")
                Key.Period -> if (isShowDot) input(".")
                Key.Enter -> onDone(number)
            }
        }
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Color.White)
                .onKeyEvent { keyEvent ->
                    handleKey(keyEvent)
                    true
                },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DividerLine()
        NumberRow("1", "2", "3", onClick = { input(it) })
        DividerLine()
        NumberRow("4", "5", "6", onClick = { input(it) })
        DividerLine()
        NumberRow("7", "8", "9", onClick = { input(it) })
        DividerLine()
        SpecialRow(
            isShowDot = isShowDot,
            onInput = { input(it) },
            onDelete = { delete() },
        )
        DividerLine()
    }
}

@Composable
fun NumberRow(
    num1: String,
    num2: String,
    num3: String,
    onClick: (String) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        NumberButton(num1, onClick)
        NumberButton(num2, onClick)
        NumberButton(num3, onClick)
    }
}

@Composable
fun SpecialRow(
    isShowDot: Boolean,
    onInput: (String) -> Unit,
    onDelete: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        if (isShowDot) {
            NumberButton(".", onInput)
        } else {
            Spacer(modifier = Modifier.width(56.dp))
        }
        NumberButton("0", onInput)
        IconButton(
            modifier = Modifier.size(56.dp),
            onClick = {
                onDelete()
            },
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Backspace,
                contentDescription = "Backspace",
            )
        }
    }
}

@Composable
fun NumberButton(
    number: String,
    onClick: (String) -> Unit,
) {
    TextButton(
        onClick = { onClick(number) },
        modifier = Modifier.size(56.dp),
    ) {
        Text(
            text = number,
            fontSize = 24.sp,
            color = Color.Black,
        )
    }
}

@Composable
fun DividerLine() {
    HorizontalDivider(
        Modifier.fillMaxWidth(),
        1.dp,
        Color.Gray,
    )
}

@Preview
@Composable
fun PreviewInputNumber() {
    InputNumber(
        onChange = { println("Current value: $it") },
        onDone = { println("Final value: $it") },
        maxLength = 7,
        maxDigit = 2,
        isShowDot = true,
    )
}
