package com.afrosin.appnasa.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afrosin.appnasa.BuildConfig
import com.afrosin.appnasa.model.PictureDTO
import com.afrosin.appnasa.repository.RetrofitImp
import com.afrosin.appnasa.view.AppState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(
    private val liveDataForViewToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val retrofitImp: RetrofitImp = RetrofitImp()
) : ViewModel() {

    fun getData(imageDateStr: String? = null): LiveData<AppState> {
        sendServerRequest(imageDateStr)
        return liveDataForViewToObserve
    }

    fun sendServerRequest(imageDateStr: String? = null) {
        liveDataForViewToObserve.value = AppState.Loading(null)
        val apiKey = BuildConfig.NASA_API_KEY

        if (apiKey.isBlank()) {
            AppState.Error(Throwable("API KEY не найден!"))
        } else {
            retrofitImp.getRetrofitImp().getPictureOfTheDay(apiKey, imageDateStr)
                .enqueue(object : Callback<PictureDTO> {
                    override fun onResponse(
                        call: Call<PictureDTO>,
                        response: Response<PictureDTO>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            liveDataForViewToObserve.value = AppState.Success(response.body()!!)
                        } else {
                            val message = response.message()
                            if (message.isNullOrEmpty()) {
                                liveDataForViewToObserve.value =
                                    AppState.Error(Throwable("Неизвестная ошибка"))
                            } else {
                                liveDataForViewToObserve.value = AppState.Error(Throwable(message))
                            }

                        }
                    }

                    override fun onFailure(call: Call<PictureDTO>, t: Throwable) {
                        liveDataForViewToObserve.value = AppState.Error(t)
                    }
                })
        }
    }

}