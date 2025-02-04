package com.teammeditalk.medicationproject.ui.order

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.teammeditalk.medicationproject.ui.cart.CartViewModel
import com.teammeditalk.medicationproject.ui.cart.OrderResponse

@Suppress("ktlint:standard:function-naming")
@Composable
fun OrderHistoryScreen(viewModel: CartViewModel) {
    LaunchedEffect(Unit) {
        viewModel.getOrderList()
    }
    val orderDrugList = viewModel.orderDrugList.collectAsState().value

    LazyColumn {
        items(orderDrugList) { order ->
            OrderItem(order)
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun OrderItem(orderItem: OrderResponse) {
    Surface(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)),
        color = MaterialTheme.colorScheme.surface,
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                Modifier.padding(5.dp),
            ) {
                Text(
                    text = orderItem.orderDate,
                )
                Text(
                    maxLines = 2,
                    text = orderItem.orderDrugList,
                )
            }
        }
    }
}
