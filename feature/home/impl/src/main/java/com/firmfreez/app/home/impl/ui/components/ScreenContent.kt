package com.firmfreez.app.home.impl.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.firmfreez.app.common.ui_components.button.AppButton
import com.firmfreez.app.common.ui_components.button.ButtonSizeType
import com.firmfreez.app.common.ui_components.button.ButtonState
import com.firmfreez.app.core.style_compose.AppTheme
import com.firmfreez.app.core.style_compose.dimensions.LocalCornerRadii
import com.firmfreez.app.core.style_compose.dimensions.LocalSize
import com.firmfreez.app.core.style_compose.dimensions.LocalSpacing
import com.firmfreez.app.home.impl.R
import com.firmfreez.app.home.impl.ui.models.Action
import com.firmfreez.app.home.impl.ui.models.BookUiModel
import com.firmfreez.app.home.impl.ui.models.UiState

@Composable
fun ScreenContent(
    uiState: UiState,
    modifier: Modifier = Modifier,
    onAction: (Action) -> Unit,
) {
    val state = uiState.contentState

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        HomeToolbar()

        BooksHeader(
            title = stringResource(R.string.home_screen_books_title),
            showAddButton = !uiState.isLoading,
            onAddClick = { onAction(Action.OnOpenBookClick) },
        )

        AnimatedContent(
            targetState = state,
            modifier = Modifier.weight(1f),
            transitionSpec = {
                fadeIn() togetherWith fadeOut() using SizeTransform(clip = false)
            },
            label = "home_screen_content_animation",
        ) { targetState ->
            when (targetState) {
                UiState.HomeContentState.Loading -> {
                    BooksLoadingScreenContent(
                        modifier = Modifier.fillMaxSize(),
                    )
                }

                UiState.HomeContentState.Empty -> {
                    EmptyBooksScreenContent(
                        modifier = Modifier.fillMaxSize(),
                        onAddClick = { onAction(Action.OnOpenBookClick) },
                    )
                }

                is UiState.HomeContentState.Content -> {
                    BooksScreenContent(
                        books = targetState.books,
                        modifier = Modifier.fillMaxSize(),
                        onBookClick = {
                            onAction(Action.OnBookClicked(it.id))
                        },
                        onBookDeleteClick = {
                            onAction(Action.OnBookDeleteClicked(it.id))
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun HomeToolbar(
    modifier: Modifier = Modifier,
) {
    val spacing = LocalSpacing.current
    val size = LocalSize.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(size.sizeX14)
            .padding(horizontal = spacing.paddingX4),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun BooksHeader(
    title: String,
    showAddButton: Boolean,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val spacing = LocalSpacing.current
    val corners = LocalCornerRadii.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = spacing.paddingX4,
                end = spacing.paddingX4,
                top = spacing.paddingX2,
                bottom = spacing.paddingX2,
            )
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.align(Alignment.CenterStart),
        )

        if (showAddButton) {
            AppButton(
                onClick = onAddClick,
                modifier = Modifier.align(Alignment.CenterEnd),
                state = ButtonState.Filled(sizeType = ButtonSizeType.Small),
                shape = RoundedCornerShape(corners.cornersX3),
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = null,
                )
                Text(
                    text = stringResource(R.string.home_screen_books_add_button),
                    modifier = Modifier.padding(start = spacing.paddingX2),
                )
            }
        }
    }
}



@Preview(name = "Loading", showBackground = true)
@Composable
private fun ScreenContentLoadingPreview() {
    AppTheme {
        ScreenContent(
            uiState = UiState(books = null),
            modifier = Modifier.fillMaxSize(),
            onAction = {},
        )
    }
}

@Preview(name = "Empty", showBackground = true)
@Composable
private fun ScreenContentEmptyPreview() {
    AppTheme {
        ScreenContent(
            uiState = UiState(books = emptyList()),
            modifier = Modifier.fillMaxSize(),
            onAction = {},
        )
    }
}

@Preview(name = "Content", showBackground = true)
@Composable
private fun ScreenContentContentPreview() {
    AppTheme {
        ScreenContent(
            uiState = UiState(
                books = listOf(
                    BookUiModel(
                        id = "1",
                        title = "Чистый код",
                        author = "Роберт Мартин",
                        coverUri = null,
                        subtitle = "Роберт Мартин • 2,4 МБ",
                        isAvailable = true,
                    ),
                    BookUiModel(
                        id = "2",
                        title = "Kotlin в действии",
                        author = "Дмитрий Жемеров",
                        coverUri = null,
                        subtitle = "Дмитрий Жемеров • 3,1 МБ",
                        isAvailable = true,
                    ),
                    BookUiModel(
                        id = "3",
                        title = "Android для профессионалов",
                        author = "Билл Филлипс",
                        coverUri = null,
                        subtitle = "Билл Филлипс • 5,7 МБ",
                        isAvailable = false,
                    ),
                )
            ),
            modifier = Modifier.fillMaxSize(),
            onAction = {},
        )
    }
}