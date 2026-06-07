package com.example.matule.presentation.filters

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Switch
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.matule.R
import com.example.matule.common.catalog.PriceRangeValidationResult
import com.example.matule.common.catalog.PriceRangeValidator
import com.example.matule.domain.model.FilterOptions
import com.example.matule.domain.model.ShoeColor
import com.example.matule.domain.model.ShoeType
import com.example.matule.presentation.shop.ProductUiState

/**
 * Purpose: Shows Sprint 3 product filters and stores selected options in memory.
 * Creation date: 2026-06-07
 * Author: Mors
 */
class FiltersFragment : Fragment() {
    private val priceRangeValidator = PriceRangeValidator()
    private val colorBoxes = linkedMapOf<ShoeColor, CheckBox>()
    private val typeBoxes = linkedMapOf<ShoeType, CheckBox>()

    private lateinit var discountSwitch: Switch
    private lateinit var minPriceEditText: EditText
    private lateinit var maxPriceEditText: EditText
    private lateinit var colorsContainer: LinearLayout
    private lateinit var typesContainer: LinearLayout

    /**
     * Purpose: Creates Filters XML view.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_filters, container, false)

    /**
     * Purpose: Fills filter controls and handles apply button.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        renderFilterOptions()
        view.findViewById<View>(R.id.filtersBackButton).setOnClickListener { findNavController().navigateUp() }
        view.findViewById<View>(R.id.applyFiltersButton).setOnClickListener { applyFilters() }
    }

    private fun bindViews(view: View) {
        discountSwitch = view.findViewById(R.id.onlyDiscountSwitch)
        minPriceEditText = view.findViewById(R.id.minPriceEditText)
        maxPriceEditText = view.findViewById(R.id.maxPriceEditText)
        colorsContainer = view.findViewById(R.id.filterColorsContainer)
        typesContainer = view.findViewById(R.id.filterTypesContainer)
    }

    private fun renderFilterOptions() {
        val options = ProductUiState.filterOptions
        discountSwitch.isChecked = options.onlyDiscount
        minPriceEditText.setText(options.minPrice?.toInt()?.toString().orEmpty())
        maxPriceEditText.setText(options.maxPrice?.toInt()?.toString().orEmpty())

        addColorBox(ShoeColor.BLACK, R.string.shop_color_black, options)
        addColorBox(ShoeColor.WHITE, R.string.shop_color_white, options)
        addColorBox(ShoeColor.BLUE, R.string.shop_color_blue, options)
        addColorBox(ShoeColor.RED, R.string.shop_color_red, options)
        addColorBox(ShoeColor.GREEN, R.string.shop_color_green, options)

        addTypeBox(ShoeType.CASUAL, R.string.shop_type_casual, options)
        addTypeBox(ShoeType.SPORT, R.string.shop_type_sport, options)
    }

    private fun addColorBox(color: ShoeColor, titleRes: Int, options: FilterOptions) {
        val box = createCheckBox(titleRes)
        box.isChecked = color in options.selectedColors
        colorBoxes[color] = box
        colorsContainer.addView(box)
    }

    private fun addTypeBox(type: ShoeType, titleRes: Int, options: FilterOptions) {
        val box = createCheckBox(titleRes)
        box.isChecked = type in options.selectedTypes
        typeBoxes[type] = box
        typesContainer.addView(box)
    }

    private fun createCheckBox(titleRes: Int): CheckBox {
        return CheckBox(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                48.dp()
            )
            text = getString(titleRes)
            textSize = 16f
            setTextColor(requireContext().getColor(R.color.matule_text_dark))
        }
    }

    private fun applyFilters() {
        val minText = minPriceEditText.text.toString()
        val maxText = maxPriceEditText.text.toString()
        val validationResult = priceRangeValidator.validate(minText, maxText)
        if (validationResult is PriceRangeValidationResult.Error) {
            showError()
            return
        }

        ProductUiState.filterOptions = FilterOptions(
            onlyDiscount = discountSwitch.isChecked,
            minPrice = minText.toDoubleOrNull(),
            maxPrice = maxText.toDoubleOrNull(),
            selectedColors = colorBoxes.filterValues { it.isChecked }.keys,
            selectedTypes = typeBoxes.filterValues { it.isChecked }.keys
        )
        findNavController().popBackStack()
    }

    private fun showError() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.validation_error_title)
            .setMessage(R.string.shop_filter_error)
            .setPositiveButton(R.string.dialog_ok, null)
            .show()
    }

    private fun Int.dp(): Int = (this * resources.displayMetrics.density).toInt()
}
