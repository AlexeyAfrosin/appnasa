package com.afrosin.appnasa.view

import com.afrosin.appnasa.model.PictureDTO

sealed class AppState {
    data class Success(val pictureDTO: PictureDTO) : AppState()
    data class Error(val error: Throwable) : AppState()
    data class Loading(val progress: Int?) : AppState()
}
