package com.karsu.pandafabsmenu

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.karsu.pandafabsmenu.databinding.ActivityMainBinding
import karsu.libs.fabsmenu.KarSuFabsMenu
import karsu.libs.fabsmenu.KarSuFabsMenuListener

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupWindowInsets()
        setupToolbar()
        setupSettingsButton()
        setupProductsButton()
        setupFabsMenu()
        setupBackPressHandler()
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
    }

    private fun setupSettingsButton() {
        binding.btnSettings.setOnClickListener {
            showSettingsBottomSheet()
        }
    }

    private fun setupProductsButton() {
        binding.btnProducts.setOnClickListener {
            startActivity(Intent(this, ProductListActivity::class.java))
        }
    }

    private fun showSettingsBottomSheet() {
        val bottomSheet = SettingsBottomSheet()
        val primaryColor = ContextCompat.getColor(this, R.color.fab_turquoise)
        bottomSheet.setTargets(binding.fabsMenu, binding.fabsMenuLayout, primaryColor)
        bottomSheet.show(supportFragmentManager, SettingsBottomSheet.TAG)
    }

    private fun setupFabsMenu() {
        binding.fabsMenu.menuListener = object : KarSuFabsMenuListener() {
            override fun onMenuExpanded(fabsMenu: KarSuFabsMenu) {
                // Menu expanded
            }

            override fun onMenuCollapsed(fabsMenu: KarSuFabsMenu) {
                // Menu collapsed
            }
        }

        binding.fabDownload.setOnClickListener {
            showToast("Download clicked")
            binding.fabsMenu.collapse()
        }

        binding.fabCamera.setOnClickListener {
            showToast("Camera clicked")
            binding.fabsMenu.collapse()
        }

        binding.fabShare.setOnClickListener {
            showToast("Share clicked")
            binding.fabsMenu.collapse()
        }

        binding.fabEdit.setOnClickListener {
            showToast("Edit clicked")
            binding.fabsMenu.collapse()
        }

        binding.fabFavorite.setOnClickListener {
            showToast("Favorite clicked")
            binding.fabsMenu.collapse()
        }
    }

    private fun setupBackPressHandler() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.fabsMenu.isExpanded) {
                    binding.fabsMenu.collapse()
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Clear listener to prevent memory leak
        binding.fabsMenu.menuListener = null
    }
}
