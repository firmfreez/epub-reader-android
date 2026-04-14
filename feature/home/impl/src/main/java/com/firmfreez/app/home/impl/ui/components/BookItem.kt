package com.firmfreez.app.home.impl.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import com.firmfreez.app.core.style_compose.AppTheme
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

                if (!book.isAvailable) {
                    Spacer(modifier = Modifier.height(spacing.paddingX1))
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

@Preview(showBackground = true)
@Composable
private fun BookItemPreview() =
    AppTheme {
        BookItem(
            book = BookUiModel(
                id = "1",
                title = "Чистый код",
                author = "Роберт Мартин",
                coverUri = null,
                subtitle = "Роберт Мартин • 2,4 МБ",
                isAvailable = true,
            ),
            onClick = {},
            onDeleteClick = {},
        )
    }