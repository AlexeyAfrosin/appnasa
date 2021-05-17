package com.afrosin.appnasa.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import coil.api.load
import com.afrosin.appnasa.R
import com.afrosin.appnasa.databinding.FragmentPictureOfTheDayBinding
import com.afrosin.appnasa.utils.dateToStr
import com.afrosin.appnasa.utils.getDate
import com.afrosin.appnasa.viewmodel.MainViewModel

class PictureOfTheDayFragment(private val addDayCount: Int) : Fragment() {


    private var _binding: FragmentPictureOfTheDayBinding? = null
    private val binding get() = _binding!!
    private var isExpanded = false

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

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val pictureDTO = appState.pictureDTO
                val url = pictureDTO.url

                binding.includePictureDescriptionLayout.sheetDescription.text =
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
                        binding.currentImage.setOnClickListener {
                            isExpanded = !isExpanded

                            TransitionManager.beginDelayedTransition(
                                binding.toolbarLayout, TransitionSet()
                                    .addTransition(ChangeBounds())
                                    .addTransition(ChangeImageTransform())
                            )

                            val params: ViewGroup.LayoutParams = binding.currentImage.layoutParams

                            if (isExpanded) {
                                params.height = ViewGroup.LayoutParams.MATCH_PARENT
                                binding.currentImage.scaleType = ImageView.ScaleType.CENTER_CROP
                            } else {
                                params.height = ViewGroup.LayoutParams.WRAP_CONTENT
                                binding.currentImage.scaleType = ImageView.ScaleType.FIT_CENTER
                            }

                            binding.currentImage.layoutParams = params

                        }
                    }
                }
            }
        }
    }
}
