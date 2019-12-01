package dev.bonch.kodik.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import dev.bonch.kodik.R
import kotlinx.android.synthetic.main.fragment_profile.*

private lateinit var profileData : TextView
private lateinit var profileMyTrophy : TextView

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val profileTrophyFragment = ProfileTrophyFragment()
        val profileDataFragment = ProfileDataFragment()

        profileData = view.findViewById(R.id.profile_data)
        profileMyTrophy = view.findViewById(R.id.profile_my_trophy)

        fragmentManager!!.beginTransaction()
            .add(R.id.profile_container, profileTrophyFragment)
            .commit()

        fun toDataFragment(){
            profileData.setTextColor(Color.parseColor("#000000"))
            profileMyTrophy.setTextColor(Color.parseColor("#575757"))
            fragmentManager!!.beginTransaction()
                .replace(R.id.profile_container, profileDataFragment)
                .commit()
        }

        fun toTrophyFragment(){
            profileData.setTextColor(Color.parseColor("#575757"))
            profileMyTrophy.setTextColor(Color.parseColor("#000000"))
            fragmentManager!!.beginTransaction()
                .replace(R.id.profile_container, profileTrophyFragment)
                .commit()
        }

        profileData.setOnClickListener{
            toDataFragment()
        }

        profileMyTrophy.setOnClickListener{
            toTrophyFragment()
        }
        return view
    }
}