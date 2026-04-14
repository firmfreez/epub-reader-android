package com.firmfreez.app.home.impl.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.firmfreez.app.core.style_compose.AppTheme
import com.firmfreez.app.core.style_compose.dimensions.LocalSpacing
import com.firmfreez.app.home.impl.ui.models.BookUiModel

@Composable
internal fun BooksScreenContent(
    books: List<BookUiModel>,
    modifier: Modifier = Modifier,
    onBookClick: (BookUiModel) -> Unit,
    onBookDeleteClick: (BookUiModel) -> Unit,
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
        items(
            items = books,
            key = { it.id },
        ) { book ->
            BookItem(
                book = book,
                onClick = onBookClick,
                onDeleteClick = onBookDeleteClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BooksScreenContentPreview() =
    AppTheme {
        BooksScreenContent(
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
            ),
            onBookClick = {},
            onBookDeleteClick = {}
        )
    }