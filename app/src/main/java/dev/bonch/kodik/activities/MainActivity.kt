package dev.bonch.kodik.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.firestore.FirebaseFirestore
import dev.bonch.kodik.R
import dev.bonch.kodik.fragments.CoursesFragment
import dev.bonch.kodik.fragments.HomeFragment
import dev.bonch.kodik.fragments.NotificationsFragment
import dev.bonch.kodik.fragments.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigationDrawer()


    }

    private fun setupNavigationDrawer() {
        val navController = Navigation.findNavController(this, R.id.fragment_container)
        bottom_navigation_bar.setupWithNavController(navController)
    }


}
