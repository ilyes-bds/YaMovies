package com.bdl.example.yamovies.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RatingStarsItem(rating: Float, modifier: Modifier = Modifier, starCount: Int = 5) {
    Row(modifier = modifier) {
        val filledStars = (rating * starCount).toInt()
        for (i in 1..starCount) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = if (i <= filledStars) Color.Yellow else Color.Gray,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}
