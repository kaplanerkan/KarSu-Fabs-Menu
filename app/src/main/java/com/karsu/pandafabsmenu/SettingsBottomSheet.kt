package com.karsu.pandafabsmenu

import android.graphics.Color
import android.os.Bundle
import androidx.core.graphics.toColorInt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.karsu.pandafabsmenu.databinding.BottomSheetSettingsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import karsu.libs.fabsmenu.KarSuFabsMenu
import karsu.libs.fabsmenu.KarSuFabsMenuLayout
import karsu.libs.fabsmenu.enums.ExpandDirection
import karsu.libs.fabsmenu.enums.LabelsPosition

class SettingsBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetSettingsBinding? = null
    private val binding get() = _binding!!

    private var fabsMenu: KarSuFabsMenu? = null
    private var fabsMenuLayout: KarSuFabsMenuLayout? = null
    private var primaryColor: Int = "#00BCD4".toColorInt()

    fun setTargets(menu: KarSuFabsMenu, layout: KarSuFabsMenuLayout, primary: Int) {
        fabsMenu = menu
        fabsMenuLayout = layout
        primaryColor = primary
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFABsMenuLayoutSettings()
        setupFABsMenuSettings()
        setupTitleFABSettings()
        setupResetButton()
    }

    private fun setupFABsMenuLayoutSettings() {
        binding.seekOverlayAlpha.onProgressChanged { progress, fromUser ->
            if (fromUser) {
                fabsMenuLayout?.setOverlayColor(Color.argb(progress, 0, 0, 0))
            }
        }

        binding.switchClickableOverlay.setOnCheckedChangeListener { _, isChecked ->
            fabsMenuLayout?.setClickableOverlay(isChecked)
        }
    }

    private fun setupFABsMenuSettings() {
        binding.chipGroupExpandDirection.setOnCheckedStateChangeListener { _, checkedIds ->
            if (checkedIds.isNotEmpty()) {
                val direction = when (checkedIds[0]) {
                    R.id.chipUp -> ExpandDirection.UP
                    R.id.chipDown -> ExpandDirection.DOWN
                    R.id.chipLeft -> ExpandDirection.LEFT
                    R.id.chipRight -> ExpandDirection.RIGHT
                    else -> ExpandDirection.UP
                }
                fabsMenu?.updateExpandDirection(direction)
            }
        }

        binding.chipGroupLabelsPosition.setOnCheckedStateChangeListener { _, checkedIds ->
            if (checkedIds.isNotEmpty()) {
                val position = when (checkedIds[0]) {
                    R.id.chipLabelsLeft -> LabelsPosition.LEFT
                    R.id.chipLabelsRight -> LabelsPosition.RIGHT
                    else -> LabelsPosition.LEFT
                }
                fabsMenu?.updateLabelsPosition(position)
            }
        }

        binding.chipGroupMenuSize.setOnCheckedStateChangeListener { _, checkedIds ->
            if (checkedIds.isNotEmpty()) {
                val size = when (checkedIds[0]) {
                    R.id.chipSizeNormal -> FloatingActionButton.SIZE_NORMAL
                    R.id.chipSizeMini -> FloatingActionButton.SIZE_MINI
                    else -> FloatingActionButton.SIZE_NORMAL
                }
                fabsMenu?.setMenuButtonSize(size)
            }
        }

        binding.seekMenuMargins.onProgressChanged { progress, fromUser ->
            binding.txtMenuMargins.text = getString(R.string.dp_format, progress)
            if (fromUser) {
                fabsMenu?.setMenuMargins(dpToPx(progress))
            }
        }

        binding.seekAnimationDuration.onProgressChanged { progress, fromUser ->
            binding.txtAnimationDuration.text = getString(R.string.ms_format, progress)
            if (fromUser) {
                fabsMenu?.setAnimationDuration(progress)
            }
        }
    }

    private fun setupTitleFABSettings() {
        binding.chipGroupLabelBg.setOnCheckedStateChangeListener { _, checkedIds ->
            if (checkedIds.isNotEmpty()) {
                val color = when (checkedIds[0]) {
                    R.id.chipLabelBgWhite -> Color.WHITE
                    R.id.chipLabelBgBlack -> Color.BLACK
                    R.id.chipLabelBgPrimary -> primaryColor
                    else -> Color.WHITE
                }
                fabsMenu?.updateAllLabelsBackgroundColor(color)
            }
        }

        binding.chipGroupLabelText.setOnCheckedStateChangeListener { _, checkedIds ->
            if (checkedIds.isNotEmpty()) {
                val color = when (checkedIds[0]) {
                    R.id.chipLabelTextBlack -> Color.BLACK
                    R.id.chipLabelTextWhite -> Color.WHITE
                    R.id.chipLabelTextPrimary -> primaryColor
                    else -> Color.BLACK
                }
                fabsMenu?.updateAllLabelsTextColor(color)
            }
        }

        binding.seekCornerRadius.onProgressChanged { progress, fromUser ->
            binding.txtCornerRadius.text = getString(R.string.dp_format, progress)
            if (fromUser) {
                fabsMenu?.updateAllLabelsCornerRadius(dpToPxFloat(progress))
            }
        }

        binding.seekTextPadding.onProgressChanged { progress, fromUser ->
            binding.txtTextPadding.text = getString(R.string.dp_format, progress)
            if (fromUser) {
                fabsMenu?.updateAllLabelsTextPadding(dpToPx(progress))
            }
        }

        binding.switchTitleClick.setOnCheckedChangeListener { _, isChecked ->
            fabsMenu?.updateAllLabelsTitleClickEnabled(isChecked)
        }
    }

    private fun setupResetButton() {
        binding.btnReset.setOnClickListener {
            resetUIToDefaults()
            applyDefaultSettings()
        }
    }

    private fun resetUIToDefaults() {
        binding.seekOverlayAlpha.progress = 77
        binding.switchClickableOverlay.isChecked = true
        binding.chipUp.isChecked = true
        binding.chipLabelsLeft.isChecked = true
        binding.chipSizeNormal.isChecked = true
        binding.seekMenuMargins.progress = 16
        binding.seekAnimationDuration.progress = 500
        binding.chipLabelBgWhite.isChecked = true
        binding.chipLabelTextBlack.isChecked = true
        binding.seekCornerRadius.progress = 4
        binding.seekTextPadding.progress = 8
        binding.switchTitleClick.isChecked = true
    }

    private fun applyDefaultSettings() {
        fabsMenuLayout?.setOverlayColor("#4d000000".toColorInt())
        fabsMenuLayout?.setClickableOverlay(true)
        fabsMenu?.updateExpandDirection(ExpandDirection.UP)
        fabsMenu?.updateLabelsPosition(LabelsPosition.LEFT)
        fabsMenu?.setMenuButtonSize(FloatingActionButton.SIZE_NORMAL)
        fabsMenu?.setMenuMargins(dpToPx(16))
        fabsMenu?.setAnimationDuration(500)
        fabsMenu?.updateAllLabelsBackgroundColor(Color.WHITE)
        fabsMenu?.updateAllLabelsTextColor(Color.BLACK)
        fabsMenu?.updateAllLabelsCornerRadius(dpToPxFloat(4))
        fabsMenu?.updateAllLabelsTextPadding(dpToPx(8))
        fabsMenu?.updateAllLabelsTitleClickEnabled(true)
    }

    private fun dpToPx(dp: Int): Int = (dp * resources.displayMetrics.density).toInt()

    private fun dpToPxFloat(dp: Int): Float = dp * resources.displayMetrics.density

    private inline fun SeekBar.onProgressChanged(crossinline action: (progress: Int, fromUser: Boolean) -> Unit) {
        setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                action(progress, fromUser)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
            override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "SettingsBottomSheet"
    }
}
