package com.firmfreez.app.home.impl.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import com.firmfreez.app.core.style_compose.AppTheme
import com.firmfreez.app.core.style_compose.colors.LocalExtendedColors
import com.firmfreez.app.core.style_compose.dimensions.LocalBorderWidth
import com.firmfreez.app.core.style_compose.dimensions.LocalCornerRadii
import com.firmfreez.app.core.style_compose.dimensions.LocalSize
import com.firmfreez.app.core.style_compose.dimensions.LocalSpacing
import com.firmfreez.app.home.impl.R
import com.firmfreez.app.home.impl.ui.models.BookUiModel

@Composable
internal fun BookItem(
    book: BookUiModel,
    modifier: Modifier = Modifier,
    onClick: (BookUiModel) -> Unit,
    onDeleteClick: (BookUiModel) -> Unit,
) {
    val spacing = LocalSpacing.current
    val size = LocalSize.current
    val cornerRadii = LocalCornerRadii.current
    val borderWidth = LocalBorderWidth.current

    var isMenuExpanded by remember(book.id) { mutableStateOf(false) }

    Surface(
        modifier = modifier.fillMaxWidth(),
        onClick = { onClick(book) },
        shape = RoundedCornerShape(cornerRadii.cornersX4),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(
            width = borderWidth.borderX0_25,
            color = MaterialTheme.colorScheme.outline,
        ),
        tonalElevation = size.sizeX0,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.paddingX4),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box {
                Surface(
                    modifier = Modifier
                        .width(size.sizeX16)
                        .height(size.sizeX24),
                    shape = RoundedCornerShape(cornerRadii.cornersX3),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                ) {
                    if (!book.coverUri.isNullOrBlank()) {
                        AsyncImage(
                            model = book.coverUri,
                            contentDescription = book.title,
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop,
                        )
                    } else {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.MenuBook,
                                contentDescription = null,
                                modifier = Modifier.size(size.sizeX8),
                                tint = MaterialTheme.colorScheme.primary,
                            )
                        }
                    }
                }

                when (book.progressState) {
                    BookUiModel.ProgressState.New -> {
                        NewBadge(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .offset(x = spacing.paddingX1, y = -spacing.paddingX1),
                        )
                    }

                    BookUiModel.ProgressState.Finished -> {
                        FinishedBadge(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .offset(x = spacing.paddingX1, y = -spacing.paddingX1),
                        )
                    }

                    is BookUiModel.ProgressState.InProgress -> Unit
                }
            }

            Spacer(modifier = Modifier.width(spacing.paddingX4))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                book.subtitle?.takeIf { it.isNotBlank() }?.let { subtitle ->
                    Spacer(modifier = Modifier.height(spacing.paddingX1))
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }

                when (val progressState = book.progressState) {
                    is BookUiModel.ProgressState.InProgress -> {
                        Spacer(modifier = Modifier.height(spacing.paddingX2))

                        Text(
                            text = stringResource(
                                R.string.home_screen_books_item_in_progress,
                                progressState.progress.coerceIn(0, 100),
                            ),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )

                        Spacer(modifier = Modifier.height(spacing.paddingX1))

                        LinearProgressIndicator(
                            progress = { progressState.progress.coerceIn(0, 100) / 100f },
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }

                    BookUiModel.ProgressState.New,
                    BookUiModel.ProgressState.Finished -> Unit
                }

                if (!book.isAvailable) {
                    Spacer(modifier = Modifier.height(spacing.paddingX2))
                    Text(
                        text = stringResource(R.string.home_screen_books_item_unavailable),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }

            Box {
                IconButton(
                    onClick = { isMenuExpanded = true }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.MoreVert,
                        contentDescription = stringResource(R.string.home_screen_books_item_menu_content_description),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }

                DropdownMenu(
                    expanded = isMenuExpanded,
                    onDismissRequest = { isMenuExpanded = false },
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(text = stringResource(R.string.home_screen_books_item_action_view))
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Visibility,
                                contentDescription = null,
                            )
                        },
                        onClick = {
                            isMenuExpanded = false
                            onClick(book)
                        },
                    )

                    DropdownMenuItem(
                        text = {
                            Text(text = stringResource(R.string.home_screen_books_item_action_delete))
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.DeleteOutline,
                                contentDescription = null,
                            )
                        },
                        onClick = {
                            isMenuExpanded = false
                            onDeleteClick(book)
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun NewBadge(
    modifier: Modifier = Modifier,
) {
    val spacing = LocalSpacing.current
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(percent = 50),
        color = MaterialTheme.colorScheme.error,
    ) {
        Text(
            text = stringResource(R.string.home_screen_books_item_badge_new),
            modifier = Modifier.padding(
                horizontal = spacing.paddingX2,
                vertical = spacing.paddingX1,
            ),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onError,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

@Composable
private fun FinishedBadge(
    modifier: Modifier = Modifier,
) {
    val size = LocalSize.current
    Surface(
        modifier = modifier
            .size(size.sizeX6)
            .clip(CircleShape),
        shape = CircleShape,
        color = LocalExtendedColors.current.success,
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Outlined.Check,
                contentDescription = stringResource(R.string.home_screen_books_item_badge_finished),
                tint = MaterialTheme.colorScheme.onTertiary,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BookItemPreviewNew() =
    AppTheme {
        BookItem(
            book = BookUiModel(
                id = "1",
                title = "Чистый код",
                author = "Роберт Мартин",
                coverUri = null,
                subtitle = "Роберт Мартин • 2,4 МБ",
                isAvailable = true,
                progressState = BookUiModel.ProgressState.New,
            ),
            onClick = {},
            onDeleteClick = {},
        )
    }

@Preview(showBackground = true)
@Composable
private fun BookItemPreviewInProgress() =
    AppTheme {
        BookItem(
            book = BookUiModel(
                id = "2",
                title = "Идеальный программист",
                author = "Роберт Мартин",
                coverUri = null,
                subtitle = "Роберт Мартин • 3,1 МБ",
                isAvailable = true,
                progressState = BookUiModel.ProgressState.InProgress(progress = 42),
            ),
            onClick = {},
            onDeleteClick = {},
        )
    }

@Preview(showBackground = true)
@Composable
private fun BookItemPreviewFinished() =
    AppTheme {
        BookItem(
            book = BookUiModel(
                id = "3",
                title = "Рефакторинг",
                author = "Мартин Фаулер",
                coverUri = null,
                subtitle = "Мартин Фаулер • 4,8 МБ",
                isAvailable = true,
                progressState = BookUiModel.ProgressState.Finished,
            ),
            onClick = {},
            onDeleteClick = {},
        )
    }