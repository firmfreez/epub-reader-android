package com.firmfreez.app.reader.impl.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.commitNow
import androidx.lifecycle.lifecycleScope
import com.firmfreez.app.reader.impl.R
import com.firmfreez.app.reader.impl.ui.ReaderScreenViewModel
import com.firmfreez.app.reader.impl.ui.models.Action
import com.firmfreez.app.reader.impl.ui.models.UiEvent
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.readium.r2.navigator.epub.EpubNavigatorFactory
import org.readium.r2.navigator.epub.EpubNavigatorFragment
import org.readium.r2.navigator.epub.EpubPreferences
import org.readium.r2.navigator.input.InputListener
import org.readium.r2.navigator.input.TapEvent
import org.readium.r2.navigator.preferences.ReadingProgression
import org.readium.r2.navigator.preferences.Spread
import org.readium.r2.navigator.preferences.Theme
import org.readium.r2.shared.ExperimentalReadiumApi
import org.readium.r2.shared.publication.Locator
import org.readium.r2.shared.publication.Publication
import org.readium.r2.shared.publication.services.positions

class EpubReaderFragment : Fragment() {

    private val viewModel: ReaderScreenViewModel by activityViewModel()

    // Arguments
    private val bookId: String
        get() = requireArguments().getString(ARG_BOOK_ID) ?: error("bookId is required")
    private val isDarkTheme: Boolean
        get() = requireArguments().getBoolean(DARK_THEME_MODE_ID)

    // Navigator
    private var navigatorFragment: EpubNavigatorFragment? = null

    @OptIn(ExperimentalReadiumApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel.getPublication(bookId = bookId)?.let { publication ->
            childFragmentManager.fragmentFactory = EpubNavigatorFactory(
                publication = publication,
                configuration = EpubNavigatorFactory.Configuration(),
            ).createFragmentFactory(
                initialLocator = viewModel.uiState.value.initialLocator,
                initialPreferences = EpubPreferences(
                    theme = if (isDarkTheme) Theme.DARK else Theme.LIGHT,
                    scroll = true,
                    readingProgression = ReadingProgression.LTR,
                    spread = Spread.ALWAYS,
                ),
                listener = null,
                paginationListener = object : EpubNavigatorFragment.PaginationListener {
                    override fun onPageChanged(pageIndex: Int, totalPages: Int, locator: Locator) {
                        viewModel.onAction(
                            Action.UpdatePageData(
                                currentPage = publication.currentReadingOrderIndex(locator) + 1,
                                totalPages = publication.readingOrder.size,
                                locator = locator,
                                publication = publication,
                            )
                        )
                    }

                    override fun onPageLoaded() {
                        super.onPageLoaded()
                        viewModel.onAction(Action.SetBookIsReady(true))
                    }
                },
                configuration = EpubNavigatorFragment.Configuration(
                    shouldApplyInsetsPadding = true,
                ),
            )
        } ?: run { viewModel.onAction(Action.FailedToOpenPublication(getString(R.string.reader_open_error_publication))) }
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return FragmentContainerView(requireContext()).apply {
            id = R.id.reader_navigator_container
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            childFragmentManager.commitNow {
                replace(
                    R.id.reader_navigator_container,
                    EpubNavigatorFragment::class.java,
                    Bundle(),
                )
            }
            navigatorFragment = findNavigatorFragment()
        }


        observeUiEvents()
        bindInputListener()
    }

    private fun findNavigatorFragment(): EpubNavigatorFragment? =
        childFragmentManager.fragments.firstOrNull { it is EpubNavigatorFragment } as? EpubNavigatorFragment

    private fun observeUiEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiEvents.collect { event ->
                when (event) {
                    is UiEvent.ReaderUiEvent -> when (event) {
                        UiEvent.ReaderUiEvent.GoNextPage -> navigatorFragment?.goForward(animated = true)
                        UiEvent.ReaderUiEvent.GoPrevPage -> navigatorFragment?.goBackward(animated = true)
                    }

                    is UiEvent.ScreenUiEvent -> { /* Handled in ReaderScreen */ }
                }
            }
        }
    }

    @OptIn(ExperimentalReadiumApi::class)
    private fun bindInputListener() {
        navigatorFragment?.addInputListener(
            object : InputListener {
                override fun onTap(event: TapEvent): Boolean {
                    viewModel.onAction(Action.OnPageClicked)
                    return false
                }
            }
        )
    }

    private fun Publication.currentReadingOrderIndex(locator: Locator): Int {
        val locatorHref = locator.href.toString()

        return readingOrder.indexOfFirst { link ->
            link.href.toString() == locatorHref
        }
    }


    companion object {
        private const val ARG_BOOK_ID = "book_id"
        private const val DARK_THEME_MODE_ID = "dark_theme_mode_id"

        fun getBundle(bookId: String, isDarkTheme: Boolean): Bundle {
            return Bundle().apply {
                putString(ARG_BOOK_ID, bookId)
                putBoolean(DARK_THEME_MODE_ID, isDarkTheme)
            }
        }
    }
}