package com.firmfreez.app.reader.impl.ui.components

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.compose.AndroidFragment
import com.firmfreez.app.core.style_compose.AppTheme
import com.firmfreez.app.core.style_compose.dimensions.LocalSize
import com.firmfreez.app.core.style_compose.dimensions.LocalSpacing
import com.firmfreez.app.reader.impl.R
import com.firmfreez.app.reader.impl.ui.fragments.EpubReaderFragment
import com.firmfreez.app.reader.impl.ui.models.Action
import com.firmfreez.app.reader.impl.ui.models.UiState

@Composable
internal fun ScreenContent(
    uiState: UiState,
    modifier: Modifier = Modifier,
    onAction: (Action) -> Unit,
    innerPaddings: PaddingValues = PaddingValues(),
) {
    val bookId = uiState.currentBookId
    if (bookId != null) {
        ReaderFragmentContent(
            modifier = modifier,
            bookId = bookId,
        )

        ControlOverlay(
            modifier = Modifier.fillMaxSize(),
            paddingValues = innerPaddings,
            currentPage = uiState.currentPage,
            totalPages = uiState.totalPages,
            isOverlayActive = uiState.isOverlayShown,
            canGoBackward = uiState.canGoBackward,
            canGoForward = uiState.canGoForward,
            onBackClick = { onAction(Action.OnBackButtonClick) },
            onPreviousClick = { onAction(Action.OnPrevPageButtonClicked) },
            onNextClick = { onAction(Action.OnNextPageButtonClicked) },
        )
    }


    if (!uiState.bookIsReady) {
        ReaderLoadingContent(
            modifier = modifier,
        )
    }
}

@Composable
private fun ReaderFragmentContent(
    bookId: String,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val activity = context as? AppCompatActivity

    if (activity == null) {
        ReaderErrorContent(
            text = stringResource(R.string.reader_screen_error_fragment_activity_required),
            modifier = modifier,
        )
        return
    }

    val isDarkTheme = isSystemInDarkTheme()

    key(bookId, isDarkTheme) {
        AndroidFragment<EpubReaderFragment>(
            modifier = modifier,
            arguments = EpubReaderFragment.getBundle(
                bookId = bookId,
                isDarkTheme = isDarkTheme
            )
        )
    }
}

@Composable
private fun ReaderLoadingContent(
    modifier: Modifier = Modifier,
) {
    val spacing = LocalSpacing.current
    val size = LocalSize.current

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.MenuBook,
            contentDescription = null,
            modifier = Modifier.size(size.sizeX16),
            tint = MaterialTheme.colorScheme.primary,
        )

        Spacer(modifier = Modifier.size(spacing.paddingX4))

        Text(
            text = stringResource(R.string.reader_screen_loading_title),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
        )

        Spacer(modifier = Modifier.size(spacing.paddingX2))

        Text(
            text = stringResource(R.string.reader_screen_loading_message),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.size(spacing.paddingX6))

        CircularProgressIndicator()
    }
}

@Composable
private fun ReaderErrorContent(
    text: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error,
        )
    }
}

@Preview(name = "Loading", showBackground = true)
@Composable
private fun ScreenContentLoadingPreview() {
    AppTheme {
        ScreenContent(
            uiState = UiState.empty(),
            modifier = Modifier.fillMaxSize(),
            onAction = {}
        )
    }
}

@Preview(name = "Error", showBackground = true)
@Composable
private fun ReaderErrorContentPreview() {
    AppTheme {
        ReaderErrorContent(
            text = "Не удалось открыть экран чтения",
            modifier = Modifier.fillMaxSize(),
        )
    }
}