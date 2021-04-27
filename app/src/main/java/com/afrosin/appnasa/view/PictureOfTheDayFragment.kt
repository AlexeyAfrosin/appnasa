package com.afrosin.appnasa.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.afrosin.appnasa.R
import com.afrosin.appnasa.databinding.FragmentPictureOfTheDayBinding
import com.afrosin.appnasa.utils.dateToStr
import com.afrosin.appnasa.utils.getDate
import com.afrosin.appnasa.viewmodel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior

class PictureOfTheDayFragment(private val addDayCount: Int) : Fragment() {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private var _binding: FragmentPictureOfTheDayBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPictureOfTheDayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getData().observe(viewLifecycleOwner, { renderData(it) })
    }

    override fun onResume() {
        super.onResume()
        viewModel.sendServerRequest(dateToStr(getDate(addDayCount)))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setBottomSheetBehavior(binding.includeBottomSheetLayout.bottomSheetContainer)
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val pictureDTO = appState.pictureDTO
                val url = pictureDTO.url

                binding.includeBottomSheetLayout.bottomSheetDescription.text =
                    pictureDTO.explanation

                if (url.isNullOrEmpty()) {
                    Toast.makeText(context, "Ссылка на картинку отсутсвует", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    if (url.indexOf("youtube") > -1) {
                        binding.webViewYoutube.visibility = View.VISIBLE
                        binding.currentImage.visibility = View.GONE
                        binding.webViewYoutube.settings.javaScriptEnabled = true
                        binding.webViewYoutube.loadUrl(url)
                    } else {
                        binding.webViewYoutube.visibility = View.GONE
                        binding.currentImage.visibility = View.VISIBLE

                        binding.currentImage.load(url) {
                            lifecycle(viewLifecycleOwner)
                            error(R.drawable.ic_baseline_error_24)
                            placeholder(R.drawable.ic_baseline_broken_image)
                        }
                    }
                }
            }
            is AppState.Error -> {
            }
            is AppState.Loading -> {
            }
        }

    }
}
