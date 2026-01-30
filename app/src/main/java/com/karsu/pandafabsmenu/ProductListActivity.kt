package com.karsu.pandafabsmenu

import android.os.Bundle
import android.os.CountDownTimer
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.karsu.pandafabsmenu.adapter.ProductAction
import com.karsu.pandafabsmenu.adapter.ProductAdapter
import com.karsu.pandafabsmenu.databinding.ActivityProductListBinding
import com.karsu.pandafabsmenu.model.Product
import java.text.NumberFormat
import java.util.Locale

class ProductListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductListBinding
    private lateinit var adapter: ProductAdapter
    private var currentPopup: PopupWindow? = null
    private var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupWindowInsets()
        setupToolbar()
        setupRecyclerView()
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.coordinator) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupRecyclerView() {
        adapter = ProductAdapter { product, action ->
            showActionPopup(product, action)
        }

        binding.recyclerProducts.adapter = adapter
        adapter.submitList(Product.getDummyProducts())
    }

    private fun showActionPopup(product: Product, action: ProductAction) {
        // Dismiss any existing popup and timer
        dismissPopup()

        val popupView = LayoutInflater.from(this).inflate(R.layout.popup_action, null)

        val txtTitle = popupView.findViewById<TextView>(R.id.txtPopupTitle)
        val txtProduct = popupView.findViewById<TextView>(R.id.txtPopupProduct)
        val txtAction = popupView.findViewById<TextView>(R.id.txtPopupAction)
        val txtDetails = popupView.findViewById<TextView>(R.id.txtPopupDetails)
        val txtCountdown = popupView.findViewById<TextView>(R.id.txtCountdown)

        val actionText = when (action) {
            ProductAction.PRINT -> getString(R.string.action_print)
            ProductAction.CHANGE_PRICE -> getString(R.string.action_change_price)
            ProductAction.EDIT -> getString(R.string.action_edit)
            ProductAction.DELETE -> getString(R.string.action_delete)
        }

        val priceFormat = NumberFormat.getCurrencyInstance(Locale.US)

        txtTitle.text = getString(R.string.popup_title)
        txtProduct.text = product.name
        txtAction.text = actionText
        txtDetails.text = getString(
            R.string.popup_details_format,
            product.id,
            priceFormat.format(product.price)
        )

        val popup = PopupWindow(
            popupView,
            android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
            android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )

        popup.elevation = 16f
        popup.animationStyle = android.R.style.Animation_Toast

        popupView.measure(
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )

        val xOffset = (binding.root.width - popupView.measuredWidth) / 2
        val yOffset = (binding.root.height - popupView.measuredHeight) / 2

        popup.showAtLocation(binding.root, Gravity.TOP or Gravity.START, xOffset, yOffset)
        currentPopup = popup

        // Start countdown timer
        countDownTimer = object : CountDownTimer(POPUP_DURATION, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = (millisUntilFinished / 1000).toInt() + 1
                txtCountdown.text = getString(R.string.countdown_format, secondsRemaining)
            }

            override fun onFinish() {
                dismissPopup()
            }
        }.start()

        // Set initial countdown text
        txtCountdown.text = getString(R.string.countdown_format, 5)
    }

    private fun dismissPopup() {
        countDownTimer?.cancel()
        countDownTimer = null
        currentPopup?.dismiss()
        currentPopup = null
    }

    override fun onDestroy() {
        super.onDestroy()
        dismissPopup()
    }

    companion object {
        private const val POPUP_DURATION = 5000L
    }
}
