package presentation.ui.main.my_orders

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.devper.app.core.domain.constants.SHIPPING_ACTIVE
import com.devper.app.core.domain.constants.SHIPPING_FAILED
import com.devper.app.core.domain.constants.SHIPPING_SUCCESS
import business.domain.main.Order
import com.devper.app.core.design.component.AppImage
import com.devper.app.core.design.component.DefaultScreenUI
import com.devper.app.core.design.component.Spacer_8dp
import com.devper.app.core.design.component.noRippleClickable
import com.devper.app.core.design.theme.BorderColor
import com.devper.app.design.resources.Res
import com.devper.app.design.resources.arrow_down
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.ui.main.my_orders.view_model.MyOrdersEvent
import presentation.ui.main.my_orders.view_model.MyOrdersState
import com.devper.app.feature.utils.convertDate


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyOrdersScreen(state: MyOrdersState, events: (MyOrdersEvent) -> Unit, popup: () -> Unit) {

    val scope = rememberCoroutineScope()

    val tabList by remember {
        mutableStateOf(
            listOf(
                "Active",
                "Success",
                "Failed",
            )
        )
    }


    val pagerState = rememberPagerState { tabList.size }

    DefaultScreenUI(
        queue = state.errorQueue,
        onRemoveHeadFromQueue = { events(MyOrdersEvent.OnRemoveHeadFromQueue) },
        progressBarState = state.progressBarState,
        networkState = state.networkState,
        onTryAgain = { events(MyOrdersEvent.OnRetryNetwork) },
        titleToolbar = "My Orders",
        startIconToolbar = Icons.AutoMirrored.Filled.ArrowBack,
        onClickStartIconToolbar = popup
    ) {

        Column(modifier = Modifier.fillMaxSize()) {


            TabRow(modifier = Modifier.height(50.dp).fillMaxWidth(),
                selectedTabIndex = pagerState.currentPage,
                contentColor = Color.Transparent,
                containerColor = Color.Transparent,
                divider = {},
                indicator = { tabPositions ->
                    Box(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage])
                            .height(4.dp).padding(horizontal = 28.dp).background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = MaterialTheme.shapes.medium
                            )
                    )
                }) {
                tabList.forEachIndexed { index, _ ->
                    Tab(
                        unselectedContentColor = Color.Transparent,
                        selectedContentColor = Color.Transparent,
                        text = {
                            Text(
                                tabList[index],
                                style = MaterialTheme.typography.labelLarge,
                                color = if (pagerState.currentPage == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                            )
                        }, selected = pagerState.currentPage == index, onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        })
                }
            }


            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.Top
            ) { index ->
                Box(
                    contentAlignment = Alignment.TopCenter,
                    modifier = Modifier.fillMaxSize()
                ) {

                    when (index) {

                        SHIPPING_ACTIVE -> {
                            MyOrdersList(list = state.orders.filter { it.status == SHIPPING_ACTIVE })
                        }

                        SHIPPING_SUCCESS -> {
                            MyOrdersList(list = state.orders.filter { it.status == SHIPPING_SUCCESS })
                        }

                        SHIPPING_FAILED -> {
                            MyOrdersList(list = state.orders.filter { it.status == SHIPPING_FAILED })
                        }
                    }
                }
            }


        }
    }
}

@Composable
private fun MyOrdersList(list: List<Order>) {
    if (list.isEmpty()) {
        Text(
            "Nothing yet!",
            style = MaterialTheme.typography.titleLarge,
            color = BorderColor,
            modifier = Modifier.fillMaxSize().padding(top = 64.dp),
            textAlign = TextAlign.Center
        )
    }

    LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp)) {
        items(list, key = { it.createdAt }) {
            OrderBox(it)
        }
    }

}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun OrderBox(order: Order) {
    var isExpanded by remember { mutableStateOf(false) }

    val rotationState by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        animationSpec = tween(350, easing = LinearEasing)
    )


    Box(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                .border(1.dp, BorderColor, MaterialTheme.shapes.medium)
                .animateContentSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(order.createdAt.convertDate(), style = MaterialTheme.typography.bodyLarge)
                Icon(
                    painter = painterResource(Res.drawable.arrow_down),
                    null,
                    modifier = Modifier.size(35.dp).padding(4.dp).rotate(rotationState)
                        .noRippleClickable { isExpanded = !isExpanded })
            }
            Spacer_8dp()
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Promo Code", style = MaterialTheme.typography.bodyLarge)
                Text(order.code, style = MaterialTheme.typography.bodyMedium)
            }
            Spacer_8dp()

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {
                items(order.products) {
                    AppImage(
                        imageUrl = it.image,
                        contentDescription = null,
                        modifier = Modifier.size(55.dp).padding(horizontal = 4.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Spacer_8dp()

            if (isExpanded) {
                Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)) {
                    Column(modifier = Modifier.fillMaxWidth()) {

                        Row(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Amount", style = MaterialTheme.typography.bodyLarge)
                            Text(order.getAmount(), style = MaterialTheme.typography.bodyMedium)
                        }
                        Spacer_8dp()

                        Row(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Delivery Cost", style = MaterialTheme.typography.bodyLarge)
                            Text(
                                order.shippingType.getPrice(),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Spacer_8dp()

                        Row(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Delivery Type", style = MaterialTheme.typography.bodyLarge)
                            Text(
                                order.shippingType.title,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Spacer_8dp()


                        Column(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text("Address", style = MaterialTheme.typography.bodyLarge)
                            Text(
                                order.address.getShippingAddress(),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
            Spacer_8dp()

        }
    }
}
