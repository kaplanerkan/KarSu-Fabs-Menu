package com.karsu.pandafabsmenu.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.karsu.pandafabsmenu.databinding.ItemProductBinding
import com.karsu.pandafabsmenu.model.Product
import karsu.libs.fabsmenu.KarSuFabsMenu
import karsu.libs.fabsmenu.KarSuFabsMenuListener
import java.text.NumberFormat
import java.util.Locale

class ProductAdapter(
    private val onActionClick: (Product, ProductAction) -> Unit
) : ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffCallback()) {

    private var expandedPosition: Int = -1

    companion object {
        private val priceFormat = NumberFormat.getCurrencyInstance(Locale.US)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onViewRecycled(holder: ProductViewHolder) {
        super.onViewRecycled(holder)
        holder.cleanup()
    }

    inner class ProductViewHolder(
        private val binding: ItemProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentProduct: Product? = null

        init {
            setupListeners()
        }

        private fun setupListeners() {
            binding.fabsMenu.menuListener = object : KarSuFabsMenuListener() {
                override fun onMenuExpanded(fabsMenu: KarSuFabsMenu) {
                    val position = bindingAdapterPosition
                    if (position == RecyclerView.NO_POSITION) return

                    val previousExpanded = expandedPosition
                    expandedPosition = position

                    if (previousExpanded != -1 && previousExpanded != expandedPosition) {
                        notifyItemChanged(previousExpanded)
                    }
                }

                override fun onMenuCollapsed(fabsMenu: KarSuFabsMenu) {
                    val position = bindingAdapterPosition
                    if (position == RecyclerView.NO_POSITION) return

                    if (expandedPosition == position) {
                        expandedPosition = -1
                    }
                }
            }

            binding.fabPrint.setOnClickListener {
                currentProduct?.let { product ->
                    onActionClick(product, ProductAction.PRINT)
                    binding.fabsMenu.collapse()
                }
            }

            binding.fabPrice.setOnClickListener {
                currentProduct?.let { product ->
                    onActionClick(product, ProductAction.CHANGE_PRICE)
                    binding.fabsMenu.collapse()
                }
            }

            binding.fabEdit.setOnClickListener {
                currentProduct?.let { product ->
                    onActionClick(product, ProductAction.EDIT)
                    binding.fabsMenu.collapse()
                }
            }

            binding.fabDelete.setOnClickListener {
                currentProduct?.let { product ->
                    onActionClick(product, ProductAction.DELETE)
                    binding.fabsMenu.collapse()
                }
            }
        }

        fun bind(product: Product) {
            currentProduct = product

            binding.txtProductName.text = product.name
            binding.txtProductDesc.text = product.description
            binding.txtProductPrice.text = priceFormat.format(product.price)
            binding.imgProduct.setImageResource(product.iconRes)

            val position = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION &&
                expandedPosition != position &&
                binding.fabsMenu.isExpanded) {
                binding.fabsMenu.collapseImmediately()
            }
        }

        fun cleanup() {
            currentProduct = null
            if (binding.fabsMenu.isExpanded) {
                binding.fabsMenu.collapseImmediately()
            }
        }
    }

    fun collapseAllMenus() {
        if (expandedPosition != -1) {
            val previous = expandedPosition
            expandedPosition = -1
            notifyItemChanged(previous)
        }
    }

    private class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}

enum class ProductAction {
    PRINT,
    CHANGE_PRICE,
    EDIT,
    DELETE
}
