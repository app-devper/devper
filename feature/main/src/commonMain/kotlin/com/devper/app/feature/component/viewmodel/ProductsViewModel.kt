package com.devper.app.feature.component.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devper.app.core.common.Result
import com.devper.app.core.domain.model.product.Product
import com.devper.app.core.domain.usecases.GetProductsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private var _products = MutableStateFlow(listOf<Product>())
    val products: StateFlow<List<Product>> = _products
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = listOf()
        )

    fun getProducts(text: String, sortBalance: Boolean) {
        viewModelScope.launch {
            when (val result = getProductsUseCase(Unit)) {
                is Result.Success -> {
                    onListProducts(text, result.data, sortBalance)
                }

                is Result.Error -> {

                }
            }
        }
    }

    private fun onListProducts(text: String, data: List<Product>, sortBalance: Boolean) {
        val items = mutableListOf<Product>()
        items.addAll(data)
        val result = searchProduct(text, items)
        if (sortBalance) {
            _products.value = result.sortedBy { it.getQuantity() }
        } else {
            _products.value = result
        }
    }

    private fun searchProduct(param: String, data: List<Product>): List<Product> {
        if (param.isEmpty()) {
            return data
        } else {
            val filtered = mutableListOf<Product>()
            for (item in data) {
                if (item.name.lowercase().contains(param.lowercase()) || item.units.any { it.barcode.contains(param) }) {
                    filtered.add(item)
                }
            }
            return filtered
        }
    }

}