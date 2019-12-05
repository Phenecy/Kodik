package dev.bonch.kodik.models

data class Banner(val bannerId: Int) {
    class BannersController {
        val bannersList: MutableList<Banner> = mutableListOf()

        init {
            for(i in 0..8)
                bannersList.add(Banner(i))
        }
    }
}
