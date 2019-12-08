package dev.bonch.kodik.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Lesson(
    var type: String,
    var title: String,
    var text: String?,
    var answerChoice: MutableList<String>? = arrayListOf(),
    var rightAnswer: Int?
): Parcelable {
}