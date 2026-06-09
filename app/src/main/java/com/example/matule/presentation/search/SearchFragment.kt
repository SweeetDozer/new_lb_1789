package com.example.matule.presentation.search

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.matule.R
import com.example.matule.common.search.SearchCache
import com.example.matule.common.search.SearchEngine
import com.example.matule.common.search.SearchHistoryManager
import com.example.matule.data.local.ProductMockData
import com.example.matule.domain.model.Product
import com.example.matule.presentation.details.DetailsFragment
import com.example.matule.presentation.shop.ProductUiState

/**
 * Purpose: Shows Sprint 4 Search screen with local results, history, and cache.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class SearchFragment : Fragment() {
    private val products = ProductMockData.products()
    private val searchEngine = SearchEngine()
    private val historyHandler = Handler(Looper.getMainLooper())

    private lateinit var searchEditText: EditText
    private lateinit var historyContainer: LinearLayout
    private lateinit var resultsContainer: LinearLayout
    private lateinit var emptyTextView: TextView
    private var isSubmitting = false
    private var isUpdatingInput = false
    private var pendingHistorySave: Runnable? = null

    /**
     * Purpose: Creates Search XML view.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_search, container, false)

    /**
     * Purpose: Connects search input, history, and result actions.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchEditText = view.findViewById(R.id.searchEditText)
        historyContainer = view.findViewById(R.id.searchHistoryContainer)
        resultsContainer = view.findViewById(R.id.searchResultsContainer)
        emptyTextView = view.findViewById(R.id.searchEmptyTextView)

        view.findViewById<View>(R.id.searchBackButton).setOnClickListener {
            findNavController().navigateUp()
        }
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) = Unit

            override fun afterTextChanged(text: Editable?) {
                if (!isUpdatingInput) {
                    val query = text.toString()
                    renderResults(searchEngine.search(products, query))
                    scheduleHistorySave(query)
                }
            }
        })
        searchEditText.setOnEditorActionListener { _, actionId, event ->
            if (isSearchAction(actionId, event)) {
                submitSearch(searchEditText.text.toString(), showCacheToast = false)
                true
            } else {
                false
            }
        }
        searchEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && searchEditText.text.toString().trim().isNotEmpty()) {
                submitSearch(searchEditText.text.toString(), showCacheToast = false)
            }
        }

        renderHistory()
        renderResults(products)
    }

    override fun onDestroyView() {
        pendingHistorySave?.let { historyHandler.removeCallbacks(it) }
        pendingHistorySave = null
        super.onDestroyView()
    }

    private fun submitSearch(query: String, showCacheToast: Boolean) {
        if (isSubmitting) {
            return
        }

        isSubmitting = true
        val preparedQuery = query.trim()
        hideKeyboard()
        updateSearchInput(preparedQuery)

        sharedHistory.add(preparedQuery)
        // Results are loaded locally for the lab task.
        val result = sharedCache.search(preparedQuery) { searchQuery ->
            searchEngine.search(products, searchQuery)
        }

        if (showCacheToast && result.wasFromCache) {
            Toast.makeText(requireContext(), R.string.search_result_from_history, Toast.LENGTH_SHORT).show()
        }
        renderHistory()
        renderResults(result.products)
        isSubmitting = false
    }

    private fun scheduleHistorySave(query: String) {
        pendingHistorySave?.let { historyHandler.removeCallbacks(it) }
        val preparedQuery = query.trim()
        if (preparedQuery.isEmpty()) {
            return
        }

        pendingHistorySave = Runnable {
            if (searchEditText.text.toString().trim() == preparedQuery) {
                saveQueryToHistoryAndCache(preparedQuery)
            }
        }
        historyHandler.postDelayed(pendingHistorySave!!, HISTORY_SAVE_DELAY_MS)
    }

    private fun saveQueryToHistoryAndCache(query: String) {
        sharedHistory.add(query)
        sharedCache.search(query) { searchQuery -> searchEngine.search(products, searchQuery) }
        renderHistory()
    }

    private fun updateSearchInput(query: String) {
        if (searchEditText.text.toString() == query) {
            return
        }

        isUpdatingInput = true
        searchEditText.setText(query)
        searchEditText.setSelection(searchEditText.text.length)
        isUpdatingInput = false
    }

    private fun isSearchAction(actionId: Int, event: KeyEvent?): Boolean {
        val isImeSearch = actionId == EditorInfo.IME_ACTION_SEARCH
        val isEnterUp = event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP
        return isImeSearch || isEnterUp
    }

    private fun renderHistory() {
        historyContainer.removeAllViews()
        sharedHistory.history().forEach { query ->
            val item = TextView(requireContext())
            item.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                44.dp()
            ).apply { bottomMargin = 8.dp() }
            item.background = requireContext().getDrawable(R.drawable.bg_shop_chip)
            item.gravity = Gravity.CENTER_VERTICAL
            item.setPadding(18.dp(), 0, 18.dp(), 0)
            item.text = query
            item.setTextColor(requireContext().getColor(R.color.matule_text_dark))
            item.textSize = 15f
            item.setOnClickListener { submitSearch(query, showCacheToast = true) }
            historyContainer.addView(item)
        }
    }

    private fun renderResults(items: List<Product>) {
        resultsContainer.removeAllViews()
        emptyTextView.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE
        items.forEach { product ->
            resultsContainer.addView(createProductItem(product))
        }
    }

    private fun createProductItem(product: Product): View {
        val item = layoutInflater.inflate(R.layout.item_search_product, resultsContainer, false)
        item.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            112.dp()
        ).apply { bottomMargin = 12.dp() }

        item.findViewById<ImageView>(R.id.searchProductImageView).setImageResource(R.drawable.img_onboard_1)
        item.findViewById<TextView>(R.id.searchProductNameTextView).text = product.name
        item.findViewById<TextView>(R.id.searchProductCategoryTextView).text = product.category
        item.findViewById<TextView>(R.id.searchProductPriceTextView).text = "\u20BD${product.price.toInt()}.00"
        item.findViewById<View>(R.id.searchProductAddButton).setOnClickListener {
            ProductUiState.cartManager.add(product)
            Toast.makeText(requireContext(), R.string.shop_added_to_cart, Toast.LENGTH_SHORT).show()
        }
        item.setOnClickListener { openDetails(product) }
        return item
    }

    private fun openDetails(product: Product) {
        ProductUiState.selectedProductId = product.id
        findNavController().navigate(
            R.id.action_searchFragment_to_detailsFragment,
            bundleOf(DetailsFragment.ARG_PRODUCT_ID to product.id)
        )
    }

    private fun hideKeyboard() {
        val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(searchEditText.windowToken, 0)
        searchEditText.clearFocus()
    }

    private fun Int.dp(): Int {
        return (this * resources.displayMetrics.density).toInt()
    }

    private companion object {
        const val HISTORY_SAVE_DELAY_MS = 800L
        val sharedHistory = SearchHistoryManager()
        val sharedCache = SearchCache()
    }
}
