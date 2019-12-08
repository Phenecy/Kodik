package dev.bonch.kodik.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    var name: String?,
    var email: String?,
    var photoUri: String?,
    var trophies: MutableList<Int> = mutableListOf(),
    var notifications: MutableList<Int> = mutableListOf(),
    var courses: MutableList<Boolean> = mutableListOf(),
    var coursesProgression: MutableList<Int> = mutableListOf()
)

