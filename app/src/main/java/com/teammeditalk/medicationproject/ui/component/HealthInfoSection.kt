package com.teammeditalk.medicationproject.ui.component

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.ui.Modifier

fun LazyListScope.healthInfoSection(
    modifier: Modifier,
    sleepDuration: String,
    stepCount: Int,
) {
    item {
        InfoRow(modifier, "연령", "25세")
        InfoRow(modifier, "체중", "45kg")
        InfoRow(modifier, "마이 약국", "")
        InfoRow(modifier, "수면 시간", sleepDuration)
        InfoRow(modifier, "걸음 수", stepCount.toString() + "걸음")
    }
}
