package dev.bonch.kodik.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import dev.bonch.kodik.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupNavigationDrawer()
    }

    private fun setupNavigationDrawer() {
        navController = Navigation.findNavController(this, R.id.fragment_container)
        bottom_navigation_bar.setupWithNavController(navController)
    }

    fun onLessonPagerCardFragment(arguments: Bundle?) {
        navController.navigate(R.id.action_nav_fragment_class_cards_to_nav_courses_card, arguments)
    }

    fun onLessonChairFragment(arguments: Bundle?) {
        navController.navigate(R.id.action_nav_home_to_nav_fragment_chair, arguments)
    }

    fun onLessonChairFragmentFromMyCourses(arguments: Bundle?) {
        navController.navigate(R.id.action_nav_courses_to_nav_fragment_chair, arguments)
    }

    fun onClassCardsFragment(arguments: Bundle?) {
        navController.navigate(
            R.id.action_nav_fragment_chair_to_nav_fragment_class_cards,
            arguments
        )
    }

    fun onLessonChairFragmentFromPager(arguments: Bundle?) {
        navController.navigate(R.id.action_nav_courses_card_to_nav_fragment_chair, arguments)
    }
}
