package com.afrosin.appnasa.model

data class ToDoItem(
    val id: Int = 0,
    val someText: String = "Text",
    val someDescription: String? = "Description"
)