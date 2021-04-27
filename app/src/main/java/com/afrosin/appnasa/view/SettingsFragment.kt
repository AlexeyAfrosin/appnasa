package com.afrosin.appnasa.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.afrosin.appnasa.R
import com.afrosin.appnasa.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        initChipGroup()
        return binding.root
    }

    private fun initChipGroup() {
        binding.grSelectAppTheme.setOnCheckedChangeListener { _, position ->

            when (position) {
                R.id.gr_ch_theme_indigo -> setAppTheme(R.style.IndigoTheme, R.string.theme_indigo)
                R.id.gr_ch_theme_pink -> setAppTheme(R.style.PinkTheme, R.string.theme_pink)
            }
        }
    }

    private fun setAppTheme(appThemeId: Int, themeNameId: Int) {
        context?.setTheme(appThemeId)
        Toast.makeText(
            context,
            getString(R.string.theme_apply_text, getString(themeNameId)),
            Toast.LENGTH_SHORT
        ).show()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SettingsFragment()
    }
}