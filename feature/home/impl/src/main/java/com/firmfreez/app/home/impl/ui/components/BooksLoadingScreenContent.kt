package com.firmfreez.app.home.impl.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.firmfreez.app.common.ui_components.loader.verticalShimmerBackground
import com.firmfreez.app.core.style_compose.AppTheme
import com.firmfreez.app.core.style_compose.dimensions.LocalCornerRadii
import com.firmfreez.app.core.style_compose.dimensions.LocalSize
import com.firmfreez.app.core.style_compose.dimensions.LocalSpacing

@Composable
internal fun BooksLoadingScreenContent(
    modifier: Modifier = Modifier,
) {
    val spacing = LocalSpacing.current

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = spacing.paddingX4,
            end = spacing.paddingX4,
            top = spacing.paddingX2,
            bottom = spacing.paddingX4,
        ),
        verticalArrangement = Arrangement.spacedBy(spacing.paddingX3),
    ) {
        items(List(6) { it }) {
            BookItemShimmer()
        }
    }
}

@Composable
private fun BookItemShimmer(
    modifier: Modifier = Modifier,
) {
    val spacing = LocalSpacing.current
    val size = LocalSize.current
    val cornerRadii = LocalCornerRadii.current

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(cornerRadii.cornersX4),
        color = MaterialTheme.colorScheme.surface,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.paddingX4),
        ) {
            Box(
                modifier = Modifier
                    .width(size.sizeX16)
                    .height(size.sizeX24)
                    .clip(RoundedCornerShape(cornerRadii.cornersX3))
                    .verticalShimmerBackground()
            )

            androidx.compose.foundation.layout.Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = spacing.paddingX4),
                verticalArrangement = Arrangement.Center,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.72f)
                        .height(size.sizeX5)
                        .clip(RoundedCornerShape(cornerRadii.cornersX3))
                        .verticalShimmerBackground()
                )

                Box(
                    modifier = Modifier
                        .padding(top = spacing.paddingX2)
                        .fillMaxWidth(0.48f)
                        .height(size.sizeX4)
                        .clip(RoundedCornerShape(cornerRadii.cornersX3))
                        .verticalShimmerBackground()
                )

                Box(
                    modifier = Modifier
                        .padding(top = spacing.paddingX2)
                        .fillMaxWidth(0.34f)
                        .height(size.sizeX3)
                        .clip(RoundedCornerShape(cornerRadii.cornersX3))
                        .verticalShimmerBackground()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BooksLoadingScreenContentPreview() =
    AppTheme {
        BooksLoadingScreenContent()
    }