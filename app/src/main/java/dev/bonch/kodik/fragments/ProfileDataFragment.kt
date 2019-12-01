package dev.bonch.kodik.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import dev.bonch.kodik.R

private lateinit var profileExitButton: Button
private lateinit var profileNameEditText: EditText
private lateinit var profileEmailEditText: EditText
private lateinit var profilePasswordEditText: EditText

class ProfileDataFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile_data, container, false)
        profileExitButton = view.findViewById(R.id.profile_exit_button)
        profileNameEditText = view.findViewById(R.id.profile_name_edit_text)
        profileEmailEditText = view.findViewById(R.id.profile_email_edit_text)
        profilePasswordEditText = view.findViewById(R.id.profile_password_edit_text)

        setEnableEditText(false)

        profileExitButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
        }
        return view
    }

    fun setEnableEditText(bool:Boolean){
        profileNameEditText.setEnabled(bool)
        profileEmailEditText.setEnabled(bool)
        profilePasswordEditText.setEnabled(bool)
    }
}