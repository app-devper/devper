@file:OptIn(ExperimentalMaterial3Api::class)

package com.devper.app.feature.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devper.app.core.design.extension.formatDate
import com.devper.app.core.domain.model.product.Product
import kotlinx.coroutines.launch

@Composable
fun ProductDetailWidget(
    product: Product,
    onBack: () -> Unit,
    onEdit: () -> Unit,
    onClickEdit: () -> Unit
) {
    Column {
        TopTitleBar(title = product.name, onBack = onBack, action = "แก้ไข", onAction = onClickEdit)
        HorizontalDivider(thickness = 1.dp, color = Color.Gray)
        TabView(product = product, onEdit = onEdit)
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TabView(product: Product, onEdit: () -> Unit) {
    val tabs = listOf("ข้อมูลทั่วไป", "หน่วยนับ", "ราคา", "สต็อก", "ประวัติ")
    val pagerState = rememberPagerState(pageCount = { tabs.size })

    val coroutineScope = rememberCoroutineScope()

    Column {
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage])
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = { Text(title) }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.Top
        ) { page ->
            when (page) {
                0 -> ProductInfoTab(product = product)
                1 -> UnitsTab(product = product, onEdit = onEdit)
                2 -> PricesTab(product = product, onEdit = onEdit)
                3 -> StocksTab(product = product, onEdit = onEdit)
                4 -> HistoryTab()
            }
        }
    }
}

@Composable
fun ProductInfoTab(product: Product) {
    val status = if (product.status == "Inactive") "ไม่ใช้งาน" else "ใช้งาน"
    val colorStatus = if (product.status == "Inactive") Color.Red else Color.Green

    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            ListItem(title = "ประเภทสินค้า", subtitle = product.category)
            ListItem(title = "ชื่อสินค้า", subtitle = product.name)
            ListItem(title = "ชื่อสามัญทางยา", subtitle = "-")
            ListItem(title = "การแสดงข้อมูลสินค้า", subtitle = status, color = colorStatus)
            ListItem(title = "วันแจ้งเตือนก่อนวันหมดอายุ", subtitle = "ก่อน 240 วัน")
            ListItem(title = "อัตราภาษีสินค้า", subtitle = "ไม่มี VAT")
        }
    }
}

@Composable
fun ListItem(title: String, subtitle: String, color: Color = Color.Black) {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = title, fontSize = 16.sp, color = Color.Black)
            Text(text = subtitle, fontSize = 16.sp, color = color)
        }
        HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
    }
}

@Composable
fun UnitsTab(product: Product, onEdit: () -> Unit) {
    Column(modifier = Modifier.padding(8.dp)) {
        product.units.forEach { unit ->
            Card(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = unit.unit, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.weight(1f))
                        TextButton(onClick = { /* Edit unit */ }) {
                            Text(text = "แก้ไข", color = MaterialTheme.colorScheme.primary)
                        }
                    }
                    Text(text = "บาร์โค้ด: ${unit.barcode}", fontSize = 16.sp)
                    HorizontalDivider(thickness = 1.dp, color = Color.Gray)
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(horizontalAlignment = Alignment.Start) {
                            Text(text = "ขนาดบรรจุ", fontSize = 14.sp, color = Color.Gray)
                            Text(text = "${unit.size}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = "ต้นทุน", fontSize = 14.sp, color = Color.Gray)
                            Text(text = "฿${unit.costPrice}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = "ปริมาณ", fontSize = 14.sp, color = Color.Gray)
                            Text(text = "${unit.volume}${unit.volumeUnit}")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PricesTab(product: Product, onEdit: () -> Unit) {
    Column(modifier = Modifier.padding(8.dp)) {
        product.units.forEach { unit ->
            val prices = product.getProductPricesByUnitId(unit.id)
            Card(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = unit.unit, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.weight(1f))
                        TextButton(onClick = { /* Add price */ }) {
                            Text(text = "เพิ่มราคาขาย", color = MaterialTheme.colorScheme.primary)
                        }
                    }
                    Text(text = "ต้นทุนของสินค้า: ฿${unit.costPrice}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    HorizontalDivider(thickness = 1.dp, color = Color.Gray)
                    prices.forEach { price ->
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column {
                                Text(text = "ผูกกับประเภทลูกค้า", fontSize = 14.sp, color = Color.Gray)
                                Text(text = price.getCustomerTypeDisplay(), fontSize = 16.sp)
                            }
                            Text(text = "฿${price.price}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StocksTab(product: Product, onEdit: () -> Unit) {
    Column(modifier = Modifier.padding(8.dp)) {
        product.units.forEach { unit ->
            val stock = product.getProductStocksByUnitId(unit.id)
            val total = stock.sumOf { it.quantity }
            Card(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = unit.unit, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.weight(1f))
                        TextButton(onClick = { /* Add stock */ }) {
                            Text(text = "เพิ่มสต็อก", color = MaterialTheme.colorScheme.primary)
                        }
                    }
                    Text(text = "คงเหลือรวมทั้งหมด: $total ${unit.unit}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    HorizontalDivider(thickness = 1.dp, color = Color.Gray)
                    stock.forEach { s ->
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column {
                                Text(text = "นำเข้าเมื่อ", fontSize = 14.sp, color = Color.Gray)
                                Text(text = s.importDate.formatDate(), fontSize = 16.sp)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "วันหมดอายุ", fontSize = 14.sp, color = Color.Gray)
                                Text(text = s.expireDate.formatDate(), fontSize = 16.sp)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text(text = "จำนวนคงเหลือ", fontSize = 14.sp, color = Color.Gray)
                                Text(text = "${s.quantity} ${unit.unit}", fontSize = 16.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HistoryTab() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "ประวัติ", fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}
