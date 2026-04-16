package com.firmfreez.app.reader.impl.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.firmfreez.app.core.style_compose.AppTheme
import com.firmfreez.app.core.style_compose.dimensions.LocalCornerRadii
import com.firmfreez.app.core.style_compose.dimensions.LocalSpacing
import com.firmfreez.app.reader.impl.R

@Composable
internal fun ControlOverlay(
    currentPage: Int?,
    totalPages: Int?,
    isOverlayActive: Boolean,
    canGoBackward: Boolean,
    canGoForward: Boolean,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    onBackClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
) {
    val spacing = LocalSpacing.current

    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        AnimatedVisibility(
            visible = isOverlayActive,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(
                    top = paddingValues.calculateTopPadding() + spacing.paddingX3,
                    start = spacing.paddingX4,
                ),
        ) {
            TopBackButton(
                onBackClick = onBackClick,
            )
        }


        BottomControlsBar(
            currentPage = currentPage,
            totalPages = totalPages,
            isOverlayActive = isOverlayActive,
            canGoBackward = canGoBackward,
            canGoForward = canGoForward,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(
                    bottom = paddingValues.calculateBottomPadding() + spacing.paddingX3,
                    start = spacing.paddingX4,
                    end = spacing.paddingX4,
                ),
            onPreviousClick = onPreviousClick,
            onNextClick = onNextClick,
        )
    }
}

@Composable
private fun TopBackButton(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    FloatingActionButton(
        onClick = onBackClick,
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            focusedElevation = 0.dp,
            hoveredElevation = 0.dp,
        )
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
            contentDescription = stringResource(R.string.reader_control_overlay_back),
        )
    }
}

@Composable
private fun BottomControlsBar(
    currentPage: Int?,
    totalPages: Int?,
    isOverlayActive: Boolean,
    canGoBackward: Boolean,
    canGoForward: Boolean,
    modifier: Modifier = Modifier,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
) {
    val corners = LocalCornerRadii.current
    val spacing = LocalSpacing.current

    Surface(
        modifier = modifier.alpha(if (isOverlayActive) 1f else 0.32f),
        shape = RoundedCornerShape(corners.cornersX4),
        color = MaterialTheme.colorScheme.surface.copy(
            alpha = if (isOverlayActive) 0.96f else 0.82f,
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = spacing.paddingX3,
                    vertical = spacing.paddingX2,
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            NavFab(
                visible = canGoBackward,
                icon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = stringResource(R.string.reader_control_overlay_previous),
                    )
                },
                onClick = onPreviousClick,
            )

            Text(
                text = buildPageText(
                    currentPage = currentPage,
                    totalPages = totalPages,
                ),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )

            NavFab(
                visible = canGoForward,
                icon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
                        contentDescription = stringResource(R.string.reader_control_overlay_next),
                    )
                },
                onClick = onNextClick,
            )
        }
    }
}

@Composable
private fun NavFab(
    visible: Boolean,
    icon: @Composable () -> Unit,
    onClick: () -> Unit,
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = Modifier.alpha(if (visible) 1f else 0f),
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            focusedElevation = 0.dp,
            hoveredElevation = 0.dp,
        )
    ) {
        icon()
    }
}

private fun buildPageText(
    currentPage: Int?,
    totalPages: Int?,
): String {
    return if (currentPage != null && totalPages != null && totalPages > 0) {
        "$currentPage / $totalPages"
    } else {
        "— / —"
    }
}

@Preview(name = "Active", showBackground = true)
@Composable
private fun ControlOverlayActivePreview() {
    AppTheme {
        ControlOverlay(
            currentPage = 12,
            totalPages = 248,
            isOverlayActive = true,
            canGoBackward = true,
            canGoForward = true,
            onBackClick = {},
            onPreviousClick = {},
            onNextClick = {},
        )
    }
}