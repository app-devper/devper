package com.devper.app.feature.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Balance
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devper.app.core.design.theme.DefaultTextFieldTheme
import com.devper.app.core.domain.model.product.Product
import com.devper.app.design.resources.Res
import com.devper.app.design.resources.search
import com.devper.app.feature.component.viewmodel.ProductsViewModel
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject


@Composable
fun ProductsScreen(
    onSelected: (Product) -> Unit,
    onMenu: () -> Unit,
    viewModel: ProductsViewModel
) {
    var searchQuery by remember { mutableStateOf("") }
    var sortBalance by remember { mutableStateOf(false) }

    LaunchedEffect(searchQuery, sortBalance) {
        viewModel.getProducts(searchQuery, sortBalance)
    }

    val products by viewModel.products.collectAsState(initial = emptyList())

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SearchTextField(
                searchQuery = searchQuery,
                onSearchQueryChange = { searchQuery = it },
                onClearSearch = {
                    searchQuery = ""
                    viewModel.getProducts(searchQuery, sortBalance)
                }
            )
            SortButton(
                sortBalance = sortBalance,
                onSortChange = {
                    sortBalance = !sortBalance
                    viewModel.getProducts(searchQuery, sortBalance)
                }
            )
            if (isMobile()) {
                IconButton(
                    onClick = onMenu,
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Icon(Icons.Default.Menu, contentDescription = "Menu")
                }
            }
        }
        Divider()
        ProductList(
            products = products,
            sortBalance = sortBalance,
            onSelected = onSelected
        )
    }
}

@Composable
fun SearchTextField(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onClearSearch: () -> Unit
) {
    TextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        modifier = Modifier
            .padding(8.dp),
        placeholder = { Text("ค้นหาสิ่งที่คุณต้องการ...") },
        leadingIcon = {
            Icon(
                painterResource(Res.drawable.search),
                contentDescription = "Search"
            )
        },
        trailingIcon = {
            IconButton(onClick = onClearSearch) {
                Icon(Icons.Default.Clear, contentDescription = "Clear")
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            onClearSearch()
        }),
        colors = DefaultTextFieldTheme(),
        shape = MaterialTheme.shapes.small,
    )
}

@Composable
fun SortButton(
    sortBalance: Boolean,
    onSortChange: () -> Unit
) {
    IconButton(onClick = onSortChange) {
        Icon(
            imageVector = if (sortBalance) Icons.AutoMirrored.Filled.Sort else Icons.Default.Balance,
            contentDescription = "Sort",
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductList(
    products: List<Product>,
    sortBalance: Boolean,
    onSelected: (Product) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(products) { product ->
            Column {
                ListItem(
                    text = {
                        Text(product.name)
                    },
                    secondaryText = {
                        Text(
                            text = getSubTitle(product, sortBalance),
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    },
                    trailing = {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowForwardIos,
                            contentDescription = "Select",
                            tint = Color.Gray,
                            modifier = Modifier.size(16.dp)
                        )
                    },
                    modifier = Modifier
                        .clickable { onSelected(product) }
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                )
                Divider()
            }
        }
    }
}

fun getSubTitle(product: Product, sortBalance: Boolean): String {
    return if (sortBalance) {
        "คงเหลือ ${product.getQuantity()} ${if (product.units.isNotEmpty()) product.units.first().unit else ""}"
    } else {
        if (product.units.isNotEmpty()) product.units.first().unit else ""
    }
}

fun isMobile(): Boolean {
    // Logic to determine if the device is mobile
    return true
}

@Composable
fun PreviewProductsScreen() {
    ProductsScreen(
        viewModel = koinInject(),
        onSelected = {},
        onMenu = {}
    )
}
