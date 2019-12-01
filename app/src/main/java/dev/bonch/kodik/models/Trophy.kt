package dev.bonch.kodik.models

data class Trophy(
    val trophyId: Int,
    val trophyTitle: String
) {
    class TrophiesController {
        val trophiesList: MutableList<Trophy> = mutableListOf()

        init {
            for(i in 0..8)
                trophiesList.add(Trophy(i, "Награда"))
        }
    }
}