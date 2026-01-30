package com.karsu.pandafabsmenu.model

import com.karsu.pandafabsmenu.R

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val iconRes: Int
) {
    companion object {
        fun getDummyProducts(): List<Product> = listOf(
            Product(
                id = 1,
                name = "Wireless Headphones",
                description = "Premium noise-cancelling headphones with 30-hour battery",
                price = 149.99,
                iconRes = R.drawable.ic_headphones
            ),
            Product(
                id = 2,
                name = "Smart Watch Pro",
                description = "Fitness tracker with heart rate monitor and GPS",
                price = 299.99,
                iconRes = R.drawable.ic_watch
            ),
            Product(
                id = 3,
                name = "Mechanical Keyboard",
                description = "RGB backlit gaming keyboard with cherry switches",
                price = 129.99,
                iconRes = R.drawable.ic_keyboard
            ),
            Product(
                id = 4,
                name = "Webcam HD Pro",
                description = "1080p webcam with built-in microphone and auto-focus",
                price = 79.99,
                iconRes = R.drawable.ic_videocam
            ),
            Product(
                id = 5,
                name = "Wireless Charger",
                description = "15W fast wireless charging for all Qi devices",
                price = 29.99,
                iconRes = R.drawable.ic_charging
            )
        )
    }
}
